package de.ckc.franke.ausbildung.io;

import java.util.LinkedList;
import java.util.Scanner;

import de.ckc.franke.ausbildung.model.Vehicle;
import de.ckc.franke.ausbildung.util.Utils;

public class Data {

	Scanner scan = new Scanner(System.in);
	
	public void menu() {
		System.out.println("\nImport/Export Data");
		System.out.println("--------------------");
		System.out.println("1. Import a txt file");
		System.out.println("2. Export a txt file");
		
		getChoice();

	}

	public void getChoice() {

		String choice = scan.nextLine();
		if (Utils.isDigit(choice.trim())) {
			int number = Integer.parseInt(choice.trim());
			selectOption(number);
		} else {
			System.err.println("Invalid Input");
			return;
		}
	}
	
	/**
	 * checks selection of menu
	 * 
	 * @param userInput
	 */
	public void selectOption(int userInput) {

		switch (userInput) {
		case 1:
			//TODO Import File
			break;

		case 2:
			//TODO Export File			
			break;
		default:

			// Handle invalid inputs
			System.err.println("Input not valid");
			menu();
		}
	}

	/**
	 * Parse a vehicleList to csv
	 */
	public static void toCSV(LinkedList<Vehicle> vehicleList) {
	
	}

}
