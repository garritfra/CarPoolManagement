package de.ckc.franke.ausbildung.io;

import java.util.Scanner;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.util.Utils;

public class Menu {

	Scanner scan;
	Utils utils;
	CarPoolManagement carPoolManagement;
	Io io;
	
	public void show() {
		// Cons.clear();
		System.out.println("\nCar Pool Management System");
		System.out.println("-------------------");
		System.out.println("1. create new vehicle");
		System.out.println("2. list all vehicles \n");
		System.out.println("3. create new reservation");
		System.out.println("4. list all reservations");
		
		getChoice();
	}
	
	public void getChoice() {
		String choice = scan.nextLine();
		if (utils.isDigit(choice.trim())) {
			int number = Integer.parseInt(choice.trim());
			io.selectOption(number);
		} else {
			getChoice();
		}
	}
}
