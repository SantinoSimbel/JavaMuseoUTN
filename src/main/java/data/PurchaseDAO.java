package data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Category;
import entities.Purchase;

public class PurchaseDAO {

	private DbConnection db = new DbConnection();

	// es un metodo normal, tiene misma estructura que un
	// public String mostrarDatos()
	public LinkedList<Category> list() {
		LinkedList<Category> categories = new LinkedList<>();
		// definimos estos 2 aca para que pueda encontrlos el finally
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		// crear el statement (sentencia)
		try {
			conn = db.getConnection(); // nos conectamos
			stmt = conn.createStatement();
			// ejecutar la sentencia (y guardamos el resultado en resulset)
			rs = stmt.executeQuery("select id, name from category");

			// recorremos el resulset para mapearlo a objetos
			while (rs != null && rs.next()) {
				Category c = new Category();

				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));

				categories.add(c); // lo guardo en la linked list que cree al inicio del metodo
			}
			return categories;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			// finally siempre se ejecuta, tanto si va por try o por catch
			// cerrar las conexiones para que no consuman recursos
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}
	
	public Category search(Category c) { //recibimos un Category que solo tenga el id
		//definimos estos 2 aca para que pueda encontrlos el finally
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		//crear el prepareStatement (la consulta)
		try {
			//lo definimos aca asi si nunca entra al try puedo devolverlo null (en caso de querer avisar que esta nulo, hacerlo en la interfaz grafica!)
			conn= db.getConnection();
			Category cat = null;
			stmt = conn.prepareStatement("select id, name from category where id = ?");
			//al primer signo de pregunta(1) le asigno el resultado de getId() (el id que ingreso el usuario)
			stmt.setInt(1, c.getId());
			
			rs = stmt.executeQuery();
			
			if(rs!= null && rs.next()) {
				cat = new Category();
				cat.setId(rs.getInt("id"));
				cat.setName(rs.getString("name"));
			}
			
			return cat;
			
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
	

	public Category searchByName(Category c) { 
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn= db.getConnection();
			Category cat = null;
			stmt = conn.prepareStatement("select id, name from category where LOWER(TRIM(name)) = LOWER(TRIM(?))");
			stmt.setString(1, c.getName().trim().toLowerCase());
			
			rs = stmt.executeQuery();
			
			if(rs!= null && rs.next()) {
				cat = new Category();
				cat.setId(rs.getInt("id"));
				cat.setName(rs.getString("name"));
			}
			
			return cat;
			
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

	
	
	
	public void add(Purchase newPur) {
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("insert into purchase(user_id, presentation_id, quantity, total_price) values(?,?,?,?)");
			stmt.setInt(1, newPur.getUserId());
			stmt.setInt(2, newPur.getPresentationId());
			stmt.setInt(3, newPur.getQuantity());
			stmt.setBigDecimal(4, newPur.getTotalPrice());

			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
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
		
		
	    Connection conn = null;
	    

	    try {
	        conn = db.getConnection();
	        //como vamos a hacer varias consultas juntas, desactivamos el autocomit para que solo se ejecute cuando hacemos el conn.commit()
	        conn.setAutoCommit(false);

	        //declaro estas variables para usarlos en los calculos (luego las relleno)
	        int capacity;
	        BigDecimal price;
	        
	        // 1. busco la presentación y bloqueo su fila con el for update. hace esperar a otra transaccion que quiera hacer algo con esos datos hasta que la transaccion actual termine
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

	        // 2. Sumo todas las entradas compradas para esa presentación
	        int ticketsSold = 0;

	        stmtTicketsSold = conn.prepareStatement("SELECT COALESCE(SUM(quantity), 0) AS ticketsSold " +
		            "FROM purchase WHERE presentation_id = ?");
	        
	        stmtTicketsSold.setInt(1, newPur.getPresentationId());
	        
	        rsTicketsSold = stmtTicketsSold.executeQuery();
	        
	        if (rsTicketsSold != null && rsTicketsSold.next()) {
	        	ticketsSold = rsTicketsSold.getInt("ticketsSold");
	        } 

	        // 3. Compruebo si alcanza el cupo
	        int availableTickets = capacity - ticketsSold;

	        if (newPur.getQuantity() > availableTickets) {
	            throw new Exception("No hay cupo suficiente. Solo quedan: " + availableTickets +" disponibles.");
	        }

	        // 4. Calculo el total usando el precio de la base de datos
	        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(newPur.getQuantity()));

	        newPur.setTotalPrice(totalPrice);

	        // 5. Inserto la compra
	        stmtSavePurchase = conn.prepareStatement("INSERT INTO purchase " +
	            "(user_id, presentation_id, quantity, total_price) " +
	            "VALUES (?, ?, ?, ?)");

	        stmtSavePurchase.setInt(1, newPur.getUserId());
	        stmtSavePurchase.setInt(2, newPur.getPresentationId());
	        stmtSavePurchase.setInt(3, newPur.getQuantity());
	        stmtSavePurchase.setBigDecimal(4, newPur.getTotalPrice());

	        stmtSavePurchase.executeUpdate();

	        // 6. Confirmo la transacción
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
	     // REVISAR LO DEL FINALLY Y VER BIEN QUE COSAS TENGO QUE CERRAR. hacerlo como en el list de category, todo junto?
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

	    		conn.setAutoCommit(true);
	    		db.releaseConnection();
	    		
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
	}

	public void update(Category updCat) {
		PreparedStatement stmt = null;
		Connection conn = null;	
		
		//crear el prepareStatement (la consulta)
		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("update category set name = ? where id = ?");
			stmt.setString(1, updCat.getName());
			stmt.setInt(2, updCat.getId());

			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//cerrar las conexiones para que no consuman recursos
			try {
				if(stmt != null)stmt.close();
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}

	} 
	
	public void delete(Category delCat) {
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("delete from category where id = ?");

			stmt.setInt(1, delCat.getId());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//cerrar las conexiones para que no consuman recursos
			try {
				if(stmt != null)stmt.close();
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}
}

