package de.ckc.franke.ausbildung;

import java.util.LinkedList;

import de.ckc.franke.ausbildung.io.Data;
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

		
		
		
//		DB.createNewDatabase(fileName);
//		DB.createNewTable("vehicles");
//		DB.connect();
//		conn = DB.connect();
//		DB.insert("Seat", "Ibiza", 55000);
//		DB.selectAll();

		// start program

		carPoolManagement.start(vehicleList);
	}
	
	

}
