package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


import entities.Presentation;
import entities.Review;

public class ReviewDAO {

	private DbConnection db = new DbConnection();
	

	
	//searchByPresentation(Presentation pre) -----------------------------------------------------------------------------------------------------
	public LinkedList<Review> searchByPresentation(Presentation pre){
			
			PreparedStatement stmt = null;
			ResultSet rs = null;
			Connection conn = null;
			
			try {
				conn= db.getConnection();
				LinkedList<Review> reviews = new LinkedList<>();
				stmt = conn.prepareStatement("select user_id, content, punctuation from review where presentation_id = ?");
	
				stmt.setInt(1, pre.getId());
				
				rs = stmt.executeQuery();
				
				while(rs!= null && rs.next()) {
					Review rev = new Review();
					rev.setUserId(rs.getInt("user_id"));
					rev.setContent("content");
					rev.setPunctuation(rs.getDouble("punctuation"));
			        
					reviews.add(rev);
				}
				
				return reviews;
				
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
	
	
	
	    
	


	
	//add(Review newRev, int preId) --------------------------------------------------------------------------------------------------------------------
	public void add(Review newRev, int PreId) {
		PreparedStatement stmt = null;
		ResultSet keyRs = null;
		Connection conn = null;
		
		
		try {
			conn= db.getConnection();
			
			stmt = conn.prepareStatement("insert into review(user_id, presentation_id, content, punctuation ) values(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); 
			stmt.setInt(1, newRev.getUserId());
			stmt.setInt(2, PreId);
			stmt.setString(3, newRev.getContent());
			stmt.setDouble(4, newRev.getPunctuation());

			
			stmt.executeUpdate();
			
			keyRs = stmt.getGeneratedKeys();
			
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
	
	
	
	
	
	//update(Review updRev, int preId) -----------------------------------------------------------------------------------------
	public void update(Review updRev, int preId) {
		PreparedStatement stmt = null;
		Connection conn = null;	
		

		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("update item set content = ?, punctuation = ? where user_id = ? and presentation_id = ?");
			stmt.setString(1, updRev.getContent());
			stmt.setDouble(2, updRev.getPunctuation());
			stmt.setInt(3, updRev.getUserId());
			stmt.setInt(4, preId);
			

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
	
	
	
	
	
	
	//delete(int userId, int preId) -----------------------------------------------------------------------------------------
		public void delete(int userId, int preId) {
			PreparedStatement stmt = null;
			Connection conn = null;
			
			try {
				conn= db.getConnection();
				stmt = conn.prepareStatement("delete from item where user_id = ? and presentation_id = ?");

				stmt.setInt(1, userId);
				stmt.setInt(2, preId);

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

