package de.ckc.franke.ausbildung.io;

import java.util.Scanner;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.Controller;
import de.ckc.franke.ausbildung.model.Vehicle;
import de.ckc.franke.ausbildung.util.Utils;

public class Io {

	CarPoolManagement carPoolManagement;
	Controller controller;
	Vehicle vehicle;
	Scanner scan = new Scanner(System.in);
	private static Io instance;
	
	public static Io getInstance() {
		if (instance == null) {
			instance = new Io();
		}
		return instance;
	}
	
	

	public int getMilageInput() throws Exception {

		String mileageStr;

		System.out.println("Mileage:");

		mileageStr = scan.nextLine();
		if (Utils.isDigit(mileageStr)) {
			int mileage = Integer.parseInt(mileageStr);
			return mileage;
		} else {
			throw new Exception("The number you entered is not numeric");

		}
	}
	
	public Vehicle findVehicle(CarPoolManagement carPoolManagement) {

		System.out.println("Enter a vehicle ID:");
		String idStr = scan.nextLine(); // receive id as string
		int id = Integer.parseInt(idStr);
		
		// return vehicle that matches the index
		return carPoolManagement.vehicleList.get(id);
	}
}
