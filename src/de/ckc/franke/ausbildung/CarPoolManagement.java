package de.ckc.franke.ausbildung;

/**
 * Main Program
 */
import java.util.LinkedList;
import java.util.Scanner;

import de.ckc.franke.ausbildung.io.Io;
import de.ckc.franke.ausbildung.io.Menu;
import de.ckc.franke.ausbildung.model.Reservation;
import de.ckc.franke.ausbildung.model.Vehicle;
import de.ckc.franke.ausbildung.util.Constants;
import de.ckc.franke.ausbildung.util.Utils;

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
	 */
	void start(LinkedList<Vehicle> vehicleList) {
		CarPoolManagement.vehicleList = vehicleList;
		// Data.toJSON(vehicleList);

		// Set Dateformat Constants to not lenient for date conversion
		Constants.DATE_LONG.setLenient(false);
		Constants.DATE_SHORT.setLenient(false);
		menu.showMenu();
	}

	public void newVehicle() {

		System.out.println("Manufacturer:");

		String make = scan.nextLine();

		System.out.println("Model:");
		String model = scan.nextLine();

		mileage = io.getMilageInput();

		Vehicle vehicle = new Vehicle(model, make, mileage);
		vehicle.setId(vehicleList.size());
		vehicleList.addLast(vehicle);
		controller.addVehicleSuccess();
	}
}
