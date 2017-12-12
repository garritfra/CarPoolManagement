package de.ckc.franke.ausbildung.io;

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
	Vehicle vehicle;
	Controller controller;
	CarPoolManagement carPoolManagement;
	public static Menu instance;

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
	public void show() {
		// Cons.clear();
		System.out.println("\nCar Pool Management System");
		System.out.println("-------------------");
		System.out.println("1. create new vehicle");
		System.out.println("2. list all vehicles \n");
		System.out.println("3. create new reservation");
		System.out.println("4. list all reservations");

		try {
			getChoice();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			show();
		}
	}

	public void getChoice() throws Exception {

		String choice = scan.nextLine();
		if (Utils.isDigit(choice.trim())) {
			int number = Integer.parseInt(choice.trim());
			this.selectOption(number);
		} else {
			throw new Exception("Invalid Input");
		}
	}

	/**
	 * checks selection of menu
	 * 
	 * @param userInput
	 */
	public void selectOption(int userInput) {

		switch (userInput) {
		case 1:
			// create new vehicle
			// carPoolManagement.newVehicle();
			carPoolManagement.newVehicle();
			break;

		case 2:
			// list all vehicles
			listVehicles(vehicle, carPoolManagement.vehicleList);
			System.out.println("press enter to continue");
			scan.nextLine();
			show();
			break;

		case 3:
			listVehicles(vehicle, carPoolManagement.vehicleList);
			newReservationDialog();
			break;

		case 4:
			// list all reservations
			listVehicles(vehicle, carPoolManagement.vehicleList);
			listReservations();

			break;
		default:
			System.err.println("Input not valid");
			this.show();
		}
	}

	/**
	 * list all vehicles
	 * 
	 * @param vehicle
	 *            TODO
	 * @param vehicleList
	 *            TODO
	 */
	public void listVehicles(Vehicle vehicle, LinkedList<Vehicle> vehicleList) {

		// if no vehicles were found, return to menu
		if (vehicleList.isEmpty()) {
			System.err.println("No vehicles defined");
			show();
		} else {
			System.out.format(
					"┌────────────────────────────┬────────────────────────────┬────────────────────────────┬───────────────┐%n");
			System.out.format(
					"│ ID                         │ Make                       │ Model                      │ Mileage       │%n");
			System.out.format(
					"├────────────────────────────┼────────────────────────────┼────────────────────────────┼───────────────┤%n");
			String leftAlignFormat = "│ %-26s │ %-26s │ %-26s │ %-13d │ %n";

			for (Vehicle entry : carPoolManagement.vehicleList) {
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
		// Console.clear();

		Vehicle vehicle;

		System.out.println("create new reservation for vehicle: ");
		String id = scan.nextLine().trim();

		// retry if ID is not a number
		if (!Utils.isDigit(id)) {
			System.err.println("ID is not valid\n");
			newReservationDialog();
		} else {
			// retry if ID is out of bounds
			try {
				vehicle = carPoolManagement.vehicleList.get(Integer.parseInt(id));
				boolean valid;

				do { // while input not valid

					// Output selected vehicle and validation
					System.out.println(vehicle.getMake() + " " + vehicle.getModel());
					System.out.println("Is this correct? (Y/N)");

					// Check user validation
					String choice = scan.nextLine();
					if (choice.trim().equals("Y") || choice.trim().equals("y")) {
						// Yes
						carPoolManagement.newReservation(vehicle);
						
						valid = true;
					} else if (choice.trim().equals("N") || choice.trim().equals("n")) {
						// No
						valid = true;
						newReservationDialog();
					} else {
						// Invalid Option

						valid = false;
					}
				} while (!valid);

			} catch (IndexOutOfBoundsException e) {
				System.err.println("Vehicle ID not found\n");
				newReservationDialog();

			}
		}

	}

	/**
	 * lists all reservations for a given vehicle
	 */
	public void listReservations() {
		Vehicle vehicle = null;
		LinkedList<Reservation> reservationList = null;

		try {
			// Enter an ID and find vehicle
			vehicle = io.findVehicle(carPoolManagement); // TODO Fix
			reservationList = vehicle.getReservationList();
		} catch (NumberFormatException e) {
			System.err.println("Enter a correct ID");
			listReservations();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("ID not found");
			listReservations();
		}

		if (vehicle.getReservationList().isEmpty()) {
			System.err.println("No reservations found");
			show();
		}

		System.out.format("┌────────────────────────────────┬────────────────────────────────┐%n");
		System.out.format("│ Reservation from               │ until                          │%n");
		System.out.format("├────────────────────────────────┼────────────────────────────────┤%n");
		String leftAlignFormat = "│ %-30s │ %-30s │%n";

		for (Reservation reservation : reservationList) {
			String beginnDate = reservation.getBeginnDate().toString();
			String endDate = reservation.getEndDate().toString();

			beginnDate = Utils.cutString(beginnDate, Constants.MAX_FIELD_LENGTH);
			endDate = Utils.cutString(endDate, Constants.MAX_FIELD_LENGTH);

			System.out.format(leftAlignFormat, beginnDate, endDate);

		}
		System.out.format("└────────────────────────────────┴────────────────────────────────┘%n");

		System.out.println("press enter to continue");
		scan.nextLine();
		show();
	}

}
