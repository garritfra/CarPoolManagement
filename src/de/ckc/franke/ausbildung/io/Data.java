package de.ckc.franke.ausbildung.io;

import java.util.Scanner;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.DAO.VehicleDAO;
import de.ckc.franke.ausbildung.io.wrapper.JSON;

public class Data {

	static Scanner scan = new Scanner(System.in);
	Io io = new Io();

	public void menu() {
		System.out.println("\nImport/Export Data");
		System.out.println("--------------------");
		System.out.println("1. Import from txt file");
		System.out.println("2. Export as txt file\n");
		System.out.println("3. Update Database");

		int choice = io.getChoice();

		selectOption(choice);
	}

	/**
	 * checks selection of menu
	 * 
	 * @param userInput
	 */
	public void selectOption(int userInput) {

		switch (userInput) {
		case 1:
			JSON.importJSON();
			break;

		case 2:
			JSON.exportJSON(CarPoolManagement.vehicleList);
			break;

		case 3:
			VehicleDAO.updateAll();
			break;
		default:
			//Back to menu
			menu();
		}

	}




}
