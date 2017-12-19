package de.ckc.franke.ausbildung.io;

import java.util.LinkedList;
import java.util.Scanner;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.Controller;
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
	
	

	public int getMilageInput() {

		String mileageStr;

		System.out.println("Mileage:");

		mileageStr = scan.nextLine();
		if (Utils.isDigit(mileageStr)) {
			int mileage = Integer.parseInt(mileageStr);
			return mileage;
		} else {
			System.err.println("Please enter a valid number");
			Utils.flush();
			getMilageInput();
			
			//Return 0 if error
			return 0;

		}
	}
	
	public Vehicle findVehicleForReservation(LinkedList<Vehicle> vehicleList) {
		try {
			
			// Enter an ID and find vehicle
			System.out.println("create new reservation for vehicle: ");
			String id = scan.nextLine().trim();

			// retry if ID is not a number
			if (!Utils.isDigit(id)) {
				System.err.println("ID is not valid\n");
				Utils.flush();
				findVehicleForReservation(vehicleList);
			} else {
				// retry if ID is out of bounds
					vehicle = vehicleList.get(Integer.parseInt(id));
			}
			//reservationList = carPoolManagement.getReservationList();
			
			
		} catch (NumberFormatException e) {
			System.err.println("Enter a correct ID");
			Utils.flush();
			listReservationsForVehicle(vehicle);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("ID not found");
			Utils.flush();
			listReservationsForVehicle(vehicle);
			
			
		}
		return vehicle;
	}
	/**
	 * lists all reservations for a given vehicle
	 */
	public void listReservationsForVehicle(Vehicle vehicle) {

		LinkedList<Reservation> reservationList = vehicle.getReservationList();
		if (vehicle.getReservationList().isEmpty()) {
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

	public int getChoice() {

		String choice = scan.nextLine();
		if (Utils.isDigit(choice.trim())) {
			int number = Integer.parseInt(choice.trim());
			return number;
		} else {
			System.err.println("Invalid Input");
			Utils.flush();
			return 0;
		}
	}


}
