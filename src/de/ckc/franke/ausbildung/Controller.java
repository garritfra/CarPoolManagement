package de.ckc.franke.ausbildung;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.Scanner;

import de.ckc.franke.ausbildung.io.Io;
import de.ckc.franke.ausbildung.io.Menu;
import de.ckc.franke.ausbildung.model.Reservation;
import de.ckc.franke.ausbildung.model.Vehicle;
import de.ckc.franke.ausbildung.util.Constants;
import de.ckc.franke.ausbildung.util.Utils;

public class Controller {

	Scanner scan = new Scanner(System.in);
	CarPoolManagement carPoolManagement;
	Utils utils = new Utils();
	Io io;
	Menu menu;
	Vehicle vehicle;

	public Controller(CarPoolManagement carPoolManagement) {
		this.carPoolManagement = carPoolManagement;
	}

	/**
	 * show menu and get user input
	 */
	public void menu() {
		menu.show();
		menu.getChoice();
	}



	/**
	 * print menu
	 */
	

	/**
	 * vehicle was successfully saved
	 */
	public void addVehicleSuccess() {

		System.out.println("Vehicle has been saved");

		boolean valid = false; // initialize boolean
		do { // while input is not valid
			System.out.println("Do you want to add another vehicle? (Y/N)");
			String choice = scan.nextLine();

			if (choice.trim().equals("Y") || choice.trim().equals("y")) {
				vehicle.newVehicle();
			} else if (choice.trim().equals("N") || choice.trim().equals("n")) {
				menu();
				valid = true;
			} else {
				System.err.println("Invalid");
			}
		} while (valid == false);
	}

	/**
	 * list all vehicles
	 */


	/**
	 * console dialog for new reservations
	 * 
	 * @throws ParseException
	 */
	public void newReservationDialog() {
		// Console.clear();

		Vehicle vehicle;

		System.out.println("create new reservation for vehicle: ");
		String id = scan.nextLine().trim();

		// retry if ID is not a number
		if (!utils.isDigit(id)) {
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





}
