package de.ckc.franke.ausbildung;

import java.util.LinkedList;

import de.ckc.franke.ausbildung.io.Data;
import de.ckc.franke.ausbildung.io.DB;
import de.ckc.franke.ausbildung.model.Vehicle;
import de.ckc.franke.ausbildung.util.Console;

public class Main {

	public static void main(String[] args) {

		LinkedList<Vehicle> vehicleList = new LinkedList<>();
		Data.pupulateVehicleListFromArgs(args, vehicleList);

		// create new instance of the program
		CarPoolManagement carPoolManagement = new CarPoolManagement();

		// clear screen before program runs
		Console.clear();

		String path = "jdbc:sqlite:E:\\Daten_Garrit_Franke\\Datenbanken\\";
		String fileName = "Vehicles";
		String absolutePath = path + fileName;

		DB.createNewDatabase(fileName);
		DB.createNewTable("vehicles");
		DB.connect();
		DB.selectAll();

		// start program

		carPoolManagement.start(vehicleList);
	}
	
	

}
