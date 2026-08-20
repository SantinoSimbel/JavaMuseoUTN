package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Category;
import entities.Item;


public class ItemDAO {
	private DbConnection db = new DbConnection();

	
	
	//list() -----------------------------------------------------------------------------------------
	public LinkedList<Item> list() {
		LinkedList<Item> items = new LinkedList<>();

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		
		try {
			conn = db.getConnection(); 
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("SELECT i.id, i.name, i.description, i.picture, i.category_id, c.name AS category_name "
					 				+ "FROM item i "
					 				+ "INNER JOIN category c ON i.category_id = c.id "
					 				+ "ORDER BY i.id ASC");


			while (rs != null && rs.next()) {
				Item i = new Item();

				i.setId(rs.getInt("id"));
				i.setName(rs.getString("name"));
				i.setDescription(rs.getString("description"));
				i.setPicture(rs.getString("picture"));
				
				Category cat = new Category();
			    cat.setId(rs.getInt("category_id"));
			    cat.setName(rs.getString("category_name"));
				
			    i.setCategory(cat);

				items.add(i); 
			}
			return items;

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
	
	
	
	
	
	
	
	
	
	//search(Item i) -----------------------------------------------------------------------------------------
	public Item search(Item i) {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			//lo definimos aca asi si nunca entra al try puedo devolverlo null (en caso de querer avisar que esta nulo, hacerlo en la interfaz grafica!)
			conn= db.getConnection();
			Item ite = null;
			stmt = conn.prepareStatement("SELECT i.id, i.name, i.description, i.picture, i.category_id, c.name AS category_name "
										+ "FROM item i "
										+ "INNER JOIN category c ON i.category_id = c.id "
										+ "WHERE i.id = ?");
			stmt.setInt(1, i.getId());
			
			rs = stmt.executeQuery();
			
			if(rs!= null && rs.next()) {
				ite = new Item();
				ite.setId(rs.getInt("id"));
				ite.setName(rs.getString("name"));
				ite.setDescription(rs.getString("description"));
				ite.setPicture(rs.getString("picture"));
				
				Category cat = new Category();
			    cat.setId(rs.getInt("category_id"));
			    cat.setName(rs.getString("category_name"));
				
			    ite.setCategory(cat);
			}
			
			return ite;
			
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
	public void add(Item newIte) {
		PreparedStatement stmt = null;
		ResultSet keyRs = null;
		Connection conn = null;
		
		
		try {
			conn= db.getConnection();
			
			stmt = conn.prepareStatement("insert into item(name, description, picture, category_id ) values(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); 
			stmt.setString(1, newIte.getName());
			stmt.setString(2, newIte.getDescription());
			stmt.setString(3, newIte.getPicture());
			stmt.setInt(4, newIte.getCategory().getId());

			
			stmt.executeUpdate();
			
			keyRs = stmt.getGeneratedKeys();
			
			if(keyRs!= null && keyRs.next()) {
				newIte.setId(keyRs.getInt(1)); 
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
	
	
	
	
	
	//update(Item updIte) -----------------------------------------------------------------------------------------
	public void update(Item updIte) {
		PreparedStatement stmt = null;
		Connection conn = null;	
		

		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("update item set name = ?, description = ?, picture = ?, category_id = ? where id = ?");
			stmt.setString(1, updIte.getName());
			stmt.setString(2, updIte.getDescription());
			stmt.setString(3, updIte.getPicture());
			stmt.setInt(4, updIte.getCategory().getId());
			stmt.setInt(5, updIte.getId()); 
			

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
	
	
	
	
	
	//delete(Item delIte) -----------------------------------------------------------------------------------------
	public void delete(Item delIte) {
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("delete from item where id = ?");

			stmt.setInt(1, delIte.getId());

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
