package de.ckc.franke.ausbildung.io;

import java.util.LinkedList;
import java.util.Scanner;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.Controller;
import de.ckc.franke.ausbildung.DAO.ReservationDAO;
import de.ckc.franke.ausbildung.model.Reservation;
import de.ckc.franke.ausbildung.model.Vehicle;
import de.ckc.franke.ausbildung.util.Constants;
import de.ckc.franke.ausbildung.util.Utils;

public class Io {

	CarPoolManagement carPoolManagement;
	Controller controller;
	Vehicle vehicle;
	Scanner scan = new Scanner(System.in);
//	private static Io instance;
	
//	public static Io getInstance() {
//		if (instance == null) {
//			instance = new Io();
//		}
//		return instance;
//	}
	
	
/**
 * Prompts user to enter a mileage integer and validates it
 * <p>
 * checks if
 * <ul>
 * <li>mileage is below 0</li>
 * <li>mileage is a number</li>
 * </ul>
 * 
 * if not so, the method recurses itself
 * @return mileage
 */
	public int getMilageInput() {

		String mileageStr;

		System.out.println("Mileage:");
		mileageStr = scan.nextLine();
		
		
		if (Utils.isDigit(mileageStr)) {
			int mileage = Integer.parseInt(mileageStr);
			
			if(mileage < 0) {
				System.err.println("Mileage can not be below 0");
				Utils.flush();
				return getMilageInput();
			}
			return mileage;
		} else {
			System.err.println("Please enter a valid number");
			Utils.flush();
			return getMilageInput();

		}
	}
	/**
	 * Prompts user for a vehicle ID from the main vehicleList and gets the corresponding vehicle 
	 * <p>
	 * recurses if
	 * <ul>
	 * <li>the ID is not a number</li>
	 * <li>the ID doesn't exist</li>
	 * </ul>
	 * @return {@link Vehicle}
	 */
	public Vehicle findVehicleForReservation() {
		try {
			
			// Enter an ID and find vehicle
			System.out.println("create new reservation for vehicle: ");
			String id = scan.nextLine().trim();

			// retry if ID is not a number
			if (!Utils.isDigit(id)) {
				System.err.println("ID is not valid\n");
				Utils.flush();
				findVehicleForReservation();
			} else {
				// retry if ID is out of bounds
					vehicle = CarPoolManagement.vehicleList.get(Integer.parseInt(id));
			}
			//reservationList = carPoolManagement.getReservationList();
			
			
		} catch (NumberFormatException e) {
			System.err.println("Enter a correct ID");
			Utils.flush();
			listReservationsForVehicle(vehicle);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("ID not found");
			Utils.flush();
			findVehicleForReservation();
			
			
		}
		return vehicle;
	}
	/**
	 * lists all reservations for a given vehicle in a table
	 * <p>
	 * If no reservations were found, a error is displayed instead.
	 * <p>
	 * After reviewing the list, the user can go back to the menu by pressing the enter key
	 * 
	 * @param @{Vehicle} 
	 */
	public void listReservationsForVehicle(Vehicle vehicle) {

		LinkedList<Reservation> reservationList = ReservationDAO.selectAllReservations(vehicle);
		if (reservationList.isEmpty()) {
			System.err.println("No reservations found");
			Utils.flush();
			return;
		}

		System.out.println("\nVehicle " + vehicle.getMake() + " " + vehicle.getModel() + " Rentals");
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
		return;
	}

	/**
	 * Prompts an integer from the user and checks if the input is numeric.
	 * Most often used in menus, when the user has multiple numeric options .
	 * @return choice
	 */
	public int getChoice() {

		String choice = scan.nextLine();
		if (Utils.isDigit(choice.trim())) {
			int number = Integer.parseInt(choice.trim());
			return number;
		} else {
			System.err.println("Input is not numeric");
			Utils.flush();
			return 0;
		}
	}


}
