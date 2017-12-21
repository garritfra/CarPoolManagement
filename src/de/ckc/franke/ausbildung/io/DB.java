package de.ckc.franke.ausbildung.io;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	static String url = "jdbc:sqlite:E:\\Daten_Garrit_Franke\\Datenbanken\\Vehicles;";

	public static void createNewDatabase(String fileName) {

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

	public static void createNewTable(String tableName) {

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n" + "	id integer PRIMARY KEY,\n"
				+ "	make text NOT NULL,\n" + "	model text NOT NULL,\n" + "	mileage integer\n" + ");";
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			// create a new table
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static Connection connect() {
		Connection conn = null;
		try {
			// db parameters
			// String url = "jdbc:sqlite:" + filePath;
			// create a connection to the database
			conn = DriverManager.getConnection(url);

			System.out.println("Connection to SQLite has been established.");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// } finally {
			// try {
			// if (conn != null) {
			//
			// conn.close();
			// }
			// } catch (SQLException ex) {
			// System.out.println(ex.getMessage());
			// }
		}
		return conn;
	}

	public static void selectAll() {
		String sql = "SELECT id, make, model, mileage FROM vehicles";

		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getString("make") + "\t" + rs.getString("model") + "\t"
						+ rs.getDouble("mileage"));
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void insert(String make, String model, int mileage) {
		String sql = "INSERT INTO vehicles(make,model,mileage) VALUES(?,?,?)";
		Connection conn = connect();
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, make);
			pstmt.setString(2, model);
			pstmt.setInt(3, mileage);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void delete() {

	}

}
