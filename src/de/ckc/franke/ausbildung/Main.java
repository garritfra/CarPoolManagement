package de.ckc.franke.ausbildung;

import java.util.LinkedList;

import de.ckc.franke.ausbildung.model.Vehicle;
import de.ckc.franke.ausbildung.util.Console;

public class Main {

	public static void main(String[] args) {

		LinkedList<Vehicle> vehicleList = new LinkedList<>();
		String[] vehicleAttr = null;
		for (String s : args) {
			vehicleAttr = s.split(",");

			String model = vehicleAttr[0];
			String make = vehicleAttr[1];
			int mileage = Integer.parseInt(vehicleAttr[2]);

			Vehicle vehicle = new Vehicle(model, make, mileage);

			vehicle.createID(vehicleList);

			vehicleList.addLast(vehicle);

			//Reset vehicle to prevent wrong entries
			vehicle = null;
		}

		// create new instance of the program
		CarPoolManagement carPoolManagement = new CarPoolManagement();

		// clear screen before program runs
		Console.clear();

		// start program

		carPoolManagement.start(vehicleList);
	}

}
