package data;

import java.sql.*; //como vamos a usar muchos mejor el *
import java.util.LinkedList;

import entities.Category;

public class DbHandler {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String host = "localhost";
	private String port = "3306";
	private String user = "java";
	private String password = "himitsu";
	private String db = "javaMuseoUTN";

	private Connection conn = null;

	public DbHandler() {
		// registrar el driver de conexion
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// para abrir la conexion:
	private Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {// la crea solo si la conexion no existe o si esta cerrada
				conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	private void releaseConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// es un metodo normal, tiene misma estructura que un
	// public String mostrarDatos()
	public LinkedList<Category> getAllCategories() {
		LinkedList<Category> categories = new LinkedList<>();
		// definimos estos 2 aca para que pueda encontrlos el finally
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		// crear el statement (sentencia)
		try {
			conn = this.getConnection(); // nos conectamos
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
				this.releaseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

}
