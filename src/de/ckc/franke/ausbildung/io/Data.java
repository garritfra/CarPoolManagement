package de.ckc.franke.ausbildung.io;

import java.io.BufferedWriter;
import java.io.File;
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
import enums.ErrorCode;

public class Data {

	static Scanner scan = new Scanner(System.in);
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
			Data.importJSON();
			break;

		case 2:
			Data.exportJSON(CarPoolManagement.vehicleList);
			break;
		default:
			// Handle invalid inputs
			ErrorCode err = ErrorCode.INVALID_INPUT;
			System.err.println(err);
			menu(io);
		}
	}

	/**
	 * exports the vehicleList to JSON
	 * 
	 * @param vehicleList
	 */
	@SuppressWarnings("unchecked")
	public static void exportJSON(LinkedList<Vehicle> vehicleList) {

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
				e.printStackTrace();
			}
		}
		try {
			// Create file string before actually writing it to the file
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			bw.write(JSONString);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void importJSON() {
		//Reset Vehicle List to prevent duplicate entries
		CarPoolManagement.vehicleList.clear();
		
		// JSONObject vehiclesJSON;
		// System.out.println("Enter a path:");
		// path = scan.nextLine().trim();
		String path = "E:\\Daten_Garrit_Franke\\Eclipse_Workspace\\Fuhrparkverwaltung\\vehicles.txt";
		JSONParser parser = new JSONParser();

		Object obj;
		JSONArray JSONArr;

		try {
			obj = parser.parse(new FileReader(path));
			JSONArr = (JSONArray) obj;

			populateVehicleListFromJSON(JSONArr);

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//CarPoolManagement.vehicleList = vehicleList;
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
