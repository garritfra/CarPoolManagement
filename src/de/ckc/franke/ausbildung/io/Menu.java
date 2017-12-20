package de.ckc.franke.ausbildung.io;

import java.text.ParseException;
import java.time.DateTimeException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.Controller;
import de.ckc.franke.ausbildung.model.Reservation;
import de.ckc.franke.ausbildung.model.Vehicle;
import de.ckc.franke.ausbildung.util.Constants;
import de.ckc.franke.ausbildung.util.Utils;

public class Menu {

	Scanner scan = new Scanner(System.in);
	Utils utils;
	Io io;
	Controller controller;
	CarPoolManagement carPoolManagement;
	public static Menu instance;
	Vehicle vehicle = new Vehicle(null, null, 0);
	Data data = new Data();

	// public static Menu getInstance() {
	// if (Menu.instance == null) {
	// Menu.instance = new Menu();
	// }
	// return instance;
	// }

	public Menu(CarPoolManagement carPoolManagement, Io io, Scanner scanner) {
		this.scan = scanner;
		this.io = io;
		this.carPoolManagement = carPoolManagement;
	}

	/**
	 * show menu and get user input
	 */
	public void showMenu() {
		// Cons.clear();

		System.out.println("\nCar Pool Management System");
		System.out.println("-------------------");
		System.out.println("1. create new vehicle");
		System.out.println("2. list all vehicles \n");
		System.out.println("3. create new reservation");
		System.out.println("4. list all reservations");
		System.out.println("5. Import or Export Vehicle Data");
		selectOption();

	}

	// public void getChoice() {
	//
	// String choice = scan.nextLine();
	// if (Utils.isDigit(choice.trim())) {
	// int number = Integer.parseInt(choice.trim());
	// this.selectOption(number);
	// } else {
	// System.err.println("Invalid Input");
	// return;
	// }
	// }

	/**
	 * checks selection of menu
	 */
	public void selectOption() {

		int choice = io.getChoice();

		switch (choice) {
		case 1:
			// create new vehicle
			// carPoolManagement.newVehicle();
			carPoolManagement.newVehicle();
			break;

		case 2:
			// list all vehicles
			listVehicles(vehicle, CarPoolManagement.vehicleList);
			System.out.println("press enter to continue");
			scan.nextLine();

			// go back to menu
			showMenu();
			break;

		case 3:
			// list all vehicles for the user
			listVehicles(vehicle, CarPoolManagement.vehicleList);
			// create new reservation
			newReservationDialog();
			break;

		case 4:
			// list all reservations
			listVehicles(vehicle, CarPoolManagement.vehicleList);

			vehicle = io.findVehicleForReservation(CarPoolManagement.vehicleList);

			io.listReservationsForVehicle(vehicle);
			showMenu(); // Show Menu
			break;

		case 5:
			// Open Import/Export Menu
			data.menu();
			
			//Reopen menu when done
			showMenu();
			
			break;

		case 0:
			// getChoice returns 0 if the number is
			showMenu();
			break;

		default:
			// Error is handled at getChoice()
			System.err.println("Invalid input");
			Utils.flush();
			this.showMenu();
		}
	}

	/**
	 * list all vehicles
	 * 
	 * @param vehicle
	 * 
	 * @param vehicleList
	 * 
	 */
	public void listVehicles(Vehicle vehicle, LinkedList<Vehicle> vehicleList) {

		// if no vehicles were found, return to menu
		if (vehicleList.isEmpty()) {
			System.err.println("No vehicles defined");
			showMenu();
		} else {
			System.out.format(
					"┌────────────────────────────┬────────────────────────────┬────────────────────────────┬───────────────┐%n");
			System.out.format(
					"│ ID                         │ Make                       │ Model                      │ Mileage       │%n");
			System.out.format(
					"├────────────────────────────┼────────────────────────────┼────────────────────────────┼───────────────┤%n");
			String leftAlignFormat = "│ %-26s │ %-26s │ %-26s │ %-13d │ %n";

			for (Vehicle entry : CarPoolManagement.vehicleList) {
				int id = entry.getId();
				String make = Utils.cutString(entry.getMake(), Constants.MAX_FIELD_LENGTH);
				String model = Utils.cutString(entry.getModel(), Constants.MAX_FIELD_LENGTH);
				int mileage = entry.getMileage();

				System.out.format(leftAlignFormat, id, make, model, mileage);

			}
			System.out.format(
					"└────────────────────────────┴────────────────────────────┴────────────────────────────┴───────────────┘%n");

		}
	}

