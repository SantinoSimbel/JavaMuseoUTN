package data;

import java.sql.*; //como vamos a usar muchos mejor el *
import java.util.LinkedList;

import entities.Category;

public class DbConnection {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String host = "localhost";
	private String port = "3306";
	private String user = "java";
	private String password = "himitsu";
	private String db = "javaMuseoUTN";

	private Connection conn = null;

	public DbConnection() {
		// registrar el driver de conexion
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// para abrir la conexion:
	public Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {// la crea solo si la conexion no existe o si esta cerrada
				conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void releaseConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
