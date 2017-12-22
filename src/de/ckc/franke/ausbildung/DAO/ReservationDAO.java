package de.ckc.franke.ausbildung.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database operations for the reservation table
 * 
 * @author frankeg
 * 
 *
 */
public class ReservationDAO extends DAO {

	static String url = "jdbc:sqlite:E:\\Daten_Garrit_Franke\\Datenbanken\\Reservations;";

	/**
	 * Create a new reservation table if it doesn't exist
	 */
	public static void createNewTable() {

		String tableName = "Reservations";

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n" + "	id integer PRIMARY KEY,\n"
				+ "	vehicleID int NOT NULL,\n" + "	beginDate Date NOT NULL,\n" + "	endDate Date\n" + ");";
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

	/**
	 * Insert new vehicle to database
	 * 
	 * @param vehicleID
	 * @param beginDate
	 * @param endDate
	 */
	public static void insert(int vehicleID, java.sql.Date beginDate, java.sql.Date endDate) {
		String sql = "INSERT INTO Reservations(vehicleID,beginDate,endDate) VALUES(?,?,?)";
		Connection conn = connect(url);
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, vehicleID);
			pstmt.setDate(2, beginDate);
			pstmt.setDate(3, endDate);
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
}
