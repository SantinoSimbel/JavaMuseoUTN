package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


import entities.Presentation;
import entities.Review;
import entities.User;

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
				stmt = conn.prepareStatement("select r.user_id, r.content, r.punctuation, u.name, u.surname "
												+ "from review r "
												+ "inner join user u on u.id = r.user_id "
												+ "where presentation_id = ?");
	
				stmt.setInt(1, pre.getId());
				
				rs = stmt.executeQuery();
				
				while(rs!= null && rs.next()) {
					User usr = new User();
					usr.setId(rs.getInt("user_id"));
					usr.setName(rs.getString("name"));
					usr.setSurname(rs.getString("surname"));
					
					Review rev = new Review();
					rev.setUser(usr);
					rev.setContent(rs.getString("content"));
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
	
	
	

	//search(int idPre, int idUsr) -----------------------------------------------------------------------------------------------------
	public Review search(int idPre, int idUsr){
			
			PreparedStatement stmt = null;
			ResultSet rs = null;
			Connection conn = null;
			
			try {
				conn= db.getConnection();
				Review rev = null;
				stmt = conn.prepareStatement("select r.user_id, r.content, r.punctuation, u.name, u.surname "
												+ "from review r "
												+ "inner join user u on u.id = r.user_id "
												+ "where presentation_id = ? and user_id = ?");
	
				stmt.setInt(1, idPre);
				stmt.setInt(2, idUsr);
				
				rs = stmt.executeQuery();
				
				if(rs!= null && rs.next()) {
					User usr = new User();
					usr.setId(rs.getInt("user_id"));
					usr.setName(rs.getString("name"));
					usr.setSurname(rs.getString("surname"));
					
					rev = new Review();
					rev.setUser(usr);
					rev.setContent(rs.getString("content"));
					rev.setPunctuation(rs.getDouble("punctuation"));
			        
				}
				
				return rev;
				
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
	public void add(Review newRev, int PreId, int usrId) {
		PreparedStatement stmt = null;
		ResultSet keyRs = null;
		Connection conn = null;
		
		
		try {
			conn= db.getConnection();
			
			stmt = conn.prepareStatement("insert into review(user_id, presentation_id, content, punctuation ) values(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); 
			stmt.setInt(1, usrId);
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
	public void update(Review updRev, int preId, int usrId) {
		PreparedStatement stmt = null;
		Connection conn = null;	
		

		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("update review set content = ?, punctuation = ? where user_id = ? and presentation_id = ?");
			stmt.setString(1, updRev.getContent());
			stmt.setDouble(2, updRev.getPunctuation());
			stmt.setInt(3, usrId);
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
				stmt = conn.prepareStatement("delete from review where user_id = ? and presentation_id = ?");

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

