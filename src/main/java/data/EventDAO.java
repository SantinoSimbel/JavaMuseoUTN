package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Event;
import entities.Category;
import entities.Item;
import entities.Exhibition;
import entities.Presentation;


public class EventDAO {
	private DbConnection db = new DbConnection();

	
	
	
	//NOTA: EL list y search de momento no se han usado. Concideración de elminar u editar: posible
	//list() -----------------------------------------------------------------------------------------
	public LinkedList<Event> list() {
		LinkedList<Event> events = new LinkedList<>();

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		
		try {
			conn = db.getConnection(); 
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("SELECT e.id, e.title, e.description, e.endTime, e.startTime, e.item_id, "
										+ "i.name AS item_name, i.description AS item_desc, i.picture, i.category_id, c.name AS category_name "
					 				+ "FROM event e "
					 				+ "INNER JOIN item i ON i.id = e.item_id "
					 				+ "INNER JOIN category c ON i.category_id = c.id "
					 				+ "ORDER BY i.id ASC");


			while (rs != null && rs.next()) {
				
				Event e = new Event();
				
				e.setId(rs.getInt("id"));
				e.setTitle(rs.getString("title"));
				e.setDescription(rs.getString("description"));
				e.setEndTime(rs.getTime("endTime").toLocalTime());
				e.setStartTime(rs.getTime("startTime").toLocalTime());
				
				
				
				
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

			    events.add(e); 
			}
					
			return events;

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
	
	
	
	
	
	//search(Event ev) -----------------------------------------------------------------------------------------
	public Event search(Event ev) {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			//lo definimos aca asi si nunca entra al try puedo devolverlo null (en caso de querer avisar que esta nulo, hacerlo en la interfaz grafica!)
			Event eve = null;
			
			conn= db.getConnection();
			stmt = conn.prepareStatement("SELECT e.id, e.title, e.description, e.endTime, e.startTime, e.item_id, "
											+ "i.name AS item_name, i.description AS item_desc, i.picture, i.category_id, c.name AS category_name "
										+ "FROM event e "
										+ "INNER JOIN item i ON i.id = e.item_id "
										+ "INNER JOIN category c ON i.category_id = c.id "
										+ "WHERE e.id = ?");
			
			stmt.setInt(1, ev.getId());
			rs = stmt.executeQuery();
			
			if(rs!= null && rs.next()) {
				
				eve = new Event();

				eve.setId(rs.getInt("id"));
				eve.setTitle(rs.getString("title"));
				eve.setDescription(rs.getString("description"));
				eve.setEndTime(rs.getTime("endTime").toLocalTime());
				eve.setStartTime(rs.getTime("startTime").toLocalTime());
				
				
				
				
				Item ite = new Item();
				ite.setId(rs.getInt("item_id"));
				ite.setName(rs.getString("item_name"));
				ite.setDescription(rs.getString("item_desc"));
				ite.setPicture(rs.getString("picture"));
				
				Category cat = new Category();
			    cat.setId(rs.getInt("category_id"));
			    cat.setName(rs.getString("category_name"));
				
			    ite.setCategory(cat);
			    eve.setItem(ite);
			}					
					
			return eve;
			
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
	
	
	
	
	
	
	
	
	
	
	//add(Item newIte) -----------------------------------------------------------------------------------------
	public void add(Event newEve) {
		PreparedStatement stmt = null;
		ResultSet keyRs = null;
		Connection conn = null;
		
		
		try {
			conn= db.getConnection();
			
			stmt = conn.prepareStatement("insert into event(title, description, endTime, startTime, item_id) values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); 
			stmt.setString(1, newEve.getTitle());
			stmt.setString(2, newEve.getDescription());
			stmt.setTime(3, java.sql.Time.valueOf(newEve.getEndTime()));
			stmt.setTime(4, java.sql.Time.valueOf(newEve.getStartTime()));
			stmt.setInt(5, newEve.getItem().getId());

			
			stmt.executeUpdate();
			
			keyRs = stmt.getGeneratedKeys();
			
			if(keyRs!= null && keyRs.next()) {
				newEve.setId(keyRs.getInt(1)); 
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//cerrar las conexiones para que no consuman recursos
			try {
				if(keyRs != null)keyRs.close();
				if(stmt != null)stmt.close();
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	
	
	
	//update(Event updEve)-----------------------------------------------------------------------------------------
	public void update(Event updEve) {
		PreparedStatement stmt = null;
		Connection conn = null;	
		

		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("update event set title = ?, description = ?, endTime = ?, startTime = ?, item_id = ? where id = ?");
			stmt.setString(1, updEve.getTitle());
			stmt.setString(2, updEve.getDescription());
			stmt.setTime(3, java.sql.Time.valueOf(updEve.getEndTime()));
			stmt.setTime(4, java.sql.Time.valueOf(updEve.getStartTime()));
			stmt.setInt(5, updEve.getItem().getId());
			stmt.setInt(6, updEve.getId()); 
			

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
			stmt = conn.prepareStatement("delete from event where id = ?");

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
