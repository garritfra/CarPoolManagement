package de.ckc.franke.ausbildung.io;

import java.util.LinkedList;
import java.util.Scanner;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.io.wrapper.JSON;
import de.ckc.franke.ausbildung.model.Vehicle;

public class Data {

	static Scanner scan = new Scanner(System.in);
	Io io = new Io();

	public void menu() {
		System.out.println("\nImport/Export Data");
		System.out.println("--------------------");
		System.out.println("1. Import from txt file");
		System.out.println("2. Export as txt file\n");
		System.out.println("3. Update Database");

		int choice = io.getChoice();

		selectOption(choice);
	}

	/**
	 * checks selection of menu
	 * 
	 * @param userInput
	 */
	public void selectOption(int userInput) {

		switch (userInput) {
		case 1:
			JSON.importJSON();
			break;

		case 2:
			JSON.exportJSON(CarPoolManagement.vehicleList);
			break;

		case 3:
			Data.updateDB();
			break;
		default:
			// Handle invalid inputs
			// ErrorCode err = ErrorCode.INVALID_INPUT;
			// System.err.println(err);
			menu();
		}

	}

	private static void updateDB() {

		DB.updateAll();
		System.out.println("Success");
		return;
	}

	public static void pupulateVehicleListFromArgs(String[] args, LinkedList<Vehicle> vehicleList) {
		String[] vehicleAttr = null;
		for (String s : args) {
			vehicleAttr = s.split(",");

			String model = vehicleAttr[0];
			String make = vehicleAttr[1];
			int mileage = Integer.parseInt(vehicleAttr[2]);

			Vehicle vehicle = new Vehicle(model, make, mileage);

			vehicle.createID(vehicleList);

			vehicleList.addLast(vehicle);

			// Reset vehicle to prevent wrong entries
			vehicle = null;
		}
	}

}
