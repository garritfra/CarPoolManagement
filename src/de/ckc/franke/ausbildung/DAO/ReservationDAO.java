package de.ckc.franke.ausbildung.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.model.Reservation;
import de.ckc.franke.ausbildung.model.Vehicle;

/**
 * Database operations for the reservation table
 * 
 * @author frankeg
 * 
 *
 */
public class ReservationDAO extends DAO {

	static String url = "jdbc:sqlite:E:\\Daten_Garrit_Franke\\Datenbanken\\CarPoolDB;";

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

	public static LinkedList<Reservation> selectAllReservations(Vehicle vehicle) {
		Connection conn = connect(url);
		LinkedList<Reservation> reservationList = null;
		String sqlVehicle = "SELECT id, make, model, mileage FROM Vehicles WHERE id = " + vehicle.getId();
		ResultSet rsVehicle = null;
		ResultSet rsReservation = null;
		reservationList = vehicle.getReservationList();
		LinkedList<Reservation> reservationsToAdd = new LinkedList<Reservation>();
		Reservation reservation = null;

		try {
			Statement stmtVehicle = conn.createStatement();
			rsVehicle = stmtVehicle.executeQuery(sqlVehicle);
			String sqlReservation = "SELECT id, beginDate, endDate FROM Reservations WHERE vehicleID = "
					+ rsVehicle.getInt("id");

			Statement stmtReservation;
			stmtReservation = conn.createStatement();
			rsReservation = stmtReservation.executeQuery(sqlReservation);
			// vehicle = CarPoolManagement.vehicleList.get(vehicle.getId());

			// loop through the result set
			while (rsReservation.next()) {
				Date dateStart = rsReservation.getDate("beginDate");
				Date dateEnd = rsReservation.getDate("endDate");
				reservation = new Reservation(dateStart, dateEnd, vehicle);
				reservationsToAdd.add(reservation);
				
				// Check if reservation already exists
//				for (Reservation r1 : reservationList) {
//					if (r1.equals(reservation)) {
//						reservationsToAdd.addLast(reservation);
//					}					
//				}
				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		reservationList.addAll(reservationsToAdd);
		return reservationList;

	}
}
