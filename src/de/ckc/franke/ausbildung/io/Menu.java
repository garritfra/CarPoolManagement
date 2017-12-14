package de.ckc.franke.ausbildung.io;

import java.util.LinkedList;
import java.util.Scanner;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.Controller;
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
	public void show() {
		// Cons.clear();
		
		System.out.println("\nCar Pool Management System");
		System.out.println("-------------------");
		System.out.println("1. create new vehicle");
		System.out.println("2. list all vehicles \n");
		System.out.println("3. create new reservation");
		System.out.println("4. list all reservations");
		System.out.println("5. Import or Export Vehicle Data");

		selectOption(io.getChoice());
		show();
	}

//	public void getChoice() {
//
//		String choice = scan.nextLine();
//		if (Utils.isDigit(choice.trim())) {
//			int number = Integer.parseInt(choice.trim());
//			this.selectOption(number);
//		} else {
//			System.err.println("Invalid Input");
//			return;
//		}
//	}

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

			// go back to menu
			show();
			break;

		case 3:
			// list all vehicles for the user
			listVehicles(vehicle, carPoolManagement.vehicleList);
			// create new reservation
			newReservationDialog();
			break;

		case 4:
			// list all reservations
			listVehicles(vehicle, carPoolManagement.vehicleList);
			
			vehicle = io.findVehicleForReservation(carPoolManagement.vehicleList);
			
			
			io.listReservationsForVehicle(vehicle);
			show();	//Show Menu
			break;
			
		case 5:
			//Open Import/Export Menu
			data.menu(io);
			break;
		
		default:

			// Handle invalid inputs
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
			Utils.flush();
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
				Utils.flush();
				System.err.println("Vehicle ID not found\n");
				newReservationDialog();

			}
		}

	}
	



}
