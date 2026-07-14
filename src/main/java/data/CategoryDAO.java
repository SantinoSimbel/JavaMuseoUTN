package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Category;

public class CategoryDAO {

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
			rs = stmt.executeQuery("select * from category");

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
	//falta el search o findOne (si es que hace falta)
	
	public void add(Category newCat) {
		PreparedStatement stmt = null;
		//le pongo keyRs para tener nombre mas amigable con lo que estoy haciendo. (en el stmt no hace falta saber que es un preparedStatement.)
		ResultSet keyRs = null;
		Connection conn = null;
		
		//crear el prepareStatement (la consulta)
		try {
			conn= db.getConnection();
			//no cortar la sentencias(aca no esta cortada pero en el video si)
			stmt = conn.prepareStatement("insert into category(name) values(?)", Statement.RETURN_GENERATED_KEYS); //eso ultimo devuelve el id generado por la base de datos
			stmt.setString(1, newCat.getName());
			
			stmt.executeUpdate();// devuelve la cantidad de filas actualizadas (se usa para insert, update o delete, cuando quiera saber cuantas filas afecte con mi operacion
			// stmt.execute(); devuelve un boolean sobre si se genero un resulset o no
			
			keyRs = stmt.getGeneratedKeys();
			
			if(keyRs!= null && keyRs.next()) {
				newCat.setId(keyRs.getInt(1)); //ese 1 porque no se el nombre de la columna que devuelve. ademas devuelve una sola columna entonces le pido el dato de esa columna
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

