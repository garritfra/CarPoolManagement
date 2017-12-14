package de.ckc.franke.ausbildung.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.model.Vehicle;

public class Data {

	Scanner scan = new Scanner(System.in);
	Io io;

	public void menu(Io io) {
		System.out.println("\nImport/Export Data");
		System.out.println("--------------------");
		System.out.println("1. Import from txt file");
		System.out.println("2. Export as txt file");

		selectOption(io.getChoice());
	}

	/**
	 * checks selection of menu
	 * 
	 * @param userInput
	 */
	public void selectOption(int userInput) {

		switch (userInput) {
		case 1:
			// TODO Import File
			break;

		case 2:
			// TODO Export File
			Data.toJSON(CarPoolManagement.vehicleList);
			break;
		default:
			// Handle invalid inputs
			System.err.println("Input not valid");
			menu(io);
		}
	}

	/**
	 * Parse a vehicleList to csv
	 * 
	 * @param vehicleList
	 */

	/**
	 * Converts a LinkedList to JSON
	 * 
	 * @param vehicleList
	 */
	@SuppressWarnings("unchecked")
	public static void toJSON(LinkedList<Vehicle> vehicleList) {

		JSONArray arr = new JSONArray();

		for (Vehicle vehicle : vehicleList) {

			JSONObject vehicleObj = new JSONObject();

			vehicleObj.put("ID", vehicle.getId());
			vehicleObj.put("make", vehicle.getMake());
			vehicleObj.put("model", vehicle.getModel());
			vehicleObj.put("Mileage", vehicle.getMileage());

			arr.add(vehicleObj);
		}

		try {

			FileWriter file = new FileWriter("vehicles.json");
			file.write(arr.toJSONString());
			file.flush();
			System.out.println("Data has successfully been exported"); // TODO Add "'to ' + path"
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
