package de.ckc.franke.ausbildung.model;

import java.util.LinkedList;
import de.ckc.franke.ausbildung.util.Utils;

public class Vehicle {
	private String model;
	private String make;
	private int mileage;
	public int id;
	private LinkedList<Reservation> reservationList;
//	public Scanner scan = new Scanner(System.in);
//	public CarPoolManagement carPoolManagement;
//	Io io;
//	public Menu menu;
//	public Controller controller;
	Utils utils;
//	public LinkedList<Vehicle> vehicleList;

	/**
	 * Vehicle Constructor
	 * 
	 * @param model
	 * @param make
	 * @param mileage
	 */

	public Vehicle(String model, String make, int mileage) {

		// this.id = id;
		this.model = model;
		this.make = make;
		this.mileage = mileage;
		this.reservationList = new LinkedList<>();
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LinkedList<Reservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(LinkedList<Reservation> reservationList) {
		this.reservationList = reservationList;
	}



	public int createID(LinkedList<Vehicle> vehicleList) {

		this.id = vehicleList.size();
		
		if(vehicleList.size() == 0) {
			this.id = 0;
		}
		

		return this.id;

	}

}
