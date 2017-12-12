package de.ckc.franke.ausbildung;

import java.util.Scanner;

import de.ckc.franke.ausbildung.io.Menu;
import de.ckc.franke.ausbildung.model.Vehicle;

public class Controller {

	Scanner scan = new Scanner(System.in);
	CarPoolManagement carPoolManagement;
	Menu menu;
	
	
//	public static Controller getInstance() {
//		if (instance == null) {
//			instance = new Controller();
//		}
//		return instance;
//	}

	Vehicle vehicle;

	public Controller(CarPoolManagement carPoolManagement, Menu menu) {
		this.carPoolManagement = carPoolManagement;
		this.menu = menu;
	}

	



	

	/**
	 * vehicle was successfully saved
	 */
	public void addVehicleSuccess() {

		System.out.println("Vehicle has been saved");

		boolean valid = false; // initialize boolean
		do { // while input is not valid
			System.out.println("Do you want to add another vehicle? (Y/N)");
			String choice = scan.nextLine();

			if (choice.trim().equals("Y") || choice.trim().equals("y")) {
				carPoolManagement.newVehicle();
			} else if (choice.trim().equals("N") || choice.trim().equals("n")) {
				menu.show();
				valid = true;
			} else {
				System.err.println("Invalid");
			}
		} while (valid == false);
	}


















}
