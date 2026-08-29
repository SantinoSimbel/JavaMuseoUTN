package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


import entities.Category;
import entities.Event;
import entities.Item;
import entities.Exhibition; 


public class ExhibitionDAO {
	private DbConnection db = new DbConnection();

	
	//list() -----------------------------------------------------------------------------------------
	public LinkedList<Exhibition> list() {
		LinkedList<Exhibition> exhibitions = new LinkedList<>();

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		
		try {
			conn = db.getConnection(); 
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("SELECT e.id, e.title, e.description, e.endTime, e.startTime, ex.startDay, ex.endDay, e_i.item_id, "
										+ "i.name AS item_name, i.description AS item_desc, i.picture, i.category_id, c.name AS category_name "
					 				+ "FROM event e "
									+ "INNER JOIN exhibition ex ON ex.event_id = e.id "
									+ "INNER JOIN event_item e_i ON e_i.event_id = e.id "
					 				+ "INNER JOIN item i ON i.id = e_i.item_id "
					 				+ "INNER JOIN category c ON i.category_id = c.id "
					 				+ "ORDER BY i.id ASC");


			while (rs != null && rs.next()) {
				
				
				Item ite = new Item();
				ite.setId(rs.getInt("item_id"));
				ite.setName(rs.getString("item_name"));
				ite.setDescription(rs.getString("item_desc"));
				ite.setPicture(rs.getString("picture"));
				
				Category cat = new Category();
			    cat.setId(rs.getInt("category_id"));
			    cat.setName(rs.getString("category_name"));
				
			    ite.setCategory(cat);
				
				
				boolean exist = false;
				for (Exhibition e : exhibitions) {
					if (e.getId() == rs.getInt("id")) {
						e.addItem(ite);
						exist = true;
					}
				}
				
				if (!exist) {
					Exhibition e = new Exhibition();
				
					e.setId(rs.getInt("id"));
					e.setTitle(rs.getString("title"));
					e.setDescription(rs.getString("description"));
					e.setEndTime(rs.getTime("endTime").toLocalTime());
					e.setStartTime(rs.getTime("startTime").toLocalTime());
					
					e.setStartDay(rs.getDate("startDay").toLocalDate());
					e.setEndDay(rs.getDate("endDay").toLocalDate());
				
			    	e.addItem(ite);

			    	exhibitions.add(e); 
				}

			}
					
			return exhibitions;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
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
	
	
	
	
	
	
	
	
	
	
	//search(Exhibition ex) -----------------------------------------------------------------------------------------
	public Exhibition search(Exhibition ex) {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			//lo definimos aca asi si nunca entra al try puedo devolverlo null (en caso de querer avisar que esta nulo, hacerlo en la interfaz grafica!)
			Exhibition exi = null;
			
			conn= db.getConnection();
			stmt = conn.prepareStatement("SELECT e.id, e.title, e.description, e.endTime, e.startTime, ex.startDay, ex.endDay, e_i.item_id, "
											+ "i.name AS item_name, i.description AS item_desc, i.picture, i.category_id, c.name AS category_name "
										+ "FROM event e "
							            + "INNER JOIN exhibition ex ON ex.event_id = e.id "
							            + "INNER JOIN event_item e_i ON e_i.event_id = e.id "
						 				+ "INNER JOIN item i ON i.id = e_i.item_id "
							            + "INNER JOIN category c ON i.category_id = c.id "
										+ "WHERE e.id = ?");
			
			stmt.setInt(1, ex.getId());
			rs = stmt.executeQuery();
			
			if(rs!= null && rs.next()) {
				exi = new Exhibition();
				exi.setStartDay(rs.getDate("startDay").toLocalDate());
				exi.setEndDay(rs.getDate("endDay").toLocalDate());

				exi.setId(rs.getInt("id"));
				exi.setTitle(rs.getString("title"));
				exi.setDescription(rs.getString("description"));
				exi.setEndTime(rs.getTime("endTime").toLocalTime());
				exi.setStartTime(rs.getTime("startTime").toLocalTime());
				
				Item ite = new Item();
				ite.setId(rs.getInt("item_id"));
				ite.setName(rs.getString("item_name"));
				ite.setDescription(rs.getString("item_desc"));
				ite.setPicture(rs.getString("picture"));
			
				Category cat = new Category();
				cat.setId(rs.getInt("category_id"));
				cat.setName(rs.getString("category_name"));
			
				ite.setCategory(cat);
				exi.addItem(ite);
				
				while(rs.next()) {
					
					ite = new Item();
				
					ite.setId(rs.getInt("item_id"));
					ite.setName(rs.getString("item_name"));
					ite.setDescription(rs.getString("item_desc"));
					ite.setPicture(rs.getString("picture"));
					
					cat = new Category();
					cat.setId(rs.getInt("category_id"));
					cat.setName(rs.getString("category_name"));
				
					ite.setCategory(cat);
					exi.addItem(ite);
				}

			}					
					
			return exi;
			
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
	
	
	
	
	
	
	
	
	
	
	//add(Exhibition newExi) -----------------------------------------------------------------------------------------
	public void add(Exhibition newExi) {
		PreparedStatement stmt = null;
		Connection conn = null;
		
		
		try {
			conn= db.getConnection();
			
			stmt = conn.prepareStatement("insert into exhibition(event_id, startDay, endDay) values(?, ?, ?)"); 
			stmt.setInt(1, newExi.getId());
			stmt.setDate(2, java.sql.Date.valueOf(newExi.getStartDay()));
			stmt.setDate(3, java.sql.Date.valueOf(newExi.getEndDay()));
			

			
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
	
	
	
	
	
	//update(Exhibition updExi)-----------------------------------------------------------------------------------------
	public void update(Exhibition updExi) {
		PreparedStatement stmt = null;
		Connection conn = null;	
		

		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("update exhibition set startDay = ?, endDay = ? where event_id = ?");
			stmt.setDate(1, java.sql.Date.valueOf(updExi.getStartDay()));
			stmt.setDate(2, java.sql.Date.valueOf(updExi.getEndDay()));
			stmt.setInt(3, updExi.getId());
			

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
	
	
	
	
	
	//delete(Event delEve) -----------------------------------------------------------------------------------------
	public void delete(Event delEve) {
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("delete from exhibition where event_id = ?");

			stmt.setInt(1, delEve.getId());

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
