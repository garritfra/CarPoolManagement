package de.ckc.franke.ausbildung.io;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.Controller;
import de.ckc.franke.ausbildung.model.Vehicle;

public class Io {

	CarPoolManagement carPoolManagement;
	Controller controller;
	Vehicle vehicle;

	/**
	 * checks selection of menu
	 * 
	 * @param userInput
	 */
	public void selectOption(int userInput) {

		switch (userInput) {
		case 1:
			// create new vehicle
			vehicle.newVehicle();
			break;

		case 2:
			// list all vehicles
			vehicle.list();
			break;

		case 3:
			controller.newReservationDialog();
			break;

		case 4:
			// list all reservations
			vehicle.listReservations();
			break;
		default:
			System.err.println("Input not valid");
			controller.menu();
		}
	}
	
	public void getMilageInput() {
		
	}
}
