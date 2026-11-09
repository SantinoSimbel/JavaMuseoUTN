package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.Event;
import entities.Item;



public class EventDAO {
	private DbConnection db = new DbConnection();

	
	//add(Event newIte) -----------------------------------------------------------------------------------------
	public void add(Event newEve) {
		PreparedStatement stmt = null;
		PreparedStatement itemStmt = null;
		ResultSet keyRs = null;
		Connection conn = null;
		
		
		try {
			conn= db.getConnection();
			
			stmt = conn.prepareStatement("insert into event(title, description, endTime, startTime, status) values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); 
			stmt.setString(1, newEve.getTitle());
			stmt.setString(2, newEve.getDescription());
			stmt.setTime(3, java.sql.Time.valueOf(newEve.getEndTime()));
			stmt.setTime(4, java.sql.Time.valueOf(newEve.getStartTime()));
			stmt.setString(5, newEve.getStatus());
			
			stmt.executeUpdate();
			
			keyRs = stmt.getGeneratedKeys();
			
			if(keyRs!= null && keyRs.next()) {
				newEve.setId(keyRs.getInt(1)); 
			}
			
			itemStmt = conn.prepareStatement("insert into event_item(item_id, event_id) values(?, ?)"); 
			
			for (Item i : newEve.getItems()) {
				itemStmt.setInt(1, i.getId());
				itemStmt.setInt(2, newEve.getId());	
				itemStmt.executeUpdate();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//cerrar las conexiones para que no consuman recursos
			try {
				if(keyRs != null)keyRs.close();
				if(stmt != null)stmt.close();
				if(itemStmt != null)itemStmt.close();
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	
	
	
	//update(Event updEve)-----------------------------------------------------------------------------------------
	public void update(Event updEve) {
		PreparedStatement stmt = null;
		PreparedStatement itemStmt = null;
		Connection conn = null;	
		

		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("update event set title = ?, description = ?, endTime = ?, startTime = ?, status = ? where id = ?");
			stmt.setString(1, updEve.getTitle());
			stmt.setString(2, updEve.getDescription());
			stmt.setTime(3, java.sql.Time.valueOf(updEve.getEndTime()));
			stmt.setTime(4, java.sql.Time.valueOf(updEve.getStartTime()));
			stmt.setString(5, updEve.getStatus());
			stmt.setInt(6, updEve.getId()); 

			stmt.executeUpdate();
			
			
			itemStmt = conn.prepareStatement("delete from event_item where event_id = ?");
			itemStmt.setInt(1, updEve.getId());
			itemStmt.executeUpdate();
			itemStmt = conn.prepareStatement("insert into event_item(item_id, event_id) values(?, ?)"); 
			
			for (Item i : updEve.getItems()) {
				itemStmt.setInt(1, i.getId());
				itemStmt.setInt(2, updEve.getId());	
				itemStmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//cerrar las conexiones para que no consuman recursos
			try {
				if(stmt != null)stmt.close();
				if(itemStmt != null)itemStmt.close();
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}

	} 
	
	
	
	
	
	//delete(Event delEve) -----------------------------------------------------------------------------------------
	public void delete(Event delEve) {
		PreparedStatement stmt = null;
		PreparedStatement stmtItem = null;
		Connection conn = null;
		
		try {
			conn= db.getConnection();
			stmtItem = conn.prepareStatement("delete from event_item where event_id = ?");
			stmtItem.setInt(1, delEve.getId());
			stmtItem.executeUpdate();
			
			stmt = conn.prepareStatement("delete from event where id = ?");
			stmt.setInt(1, delEve.getId());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//cerrar las conexiones para que no consuman recursos
			try {
				if(stmt != null)stmt.close();
				if(stmtItem != null)stmtItem.close();
				db.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}
}
