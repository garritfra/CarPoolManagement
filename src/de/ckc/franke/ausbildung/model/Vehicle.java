package de.ckc.franke.ausbildung.model;

import java.util.LinkedList;
import java.util.Scanner;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.Controller;
import de.ckc.franke.ausbildung.io.Io;
import de.ckc.franke.ausbildung.io.Menu;
import de.ckc.franke.ausbildung.util.Constants;
import de.ckc.franke.ausbildung.util.Utils;

public class Vehicle {
	private String model;
	private String make;
	private int mileage;
	public int id;
	private LinkedList<Reservation> reservationList;
	public Scanner scan = new Scanner(System.in);
	public CarPoolManagement carPoolManagement;
	Io io;
	public Menu menu;
	public Controller controller;
	Utils utils;
	public LinkedList<Vehicle> vehicleList;

	/**
	 * Vehicle Constructor
	 * 
	 * @param model
	 * @param make
	 * @param mileage
	 */

	public Vehicle(String model, String make, int mileage) {

		// this.id = id;
		this.model = model;
		this.make = make;
		this.mileage = mileage;
		this.reservationList = new LinkedList<>();
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LinkedList<Reservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(LinkedList<Reservation> reservationList) {
		this.reservationList = reservationList;
	}

	/**
	 * lists all reservations for a given vehicle
	 */
	public void listReservations(Vehicle vehicle, CarPoolManagement carPoolManagement) {

		try {
			
			System.out.println("create new reservation for vehicle: ");
			String id = scan.nextLine().trim();

			// retry if ID is not a number
			if (!Utils.isDigit(id)) {
				System.err.println("ID is not valid\n");
			} else {
				// retry if ID is out of bounds
					vehicle = carPoolManagement.vehicleList.get(Integer.parseInt(id));
			}
			// Enter an ID and find vehicle
			//reservationList = carPoolManagement.getReservationList();
			
			
		} catch (NumberFormatException e) {
			System.err.println("Enter a correct ID");
			listReservations(vehicle, carPoolManagement);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("ID not found");
			listReservations(vehicle, carPoolManagement);
		}

		if (vehicle.getReservationList().isEmpty()) {
			System.err.println("No reservations found");
			return;
		}

		System.out.format("┌────────────────────────────────┬────────────────────────────────┐%n");
		System.out.format("│ Reservation from               │ until                          │%n");
		System.out.format("├────────────────────────────────┼────────────────────────────────┤%n");
		String leftAlignFormat = "│ %-30s │ %-30s │%n";

		for (Reservation reservation : vehicle.reservationList) {
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

	public int createID(LinkedList<Vehicle> vehicleList) {

		this.id = vehicleList.size();
		

		return this.id;

	}

}
