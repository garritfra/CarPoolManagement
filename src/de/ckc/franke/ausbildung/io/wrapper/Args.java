package de.ckc.franke.ausbildung.io.wrapper;

import java.util.LinkedList;

import de.ckc.franke.ausbildung.model.Vehicle;

public class Args {

	
	/**
	 * 
	 * @param args
	 * @param vehicleList
	 */
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
