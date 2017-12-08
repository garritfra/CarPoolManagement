package de.ckc.franke.ausbildung.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import de.ckc.franke.ausbildung.CarPoolManagement;

public class Reservation {

	private Date beginnDate;
	private Date endDate;
	private Vehicle vehicle;

	public Reservation(Date dateStart, Date dateEnd, Vehicle vehicle) {
		this.setBeginnDate(dateStart);
		this.setEndDate(dateEnd);
		this.setVehicle(vehicle);

	}

	public Date getBeginnDate() {
		return beginnDate;
	}

	public void setBeginnDate(Date dateStart) {
		this.beginnDate = dateStart;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date dateEnd) {
		this.endDate = dateEnd;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public boolean isValid(Date date) {

		try {
			DateFormat df = new SimpleDateFormat();
			df.setLenient(false);
			df.parse(date.toString());
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Returns true, if the reservation overlaps with any other reservation
	 * 
	 * @param reservation
	 * @param reservationList
	 * @return
	 */
	public boolean isDuplicate(Reservation reservation, LinkedList<Reservation> reservationList) {
		// TODO fix return true if begin date is before start AND after end

		// reservation for comparison
		Date cBegin = reservation.getBeginnDate();
		Date cEnd = reservation.getEndDate();

		for (Reservation activeReservation : reservationList) {

			// iteration of reservations for comparison
			Date nBegin = activeReservation.getBeginnDate();
			Date nEnd = activeReservation.getEndDate();

			// return true, if
			// beginning of entered reservation is after beginning of reservation-iterate
			// and
			// beginning of entered reservation is before end of reservation-iterate

			// or
			// end of entered reservation is after beginning of reservation-iterate
			// and
			// end of entered reservation is before end of reservation-iterate

			// or
			// begin of entered reservation is before beginning of reservation-iterate
			// and
			// end of entered reservation is after end of reservation iterate
			if (cBegin.after(nBegin) && cBegin.before(nEnd) || cEnd.after(nBegin) && cEnd.before(nEnd)
					|| cBegin.before(nBegin) && cEnd.after(nEnd)) {
				return true;// reservation overlaps with another reservation
			}
		}

		return false;// reservation does not overlap with any other reservation

	}

	public boolean isInFuture(Date date) {

		Calendar cal = Calendar.getInstance();
		Date currentTime = cal.getTime();

		// return true, if date is in the future
		return date.after(currentTime);
	}

	private static boolean dateExists(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
        

        String dateAsString = sdf.format(date);

    	sdf.setLenient(false);
            
        try {
			sdf.parse(dateAsString);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return true;
	}

	public boolean EndBeforeBegin(Date dateBegin, Date dateEnd) {

		// return true, if end date is before begin date
		return dateEnd.before(dateBegin);
	}

	public void validateReservation(Vehicle vehicle, Date dateStart, Date dateEnd, CarPoolManagement carPoolManagement,
			LinkedList<Reservation> reservationList){
		// Check if vehicle is already booked
		if (isDuplicate(this, reservationList)) {
			System.err.println("The vehicle is already booked for this date");
			carPoolManagement.newReservation(vehicle);
		}

		// Check if date is not in the future
		if (!isInFuture(dateStart) || !isInFuture(dateEnd)) {
			Calendar cal = Calendar.getInstance();

			// create a 10 minute time buffer, so that the current date won't be in the past
			// upon validation
			cal.add(Calendar.MINUTE, -10);
			if (!cal.getTime().before(dateStart)) {
				System.err.println("The date you entered is in the past");
				carPoolManagement.newReservation(vehicle);
			}

		}

		// Check if begin is after end date
		if (EndBeforeBegin(dateStart, dateEnd)) {
			System.err.println("The end date can not be before the begin date");
			carPoolManagement.newReservation(vehicle);
		}

		// Check if date exists
		if (!dateExists(dateStart) || !dateExists(dateEnd)) {
			System.err.println("You have entered a invalid date");
			carPoolManagement.newReservation(vehicle);
		}
	}

}
