package de.ckc.franke.ausbildung.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.model.Vehicle;

public class VehicleDAO extends DAO {
	static String url = "jdbc:sqlite:E:\\Daten_Garrit_Franke\\Datenbanken\\CarPoolDB;";

	/**
	 * Creates new vehicles table with all corresponding fields
	 * 
	 * @author frankeg
	 */
	public static void createNewTable() {

		String tableName = "Vehicles";

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

	public static Vehicle getVehicleById(int id) {

		String sql = "SELECT id, make, model, mileage FROM vehicles";
		Connection conn = connect(url);

		Vehicle vehicle = null;

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {

				vehicle = new Vehicle(rs.getString("model"), rs.getString("make"), rs.getInt("mileage"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vehicle;

	}

	/**
	 * Select all vehicles from the Database and save then to the main vehicleList
	 * 
	 * @return vehicleList
	 */
	public static LinkedList<Vehicle> selectAll() {
		String sql = "SELECT id, make, model, mileage FROM vehicles";
		LinkedList<Vehicle> vehicleList = CarPoolManagement.vehicleList;
		Connection conn = connect(url);
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {

				Vehicle vehicle = new Vehicle(rs.getString("model"), rs.getString("make"), rs.getInt("mileage"));

				
				vehicleList.addLast(vehicle);

				// System.out.println(rs.getInt("id") + "\t" + rs.getString("make") + "\t" +
				// rs.getString("model") + "\t"
				// + rs.getDouble("mileage"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vehicleList;
	}

	/**
	 * Insert new vehicle to database
	 * 
	 * @param make
	 * @param model
	 * @param mileage
	 */
	public static void insert(String make, String model, int mileage) {
		String sql = "INSERT INTO vehicles(make,model,mileage) VALUES(?,?,?)";
		Connection conn = connect(url);
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

	/**
	 * add the currently loaded vehicleList to the database. WARNING: Vehicles added
	 * might be duplicates
	 * 
	 * Prints out "Success" when done
	 * 
	 * @author frankeg
	 */
	public static void updateAll() {

		for (Vehicle vehicle : CarPoolManagement.vehicleList) {
			int id = vehicle.getId();
			String make = vehicle.getMake();
			String model = vehicle.getModel();
			int mileage = vehicle.getMileage();

			System.out.println("Success");
		}

	}
}
