package de.ckc.franke.ausbildung.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import de.ckc.franke.ausbildung.model.Vehicle;

public class Data {

	Scanner scan = new Scanner(System.in);
	Io io;

	public void menu(Io io) {
		System.out.println("\nImport/Export Data");
		System.out.println("--------------------");
		System.out.println("1. Import a txt file");
		System.out.println("2. Export a txt file");

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

			vehicleObj.put("Mileage", vehicle.getMileage());
			vehicleObj.put("model", vehicle.getModel());
			vehicleObj.put("make", vehicle.getMake());
			vehicleObj.put("ID", vehicle.getId());
			
			
			arr.add(vehicleObj);
		}

		try {

			FileWriter file = new FileWriter("vehicles.json");
			file.write(arr.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
