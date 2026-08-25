package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.User;

public class UserDAO {
	private DbConnection db = new DbConnection();

	// es un metodo normal, tiene misma estructura que un
	// public String mostrarDatos()
	public LinkedList<User> list() {
		LinkedList<User> users = new LinkedList<>();
		// definimos estos 2 aca para que pueda encontrlos el finally
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		// crear el statement (sentencia)
		try {
			conn = db.getConnection(); // nos conectamos
			stmt = conn.createStatement();
			// ejecutar la sentencia (y guardamos el resultado en resulset)
			rs = stmt.executeQuery("select id, dni, name, surname, email, password, role from user");

			// recorremos el resulset para mapearlo a objetos
			while (rs != null && rs.next()) {
				User u = new User();

				u.setId(rs.getInt("id"));
				u.setDni(rs.getString("dni"));
				u.setName(rs.getString("name"));
				u.setSurname(rs.getString("surname"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));
				u.setRole(rs.getString("role"));

				users.add(u); // lo guardo en la linked list que cree al inicio del metodo
			}
			return users;

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
	
	public User search(User c) { //recibimos un User que solo tenga el id
		//definimos estos 2 aca para que pueda encontrlos el finally
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		//crear el prepareStatement (la consulta)
		try {
			//lo definimos aca asi si nunca entra al try puedo devolverlo null (en caso de querer avisar que esta nulo, hacerlo en la interfaz grafica!)
			conn= db.getConnection();
			User use = null;
			stmt = conn.prepareStatement("select id, dni, name, surname, email, password, role from user where id = ?");
			//al primer signo de pregunta(1) le asigno el resultado de getId() (el id que ingreso el usuario)
			stmt.setInt(1, c.getId());
			
			rs = stmt.executeQuery();
			
			if(rs!= null && rs.next()) {
				use = new User();
				use.setId(rs.getInt("id"));
				use.setDni(rs.getString("dni"));
				use.setName(rs.getString("name"));
				use.setSurname(rs.getString("surname"));
				use.setEmail(rs.getString("email"));
				use.setPassword(rs.getString("password"));
				use.setRole(rs.getString("role"));
			}
			
			return use;
			
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
	
	public void add(User newUser) {
		PreparedStatement stmt = null;
		//le pongo keyRs para tener nombre mas amigable con lo que estoy haciendo. (en el stmt no hace falta saber que es un preparedStatement.)
		ResultSet keyRs = null;
		Connection conn = null;
		
		//crear el prepareStatement (la consulta)
		try {
			conn= db.getConnection();
			//no cortar la sentencias(aca no esta cortada pero en el video si)
			stmt = conn.prepareStatement("insert into user(dni,name,surname,email,password,role) values(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS); //eso ultimo devuelve el id generado por la base de datos
			stmt.setString(1, newUser.getDni());
			stmt.setString(2, newUser.getName());
			stmt.setString(3, newUser.getSurname());
			stmt.setString(4, newUser.getEmail());
			stmt.setString(5, newUser.getPassword());
			stmt.setString(6, newUser.getRole());
			
			stmt.executeUpdate();// devuelve la cantidad de filas actualizadas (se usa para insert, update o delete, cuando quiera saber cuantas filas afecte con mi operacion
			// stmt.execute(); devuelve un boolean sobre si se genero un resulset o no
			
			keyRs = stmt.getGeneratedKeys();
			
			if(keyRs!= null && keyRs.next()) {
				newUser.setId(keyRs.getInt(1)); //ese 1 porque no se el nombre de la columna que devuelve. ademas devuelve una sola columna entonces le pido el dato de esa columna
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
	
	public void update(User updUser) {
		PreparedStatement stmt = null;
		Connection conn = null;	
		
		//crear el prepareStatement (la consulta)
		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("update user set dni = ?, name = ?, surname = ?, email = ?, password = ?, role = ? where id = ?");
			stmt.setString(1, updUser.getDni());
			stmt.setString(2, updUser.getName());
			stmt.setString(3, updUser.getSurname());
			stmt.setString(4, updUser.getEmail());
			stmt.setString(5, updUser.getPassword());
			stmt.setString(6, updUser.getRole());
			stmt.setInt(7, updUser.getId());

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
	
	public void delete(User delUser) {
		PreparedStatement stmt = null;
		Connection conn = null;
		
		try {
			conn= db.getConnection();
			stmt = conn.prepareStatement("delete from user where id = ?");

			stmt.setInt(1, delUser.getId());

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

