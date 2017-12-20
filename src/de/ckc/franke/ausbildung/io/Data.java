package de.ckc.franke.ausbildung.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.model.Vehicle;
import de.ckc.franke.ausbildung.util.Utils;
import enums.ErrorCode;

public class Data {

	static Scanner scan = new Scanner(System.in);
	Io io = new Io();

	public void menu() {
		System.out.println("\nImport/Export Data");
		System.out.println("--------------------");
		System.out.println("1. Import from txt file");
		System.out.println("2. Export as txt file");

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
			Data.importJSON();
			break;

		case 2:
			Data.exportJSON(CarPoolManagement.vehicleList);
			break;
		default:
			// Handle invalid inputs
			// ErrorCode err = ErrorCode.INVALID_INPUT;
			// System.err.println(err);
			menu();
		}
	}

	/**
	 * exports the vehicleList to JSON
	 * 
	 * @param vehicleList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object exportJSON(LinkedList<Vehicle> vehicleList) {

		JSONArray arr = new JSONArray();

		for (Vehicle vehicle : vehicleList) {

			JSONObject vehicleObj = new JSONObject(); // Create new vehicle JSON Object

			vehicleObj.put("ID", vehicle.getId());
			vehicleObj.put("make", vehicle.getMake());
			vehicleObj.put("model", vehicle.getModel());
			vehicleObj.put("mileage", vehicle.getMileage());

			arr.add(vehicleObj); // add vehicle to parent array
		}

		String path;
		String JSONString = arr.toJSONString(); // Convert JSON Array to string

		if (arr.isEmpty()) {
			System.out.println("Nothing to export");
			Utils.flush();
			return 0;
		}

		// Get custom path from user
		System.out.println("export to path:");
		path = scan.nextLine().trim();

		// Create file Object
		File file = new File(path, "vehicles.txt");

		// If the file does not exist -> create it
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.err.println("Path not found");
				Utils.flush();
				return exportJSON(vehicleList);
			}
		}
		try {
			// Create file string before actually writing it to the file
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			bw.write(JSONString);
			bw.close();
			System.out.println("File successfully exported");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;

	}

	public static void importJSON() {
		// Reset Vehicle List to prevent duplicate entries
		CarPoolManagement.vehicleList.clear();

		String pathPrefix = "E:\\Daten_Garrit_Franke\\Eclipse_Workspace\\Fuhrparkverwaltung\\";

		// get path from user
		System.out.println("Enter a file path. Default: " + pathPrefix);
		String fileName = scan.nextLine().trim();

		String path;

		if (fileName.contains("/")) {
			path = fileName;
		} else {
			path = pathPrefix + fileName;
		}

		// Concatenate file path

		JSONParser parser = new JSONParser();

		Object obj;
		JSONArray JSONArr;

		try {
			obj = parser.parse(new FileReader(path));
			JSONArr = (JSONArray) obj;

			populateVehicleListFromJSON(JSONArr);

			System.out.println("Successfully imported " + JSONArr.size() + " entries.");
			Utils.flush();

		} catch (FileNotFoundException e) {

			System.err.println("File not found");
			Utils.flush();
			importJSON();

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	private static void populateVehicleListFromJSON(JSONArray JSONArr) {

		Vehicle vehicle;
		JSONObject vehicleObj;
		String make;
		String model;
		String mileageStr;
		int mileage;

		// For every entry in array
		for (int index = 0; index < JSONArr.size(); index++) {

			// get vehicle as object
			vehicleObj = (JSONObject) JSONArr.get(index);

			// Assign variables to locally defined attributes
			make = vehicleObj.get("make").toString();
			model = vehicleObj.get("model").toString();
			mileageStr = vehicleObj.get("mileage").toString();

			// Convert mileage to Integer
			mileage = Integer.parseInt(mileageStr);

			vehicle = new Vehicle(model, make, mileage);
			CarPoolManagement.vehicleList.addLast(vehicle);

		}

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
