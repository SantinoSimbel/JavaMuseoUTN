package data;

import java.sql.Connection;
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
	//falta el search o findOne
}

