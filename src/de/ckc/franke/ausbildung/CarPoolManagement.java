package de.ckc.franke.ausbildung;

import java.text.ParseException;
import java.util.Date;
/**
 * Main Program
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import de.ckc.franke.ausbildung.model.Reservation;
import de.ckc.franke.ausbildung.model.Vehicle;
import de.ckc.franke.ausbildung.util.Console;
import de.ckc.franke.ausbildung.util.Utils;

public class CarPoolManagement {
	Scanner scan = new Scanner(System.in);
	Utils utils = new Utils();

	boolean err;
	int mileage = 0;

	public List<Vehicle> vehicleList;
	Controller controller;

	/**
	 * Program start
	 */
	void start() {
		// TEST DATA
		Vehicle seat = new Vehicle("Ibiza", "Seat", 55000);
		seat.setId(vehicleList.size());
		vehicleList.add(seat);

		controller.menu();
	}

	public CarPoolManagement() {
		vehicleList = new LinkedList<>();
		this.controller = new Controller(this);
	}



	/**
	 * Creates a new vehicle
	 */



	public void newReservation(Vehicle vehicle) {
		Date dateStart = null;
		Date dateEnd = null;

		System.out.println("Enter a starting date in format dd/MM/yyyy (HH:mm) (enter 'c' for current date)");
		String dateInput = scan.nextLine().trim();
		
		if (dateInput.toString().toLowerCase().contains("c")) {
			// use current time as beginning
			dateStart = utils.getCurrentTime();
		} else {
			try {
				dateStart = validateDateFormat(dateInput);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println("date has an invalid format");
			}
		}

		dateEnd = reservationEndMenu(dateInput, dateStart);

		Reservation reservation = new Reservation(dateStart, dateEnd, vehicle);
		LinkedList<Reservation> reservationList = vehicle.getReservationList();

		reservation.validateReservation(vehicle, dateStart, dateEnd, this, reservationList);

		vehicle.getReservationList().add(reservation);
		System.out.println("Reservation has been saved");

		controller.menu();

	}

	private Date reservationEndMenu(String dateInput, Date dateStart) {

		Date dateEnd = null;
		// Output options
		System.out.println("Select a duration or enter an end date in format dd/MM/yyyy (HH:mm)");
		System.out.println("1. One Day");
		System.out.println("2. One Week");
		System.out.println("3. One Month");
		System.out.println("4. One Year");

		// get user input
		dateInput = scan.nextLine().trim();

		switch (dateInput) {

		// reservation for one day
		case "1":
			dateEnd = utils.addDays(dateStart, 1);
			break;

		// reservation for one week
		case "2":
			dateEnd = utils.addDays(dateStart, 7);
			break;
		// reservation for one month
		case "3":
			dateEnd = utils.addDays(dateStart, 30);
			break;

		// reservation for one year
		case "4":
			dateEnd = utils.addDays(dateStart, 365);
			break;

		// user entered a date
		default:
			dateEnd = validateDateFormat(dateInput);
			break;
		}
		return dateEnd;

	}

	/**
	 * validates, if a string is equal to a date format TODO add validation for
	 * correct date
	 * 
	 * @param dateInput
	 * 
	 * @throws ParseException
	 */
	private Date validateDateFormat(String dateInput){
		Date time = null;

		time = utils.convertDate(dateInput).getTime();

		return time;
	}


}
