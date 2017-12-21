package de.ckc.franke.ausbildung.DAO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract class DAO {
	public static void createNewDatabase(String fileName, String url) {

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}
	}

	public static Connection connect(String url) {
		Connection conn = null;
		try {
			// db parameters
			// String url = "jdbc:sqlite:" + filePath;
			// create a connection to the database
			conn = DriverManager.getConnection(url);

			// System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;

		// try {
		// conn.close();
		// } catch (SQLException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// } finally {
		// try {
		// if (conn != null) {
		// conn.close();
		// }
		// } catch (SQLException ex) {
		// System.out.println(ex.getMessage());
		// }
	}
}
