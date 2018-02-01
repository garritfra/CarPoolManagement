package de.ckc.franke.ausbildung.io;

import java.util.Scanner;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.io.wrapper.JSON;

public class Data {

	static Scanner scan = new Scanner(System.in);
	Io io = new Io();

	/**
	 * Visual output of the import/export menu 
	 */
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
	 * Import Export Menu Evaluation
	 * 
	 * Takes a userInput and evaluates which menu point executes
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
			System.out.println("Feature currently disabled");
			//VehicleDAO.selectAll();
			break;
		default:
			// Back to menu
			menu();
		}

	}

}
