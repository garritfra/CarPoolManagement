package de.ckc.franke.ausbildung.DAO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO Superclass
 * <p>
 * used by:
 * <ul>
 * <li>vehicleDAO</li>
 * <li>reservationDAO</li>
 * </ul>
 * 
 * @author frankeg
 *
 */
public abstract class DAO {
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

	/**
	 * Creates new vehicles table with all corresponding fields.
	 * You can append columns via the array. An attribute should look like this:
	 * <p>
	 * <code>"id integer PRIMARY KEY"</code>
	 * <p>
	 * <code>"model text NOT NULL"</code>
	 * 
	 * @author frankeg
	 */
	public static void createNewTable(String tableName, ArrayList<String> attributes) {

		String urlPrefix = "jdbc:sqlite:E:\\Daten_Garrit_Franke\\Datenbanken\\";
		String attrPrefix = " ";
		String attrSuffix = ",\n";
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n";
		// SQL statement for creating a new table
		// String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n" + " id
		// integer PRIMARY KEY,\n"
		// + " make text NOT NULL,\n" + " model text NOT NULL,\n" + " mileage integer\n"
		// + ");";
		Connection conn = null;

		String url = urlPrefix + tableName;
		int iteration = 1;
		for (String attr : attributes) {

			// Add all attributes to the statement
			sql += attrPrefix;
			sql += attr;
			if (iteration != attributes.size()){
				sql += attrSuffix;
			}
			iteration++;
		}
		// Add closing String to the attribute
		sql += ");";

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

	/**
	 * Connects to a database via URL This method should not be used on its own. It
	 * should only be executed for other DAO Operations
	 * 
	 * WARNING: Connection has to be closed after the method executes
	 * 
	 * 
	 * @param url
	 * @return
	 */
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
