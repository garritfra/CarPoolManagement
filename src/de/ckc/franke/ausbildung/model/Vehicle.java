package de.ckc.franke.ausbildung.model;

import java.util.LinkedList;
import java.util.Scanner;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.Controller;
import de.ckc.franke.ausbildung.io.Io;
import de.ckc.franke.ausbildung.io.Menu;
import de.ckc.franke.ausbildung.util.Console;
import de.ckc.franke.ausbildung.util.Constants;
import de.ckc.franke.ausbildung.util.Utils;

public class Vehicle {
	private String model;
	private String make;
	private int mileage;
	public int id;
	private LinkedList<Reservation> reservationList;
	Scanner scan;
	CarPoolManagement carPoolManagement;
	Io io;
	Menu menu;
	Controller controller;
	Utils utils;

	/**
	 * Vehicle Constructor
	 * 
	 * @param model
	 * @param make
	 * @param mileage
	 */
	public Vehicle(String model, String make, int mileage) {
		
		//this.id = id;
		this.model = model;
		this.make = make;
		this.mileage = mileage;
		this.setReservationList(new LinkedList<>());
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
	
	public Vehicle find() {

		System.out.println("Enter a vehicle ID:");
		String id = scan.nextLine(); // receive id as string

		// return vehicle that matches the index
		return carPoolManagement.vehicleList.get(Integer.parseInt(id));
	}
	
	public void newVehicle() {
		Console.clear();

		System.out.println("Manufacturer:");

		String make = scan.nextLine();

		System.out.println("Model:");
		String model = scan.nextLine();

		carPoolManagement.getMileageInput();
		Vehicle vehicle = new Vehicle(model, make, mileage);
		vehicle.setId(carPoolManagement.vehicleList.size());
		carPoolManagement.vehicleList.add(vehicle);
		controller.addVehicleSuccess();
	}

	public void list() {

		// if no vehicles were found, return to menu
		if (carPoolManagement.vehicleList.isEmpty()) {
			System.err.println("No vehicles defined");
			menu.show();
		} else {
			System.out.format(
					"┌────────────────────────────┬────────────────────────────┬────────────────────────────┬───────────────┐%n");
			System.out.format(
					"│ ID                         │ Make                       │ Model                      │ Mileage       │%n");
			System.out.format(
					"├────────────────────────────┼────────────────────────────┼────────────────────────────┼───────────────┤%n");
			String leftAlignFormat = "│ %-26s │ %-26s │ %-26s │ %-13d │ %n";

			for (Vehicle vehicle : carPoolManagement.vehicleList) {
				int id = vehicle.getId();
				String make = utils.cutString(vehicle.getMake(), Constants.MAX_FIELD_LENGTH);
				String model = utils.cutString(vehicle.getModel(), Constants.MAX_FIELD_LENGTH);
				int mileage = vehicle.getMileage();

				System.out.format(leftAlignFormat, id, make, model, mileage);

			}
			System.out.format(
					"└────────────────────────────┴────────────────────────────┴────────────────────────────┴───────────────┘%n");

			System.out.println("press enter to continue");
			scan.nextLine();
			menu.show();
		}
	}
	
	/**
	 * lists all reservations for a given vehicle
	 */
	public void listReservations() {
		Vehicle vehicle;
		LinkedList<Reservation> reservationList = null;
		try {
			vehicle = find();
			reservationList = vehicle.getReservationList();
		} catch (NumberFormatException e) {
			System.err.println("Enter a correct ID");
			listReservations();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("ID not found");
			listReservations();
		}

		if (getReservationList().isEmpty()) {
			System.err.println("No reservations found");
			menu.show();
		}

		System.out.format("┌────────────────────────────────┬────────────────────────────────┐%n");
		System.out.format("│ Reservation from               │ until                          │%n");
		System.out.format("├────────────────────────────────┼────────────────────────────────┤%n");
		String leftAlignFormat = "│ %-30s │ %-30s │%n";

		for (Reservation reservation : reservationList) {
			String beginnDate = reservation.getBeginnDate().toString();
			String endDate = reservation.getEndDate().toString();

			beginnDate = utils.cutString(beginnDate, Constants.MAX_FIELD_LENGTH);
			endDate = utils.cutString(endDate, Constants.MAX_FIELD_LENGTH);

			System.out.format(leftAlignFormat, beginnDate, endDate);

		}
		System.out.format("└────────────────────────────────┴────────────────────────────────┘%n");

		System.out.println("press enter to continue");
		scan.nextLine();
		menu.show();
	}

}
