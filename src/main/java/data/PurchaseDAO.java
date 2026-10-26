package data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import entities.Presentation;
import entities.Purchase;
import entities.User;

public class PurchaseDAO {

	private DbConnection db = new DbConnection();
	
	public Purchase searchByUserAndPresentation(Purchase p){
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn= db.getConnection();
			Purchase pur = null;
			stmt = conn.prepareStatement("select user_id, presentation_id, quantity, total_price from purchase where user_id = ? and presentation_id = ?");

			stmt.setInt(1, p.getUserId());
			stmt.setInt(2, p.getPresentationId());
			
			rs = stmt.executeQuery();
			
			if(rs!= null && rs.next()) {
				pur = new Purchase();
				pur.setUserId(rs.getInt("user_id"));
				pur.setPresentationId(rs.getInt("presentation_id"));
				pur.setQuantity(rs.getInt("quantity"));
		        pur.setTotalPrice(rs.getBigDecimal("total_price"));
			}
			
			return pur;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(rs != null)rs.close();
				if(stmt != null)stmt.close();
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public LinkedList<Purchase> searchAllNotFinishedByUser(User u){
		
		LinkedList<Purchase> purchases = new LinkedList<>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement(
					"SELECT p.user_id, p.presentation_id, p.quantity, p.total_price " +
				    "FROM purchase p " +
				    "INNER JOIN presentation pr ON p.presentation_id = pr.event_id " +
				    "INNER JOIN event e ON pr.event_id = e.id " +
				    "WHERE p.user_id = ? " +
				    "AND TIMESTAMP(pr.day, e.endTime) > NOW()"
				);

			stmt.setInt(1, u.getId());
			
			rs = stmt.executeQuery();
			
			while(rs!= null && rs.next()) {
				Purchase pur = new Purchase();
				pur.setUserId(rs.getInt("user_id"));
				pur.setPresentationId(rs.getInt("presentation_id"));
				pur.setQuantity(rs.getInt("quantity"));
		        pur.setTotalPrice(rs.getBigDecimal("total_price"));
		        
		        purchases.add(pur);
			}
			
			return purchases;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(stmt != null)
					stmt.close();
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public LinkedList<Purchase> searchByPresentation(Presentation pre){
			
			PreparedStatement stmt = null;
			ResultSet rs = null;
			Connection conn = null;
			
			try {
				conn= db.getConnection();
				LinkedList<Purchase> purchases = new LinkedList<>();
				stmt = conn.prepareStatement("select user_id, presentation_id, quantity, total_price from purchase where presentation_id = ?");
	
				stmt.setInt(1, pre.getId());
				
				rs = stmt.executeQuery();
				
				while(rs!= null && rs.next()) {
					Purchase pur = new Purchase();
					pur.setUserId(rs.getInt("user_id"));
					pur.setPresentationId(rs.getInt("presentation_id"));
					pur.setQuantity(rs.getInt("quantity"));
			        pur.setTotalPrice(rs.getBigDecimal("total_price"));
			        
			        purchases.add(pur);
				}
				
				return purchases;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} finally {
				try {
					if(rs != null)rs.close();
					if(stmt != null)stmt.close();
					db.releaseConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	
	public void addWithCapacityCheck(Purchase newPur) throws Exception {
		//Primero lo hago con solo la logica de la primera compra
		PreparedStatement stmtSelectPresentations = null;
		ResultSet rsSelectPresentations = null;
		
		PreparedStatement stmtTicketsSold = null;
		ResultSet rsTicketsSold = null;
		
		PreparedStatement stmtSavePurchase = null;
		
		PreparedStatement stmtCheckPreviousPurchase = null;
		ResultSet rsCheckPreviousPurchase = null;
		
		PreparedStatement stmtUpdatePurchase = null;
		
	    Connection conn = null;
	    

	    try {
	        conn = db.getConnection();
	        //como vamos a hacer varias consultas juntas, desactivamos el autocomit para que solo se ejecute cuando hacemos el conn.commit()
	        conn.setAutoCommit(false);

	        int capacity;
	        BigDecimal price;
	        
	        // 1) busco la presentación y bloqueo su fila con el for update. hace esperar a otra transaccion que quiera hacer algo con esos datos hasta que la transaccion actual termine
	        stmtSelectPresentations = conn.prepareStatement("SELECT capacity, price " +
		            "FROM presentation " +
		            "WHERE event_id = ? FOR UPDATE");
	        
	        stmtSelectPresentations.setInt(1, newPur.getPresentationId());
	        
	        rsSelectPresentations = stmtSelectPresentations.executeQuery();
	        
	        if (rsSelectPresentations != null && rsSelectPresentations.next()) {
	            capacity = rsSelectPresentations.getInt("capacity");
	            price = rsSelectPresentations.getBigDecimal("price");
	        } else {
	            throw new Exception("La presentación no existe.");
	        }

	        // 2) busco todas las entradas compradas para esta presentación
	        int ticketsSold = 0;

	        stmtTicketsSold = conn.prepareStatement("SELECT COALESCE(SUM(quantity), 0) AS ticketsSold " +
		            "FROM purchase WHERE presentation_id = ?");
	        
	        stmtTicketsSold.setInt(1, newPur.getPresentationId());
	        
	        rsTicketsSold = stmtTicketsSold.executeQuery();
	        
	        if (rsTicketsSold != null && rsTicketsSold.next()) {
	        	ticketsSold = rsTicketsSold.getInt("ticketsSold");
	        } 
	        
	        // 3) verifico si hay cupo para las nuevas entradas
	        int availableTickets = capacity - ticketsSold;

	        if (newPur.getQuantity() > availableTickets) {
	            throw new Exception("No hay cupo suficiente. Solo quedan: " + availableTickets +" disponibles.");
	        }
	        
	        // 4) reviso si tiene otra compra, si tiene hago el update y si no el insert. luego commit
        	int oldTickets =0;
        	
	        stmtCheckPreviousPurchase = conn.prepareStatement("SELECT quantity as oldTickets FROM purchase where user_id = ? and presentation_id = ?");
	        stmtCheckPreviousPurchase.setInt(1, newPur.getUserId());
	        stmtCheckPreviousPurchase.setInt(2, newPur.getPresentationId());
	        
	        rsCheckPreviousPurchase = stmtCheckPreviousPurchase.executeQuery();
	        
	        if (rsCheckPreviousPurchase != null && rsCheckPreviousPurchase.next()) {
	        	//calculo total price
	        	oldTickets = rsCheckPreviousPurchase.getInt("oldTickets");
	        	
	        	int actualTickets = newPur.getQuantity()+ oldTickets;
	        	newPur.setQuantity(actualTickets);
	        	
	        	BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(actualTickets));

		        newPur.setTotalPrice(totalPrice);
	        	//actualizo la tabla
		        
		        stmtUpdatePurchase = conn.prepareStatement("update purchase set quantity = ?, total_price = ? where user_id = ? and presentation_id = ? ");
		        stmtUpdatePurchase.setInt(1, newPur.getQuantity());
		        stmtUpdatePurchase.setBigDecimal(2, newPur.getTotalPrice());
		        stmtUpdatePurchase.setInt(3, newPur.getUserId());
		        stmtUpdatePurchase.setInt(4, newPur.getPresentationId());

		        stmtUpdatePurchase.executeUpdate();

	        } else {
	            //calculo total price
		        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(newPur.getQuantity()));

		        newPur.setTotalPrice(totalPrice);
		        
		        // Inserto la compra
		        stmtSavePurchase = conn.prepareStatement("INSERT INTO purchase " +
		            "(user_id, presentation_id, quantity, total_price) " +
		            "VALUES (?, ?, ?, ?)");

		        stmtSavePurchase.setInt(1, newPur.getUserId());
		        stmtSavePurchase.setInt(2, newPur.getPresentationId());
		        stmtSavePurchase.setInt(3, newPur.getQuantity());
		        stmtSavePurchase.setBigDecimal(4, newPur.getTotalPrice());

		        stmtSavePurchase.executeUpdate();        	
	        }


	        // 5) Confirmo la transacción
	        conn.commit();

	    } catch (Exception e) {
	    	e.printStackTrace(); 
	    	//el catch agarra cualquier exepcion y trata de hacer un rollback
	        if (conn != null) {
	            try {
	                conn.rollback();
	                //este rollback puede fallar por eso esta con un try-catch tambien
	            } catch (SQLException rollbackError) {
	                e.addSuppressed(rollbackError); //addSuppressed lo guarda como error secundario
	            }
	        }

	        throw e; //al mandarlo asi en vez de con e.printStackTrace() el error se propaga a las otras capas, por lo que puedo recuperarlo y mostrarlo.
	    } finally {
	    	try {
	    		if(stmtSelectPresentations != null) {
	    			stmtSelectPresentations.close();
	    		}
	    		if (rsSelectPresentations != null) {
	    			rsSelectPresentations.close();
	    		}
	    		
	    		if(stmtTicketsSold != null) {
	    			stmtTicketsSold.close();
	    		}
	    		
	    		if (rsTicketsSold != null) {
	    			rsTicketsSold.close();
	    		}
	    		
	    		if(stmtSavePurchase != null) {
	    			stmtSavePurchase.close();
	    		}

	    		if(stmtCheckPreviousPurchase != null) {
	    			stmtCheckPreviousPurchase.close();
	    		}
	    		
	    		if(rsCheckPreviousPurchase != null) {
	    			rsCheckPreviousPurchase.close();
	    		}
	    		
	    		if(stmtUpdatePurchase != null) {
	    			stmtUpdatePurchase.close();
	    		}
	    			    			
	    		conn.setAutoCommit(true);
	    		db.releaseConnection();
	    		
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
	}


}

