package de.ckc.franke.ausbildung;

import java.util.ArrayList;
/**
 * Main Program
 */
import java.util.LinkedList;
import java.util.Scanner;

import org.omg.DynamicAny.DynAnyOperations;

import de.ckc.franke.ausbildung.DAO.DAO;
import de.ckc.franke.ausbildung.DAO.ReservationDAO;
import de.ckc.franke.ausbildung.DAO.VehicleDAO;
import de.ckc.franke.ausbildung.io.Io;
import de.ckc.franke.ausbildung.io.Menu;
import de.ckc.franke.ausbildung.model.Reservation;
import de.ckc.franke.ausbildung.model.Vehicle;
import de.ckc.franke.ausbildung.util.Constants;

public class CarPoolManagement {
	Scanner scan = new Scanner(System.in);
	// Utils utils = new Utils();
	public Vehicle vehicle;
	Io io = new Io();
	Menu menu = new Menu(this, io, scan);
	Controller controller = new Controller(this, menu);
	Reservation reservation;
	// private static CarPoolManagement instance;
	//
	// public static CarPoolManagement getInstance() {
	// if (CarPoolManagement.instance == null) {
	// CarPoolManagement.instance = new CarPoolManagement();
	// }
	// return instance;
	// }

	boolean err;
	int mileage = 0;

	public static LinkedList<Vehicle> vehicleList;

	// CarPoolManagement() {}

	// public CarPoolManagement() {
	// this.vehicleList = new LinkedList<>();
	// this.menu = new Menu();
	// this.io = new Io();
	// }

	/**
	 * Program start
	 * 
	 * @param vehicleList
	 */
	void start(LinkedList<Vehicle> vehicleList) {
		CarPoolManagement.vehicleList = vehicleList;

		
		
		DAO.createNewDatabase("CarPoolDB", "jdbc:sqlite:E:\\Daten_Garrit_Franke\\Datenbanken\\CarPoolManagement;");
		VehicleDAO.createNewTable();
		ReservationDAO.createNewTable();
		
		vehicleList = VehicleDAO.selectAll();

		// Data.toJSON(vehicleList);
  
		// Set Dateformat Constants to not lenient for date conversion
		Constants.DATE_LONG.setLenient(false);
		Constants.DATE_SHORT.setLenient(false);
		menu.showMenu();
	}

	/**
	 * Prompts user to enter the make, model and mileage of a vehicle and adds it to the database.
	 * It evaluates the mileage input by checking if it is numeric and not below 0.
	 */
	public void newVehicle() {

		// Get Make input
		System.out.println("Manufacturer:");
		String make = scan.nextLine();

		// Get model input
		System.out.println("Model:");
		String model = scan.nextLine();

		// get mileage input
		mileage = io.getMilageInput();

		Vehicle vehicle = new Vehicle(model, make, mileage);
		vehicle.setId(vehicleList.size());
		vehicleList.addLast(vehicle);
		VehicleDAO.insert(make, model, mileage);
		controller.addVehicleSuccess();
	}
}