	/**
	 * console dialog for new reservations
	 * 
	 * 
	 */
	public void newReservationDialog() {
		Date dateStart;
		Date dateEnd;

		// get vehicle for reservation
		vehicle = showSelectVehicleDialog();

		// get starting date
		dateStart = showSelectStartDialog();

		// get end date
		dateEnd = showSelectEndDialog(dateStart);

		// create reservation
		newReservation(vehicle, dateStart, dateEnd);

	}

	private Vehicle showSelectVehicleDialog() {
		Vehicle vehicle = null;

		System.out.println("create new reservation for vehicle: ");
		String idStr = scan.nextLine().trim();
		// retry if ID is not a number
		if (!Utils.isDigit(idStr)) {
			Utils.flush();
			System.err.println("Enter a valid ID\n");
			Utils.flush();
			newReservationDialog();
		} else {
			int id = Integer.parseInt(idStr);
			// retry if ID is out of bounds
			// -1 becuase index 0
			if (id > CarPoolManagement.vehicleList.size() - 1 || id < 0) {
				System.err.println("Vehicle ID not found\n");
				Utils.flush();
				showSelectVehicleDialog();
			}

			vehicle = CarPoolManagement.vehicleList.get(id);

			// Output selected vehicle and validation
			System.out.println(vehicle.getMake() + " " + vehicle.getModel());

			// Ask user if the vehicle is correct
			System.out.println("Is this correct? (Y/N)");
			String choice = scan.nextLine();
			if (choice.trim().equals("Y") || choice.trim().equals("y")) {
				// Yes
				// return the vehicle
				return vehicle;
			} else if (choice.trim().equals("N") || choice.trim().equals("n")) {
				// No
				showSelectVehicleDialog();
			} else {
				// Invalid Option
				Utils.flush();
				System.err.println("Invalid input");
				showSelectVehicleDialog();
			}

		}

		return vehicle;
	}

	private Date showSelectStartDialog() {
		System.out.println("Enter a starting date in format dd.MM.yyyy (HH:mm) (enter 'c' for current date)");
		String dateInput = scan.nextLine().trim();
		Date dateStart;

		if (dateInput.toString().toLowerCase().startsWith("c")) {
			// use current time as beginning
			dateStart = Utils.getCurrentTime();
			return dateStart;
		} else {
			try {
				// Format time from string to date
				dateStart = Utils.convertDate(dateInput).getTime();
				
				//if date is in the past
				if(!Utils.isInFuture(dateStart)) {
					System.err.println("Date must be in the future");
					Utils.flush();
					return showSelectStartDialog();
				}

				Utils.flush();
				return dateStart;

			} catch (ParseException e) {
				System.err.println("date has an invalid format");
				Utils.flush();
				return showSelectStartDialog();
			}
		}

	}

	private Date showSelectEndDialog(Date dateStart) {
		Date dateEnd = null;
		// Output options
		System.out.println("Select a duration or enter an end date in format dd.MM.yyyy (HH:mm)");
		System.out.println("1. One Day");
		System.out.println("2. One Week");
		System.out.println("3. One Month");
		System.out.println("4. One Year");

		// get user input
		String dateInput = scan.nextLine().trim();

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
			
			try {
				dateEnd = validateDateFormat(dateInput);
				if(!Utils.isInFuture(dateEnd)) {
					System.err.println("Date can not be in the past");
					Utils.flush();
					return showSelectEndDialog(dateStart);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				
				
				System.err.println("Invalid date format");
				Utils.flush();
				return showSelectEndDialog(dateStart);
			}

			break;
		}

		return dateEnd;

	}

	public Date validateDateFormat(String dateInput) throws ParseException {

		Date time = Utils.convertDate(dateInput).getTime();

		return time;
	}

	public void newReservation(Vehicle vehicle, Date dateStart, Date dateEnd) {

		Reservation reservation = new Reservation(dateStart, dateEnd, vehicle);

		LinkedList<Reservation> reservationList = vehicle.getReservationList();

			//If reservation is not valid
			if(!Utils.validateReservation(reservation, vehicle, dateStart, dateEnd, reservationList)) {
				showSelectEndDialog(dateStart);
			}
			
			

		

		reservationList.add(reservation);

		vehicle.setReservationList(reservationList);

		System.out.println("Reservation has been saved");
		showMenu();

	}

}
