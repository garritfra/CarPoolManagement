package de.ckc.franke.ausbildung;

import java.text.ParseException;
import java.util.Date;
/**
 * Main Program
 */
import java.util.LinkedList;
import java.util.Scanner;

import de.ckc.franke.ausbildung.io.Data;
import de.ckc.franke.ausbildung.io.Io;
import de.ckc.franke.ausbildung.io.Menu;
import de.ckc.franke.ausbildung.model.Reservation;
import de.ckc.franke.ausbildung.model.Vehicle;
import de.ckc.franke.ausbildung.util.Constants;
import de.ckc.franke.ausbildung.util.Utils;
public class CarPoolManagement {
	Scanner scan = new Scanner(System.in);
	// Utils utils = new Utils();
	Vehicle vehicle;
	Io io = Io.getInstance();
	Menu menu = new Menu(this, io, scan);
	Controller controller = new Controller(this, menu);
	Reservation reservation;
//	private static CarPoolManagement instance;
//
//	 public static CarPoolManagement getInstance() {
//	 if (CarPoolManagement.instance == null) {
//	 CarPoolManagement.instance = new CarPoolManagement();
//	 }
//	 return instance;
//	 }

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
		this.vehicleList = vehicleList;
//		Data.toJSON(vehicleList);
		
		//Set Dateformat Constants to not lenient for date conversion
		Constants.DATE_LONG.setLenient(false);
		Constants.DATE_SHORT.setLenient(false);
		menu.show();
	}

	/**
	 * Creates a new vehicle
	 */
	public void newReservation(Vehicle vehicle) {
		Date dateStart = null;
		Date dateEnd = null;

		System.out.println("Enter a starting date in format dd.MM.yyyy (HH:mm) (enter 'c' for current date)");
		String dateInput = scan.nextLine().trim();

		if (dateInput.toString().toLowerCase().startsWith("c")) {
			// use current time as beginning
			dateStart = Utils.getCurrentTime();
		} else {
			try {
				// Format time from string to date
				
				Utils.flush();
				dateStart = Utils.convertDate(dateInput).getTime();
				Utils.flush();
				
			} catch (ParseException e) {
				Utils.flush();
				System.err.println("date has an invalid format");
				Utils.flush();
				newReservation(vehicle);
			}
		}

		dateEnd = reservationEndMenu(dateInput, dateStart);
		Reservation reservation = new Reservation(dateStart, dateEnd, vehicle);

		LinkedList<Reservation> reservationList = vehicle.getReservationList();

		try {
			Utils.validateReservation(reservation, vehicle, dateStart, dateEnd, reservationList);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			newReservation(vehicle);
		}

		vehicle.getReservationList().add(reservation);
		System.out.println("Reservation has been saved");

		menu.show();

	}

	private Date reservationEndMenu(String dateInput, Date dateStart) {

		Date dateEnd = null;
		// Output options
		System.out.println("Select a duration or enter an end date in format dd.MM.yyyy (HH:mm)");
		System.out.println("1. One Day");
		System.out.println("2. One Week");
		System.out.println("3. One Month");
		System.out.println("4. One Year");

		// get user input
		dateInput = scan.nextLine().trim();

		switch (dateInput) {

		// reservation for one day
		case "1":
			dateEnd = Utils.addDays(dateStart, 1);
			break;

		// reservation for one week
		case "2":
			dateEnd = Utils.addDays(dateStart, 7);
			break;
		// reservation for one month
		case "3":
			dateEnd = Utils.addDays(dateStart, 30);
			break;

		// reservation for one year
		case "4":
			dateEnd = Utils.addDays(dateStart, 365);
			break;

		// user entered a date
		default:
			dateEnd = validateDateFormat(dateInput);
			break;
		}
		return dateEnd;

	}

	/**
	 * 
	 * @param dateInput
	 * 
	 */
	private Date validateDateFormat(String dateInput) {
		Date time = null;

		try {
			time = Utils.convertDate(dateInput).getTime();
		} catch (Exception e) {
			Utils.flush();
			System.err.println(e.getMessage());
			newReservation(vehicle);
		}

		return time;
	}

	public void newVehicle() {

		System.out.println("Manufacturer:");

		String make = scan.nextLine();

		System.out.println("Model:");
		String model = scan.nextLine();

		try {
			mileage = io.getMilageInput();
		} catch (Exception e) {
			Utils.flush();
			System.err.println(e.getMessage());
			newVehicle();
		}
		Vehicle vehicle = new Vehicle(model, make, mileage);
		vehicle.setId(vehicleList.size());
		vehicleList.addLast(vehicle);
		controller.addVehicleSuccess();
	}
	
	

}
