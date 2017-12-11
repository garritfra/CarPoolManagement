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
	
	

	public int getMilageInput() {

		String mileageStr;

		System.out.println("Mileage:");

		mileageStr = scan.nextLine();
		if (Utils.isDigit(mileageStr)) {
			int mileage = Integer.parseInt(mileageStr);
			return mileage;
		} else {
			System.err.println("The number you entered is not numeric");
			getMilageInput();
		}

		return 0;
	}
}
