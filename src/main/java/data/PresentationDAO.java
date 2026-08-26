package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


import entities.Category;
import entities.Item;
import entities.Presentation; 
import entities.Event;


public class PresentationDAO {
	private DbConnection db = new DbConnection();

	
	//list() -----------------------------------------------------------------------------------------
	public LinkedList<Presentation> list() {
		LinkedList<Presentation> presentations = new LinkedList<>();

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		
		try {
			conn = db.getConnection(); 
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("SELECT e.id, e.title, e.description, e.endTime, e.startTime, e.item_id, p.day, p.capacity, "
										+ "i.name AS item_name, i.description AS item_desc, i.picture, i.category_id, c.name AS category_name "
					 				+ "FROM event e "
									+ "INNER JOIN presentation p ON p.event_id = e.id "
					 				+ "INNER JOIN item i ON i.id = e.item_id "
					 				+ "INNER JOIN category c ON i.category_id = c.id "
					 				+ "ORDER BY i.id ASC");


			while (rs != null && rs.next()) {
				
				Presentation e = new Presentation();
				
				e.setId(rs.getInt("id"));
				e.setTitle(rs.getString("title"));
				e.setDescription(rs.getString("description"));
				e.setEndTime(rs.getTime("endTime").toLocalTime());
				e.setStartTime(rs.getTime("startTime").toLocalTime());
				
				e.setDay(rs.getDate("day").toLocalDate());
				e.setCapacity(rs.getInt("capacity"));
				
				Item ite = new Item();
				ite.setId(rs.getInt("item_id"));
				ite.setName(rs.getString("item_name"));
				ite.setDescription(rs.getString("item_desc"));
				ite.setPicture(rs.getString("picture"));
				
				Category cat = new Category();
			    cat.setId(rs.getInt("category_id"));
			    cat.setName(rs.getString("category_name"));
				
			    ite.setCategory(cat);
			    e.setItem(ite);

			    presentations.add(e); 
			}
					
			return presentations;

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
	
	
	
	
	
	
	
	
	
	
	//search(Presentation p) -----------------------------------------------------------------------------------------
	public Presentation search(Presentation p) {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			//lo definimos aca asi si nunca entra al try puedo devolverlo null (en caso de querer avisar que esta nulo, hacerlo en la interfaz grafica!)
			Presentation pre = null;
			
			conn= db.getConnection();
			stmt = conn.prepareStatement("SELECT e.id, e.title, e.description, e.endTime, e.startTime, e.item_id, p.day, p.capacity, "
											+ "i.name AS item_name, i.description AS item_desc, i.picture, i.category_id, c.name AS category_name "
										+ "FROM event e "
							            + "INNER JOIN presentation p ON p.event_id = e.id "
										+ "INNER JOIN item i ON i.id = e.item_id "
										+ "INNER JOIN category c ON i.category_id = c.id "
										+ "WHERE e.id = ?");
			
			stmt.setInt(1, p.getId());
			rs = stmt.executeQuery();
			
			if(rs!= null && rs.next()) {
				pre = new Presentation();
				pre.setDay(rs.getDate("day").toLocalDate());
				pre.setCapacity(rs.getInt("capacity"));
				
				
				pre.setId(rs.getInt("id"));
				pre.setTitle(rs.getString("title"));
				pre.setDescription(rs.getString("description"));
				pre.setEndTime(rs.getTime("endTime").toLocalTime());
				pre.setStartTime(rs.getTime("startTime").toLocalTime());
				
				
				
				
				Item ite = new Item();
				ite.setId(rs.getInt("item_id"));
				ite.setName(rs.getString("item_name"));
				ite.setDescription(rs.getString("item_desc"));
				ite.setPicture(rs.getString("picture"));
				
				Category cat = new Category();
			    cat.setId(rs.getInt("category_id"));
			    cat.setName(rs.getString("category_name"));
				
			    ite.setCategory(cat);
			    pre.setItem(ite);
			}					
					
			return pre;
			
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
	
	
	
	
	
	
	
	
	
	
	//add(Presentation newPre) -----------------------------------------------------------------------------------------
	public void add(Presentation newPre) {
		PreparedStatement stmt = null;
		Connection conn = null;
		
		
		try {
			conn= db.getConnection();
			
			stmt = conn.prepareStatement("insert into presentation(event_id, day, capacity) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS); 
			stmt.setInt(1, newPre.getId());
			stmt.setDate(2, java.sql.Date.valueOf(newPre.getDay()));
			stmt.setInt(3, newPre.getCapacity());
			

			
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
	
	
	
	
	
	//update(Presentation updPre)-----------------------------------------------------------------------------------------
	public void update(Presentation updPre) {
		PreparedStatement stmt = null;
		Connection conn = null;	
		

		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("update presentation set day = ?, capacity = ? where event_id = ?");
			stmt.setDate(1, java.sql.Date.valueOf(updPre.getDay()));
			stmt.setInt(2, updPre.getCapacity());
			stmt.setInt(3, updPre.getId());
			

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
			stmt = conn.prepareStatement("delete from presentation where event_id = ?");

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
