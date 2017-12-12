package de.ckc.franke.ausbildung;

import de.ckc.franke.ausbildung.util.Console;

public class Main {

	public static void main(String[] args) {
		
		
		//create new instance of the program
		CarPoolManagement carPoolManagement = new CarPoolManagement();
		
		//clear screen before program runs
		Console.clear();	
		
		//start program
		
		carPoolManagement.start();
	}

}
