package de.ckc.franke.ausbildung.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
			CarPoolManagement.vehicleList = Data.importJSON();
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
			vehicleObj.put("Mileage", vehicle.getMileage());

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

	public static LinkedList<Vehicle> importJSON() {
		String path;
		LinkedList<Vehicle> vehicleList = null;
		// JSONObject vehiclesJSON;

		// System.out.println("Enter a path:");
		// path = scan.nextLine().trim();
		path = "E:\\Daten_Garrit_Franke\\Eclipse_Workspace\\Fuhrparkverwaltung\\vehicles.txt";

		// TODO
		File file = new File(path);
		String fileStr = file.toString();

		return vehicleList;
	}

}
