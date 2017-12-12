package de.ckc.franke.ausbildung.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import de.ckc.franke.ausbildung.CarPoolManagement;
import de.ckc.franke.ausbildung.model.Reservation;
import de.ckc.franke.ausbildung.model.Vehicle;

/**
 * method utilities
 * 
 * @author Garrit Franke
 *
 */
public class Utils {

	/**
	 * cuts a string to a certain length
	 * 
	 * @param str
	 * @param maxLength
	 * @return
	 */
	public static String cutString(String str, int maxLength) {
		if (str.length() > maxLength) {
			str = str.substring(0, maxLength);
		}

		return str;

	}

	/**
	 * checks, if a string is a number
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isDigit(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * gets current date and time
	 * 
	 * @return
	 */
	public static Date getCurrentTime() {

		Calendar cal = Calendar.getInstance();
		System.out.println(Constants.DATE_LONG.format(cal.getTime()));
		return cal.getTime();

	}

	/**
	 * converts a string to either a date or date and time
	 * 
	 * @param dateInput
	 * @return
	 * @throws Exception
	 * @throws ParseException
	 */
	public static Calendar convertDate(String dateInput) throws Exception {

		Calendar cal = Calendar.getInstance();
		if (dateInput.toString().length() > 10) {
			// user probably specified a time

			try {
				cal.setTime(Constants.DATE_LONG.parse(dateInput));
			} catch (Exception e) {
				System.err.println("Invalid date");

			}

		} else {
			// user probably didn't specify a time
			try {
				cal.setTime(Constants.DATE_SHORT.parse(dateInput));
			} catch (Exception e) {
				System.err.println();
				throw new Exception("Invalid date");
			}
		}

		return cal;

	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static void validateReservation(Reservation reservation, Vehicle vehicle, Date dateStart, Date dateEnd,
			LinkedList<Reservation> reservationList) throws Exception {
		// Check if vehicle is already booked
		if (isDuplicate(reservation, reservationList)) {
			throw new Exception("The vehicle is already booked for this date");
		}

		// Check if date is not in the future
		if (!isInFuture(dateStart) || !isInFuture(dateEnd)) {
			Calendar cal = Calendar.getInstance();

			// create a 10 minute time buffer, so that the current date won't be in the past
			// upon validation
			cal.add(Calendar.MINUTE, -10);
			if (!cal.getTime().before(dateStart)) {
				throw new Exception("The date you entered is in the past");
			}

		}

		// Check if begin is after end date
		if (EndBeforeBegin(dateStart, dateEnd)) {
			throw new Exception("The end date can not be before the begin date");
		}

		// Check if date exists
		if (!dateExists(dateStart) || !dateExists(dateEnd)) {
			throw new Exception("You have entered a invalid date");
		}
	}

	/**
	 * Returns true, if the reservation overlaps with any other reservation
	 * 
	 * @param utils
	 * @param reservationList
	 * @return
	 */
	public static boolean isDuplicate(Reservation reservation, LinkedList<Reservation> reservationList) {

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

	public static boolean isInFuture(Date date) {

		Calendar cal = Calendar.getInstance();
		Date currentTime = cal.getTime();

		// return true, if date is in the future
		return date.after(currentTime);
	}

	private static boolean dateExists(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		String dateAsString = sdf.format(date);

		sdf.setLenient(true);

		try {
			sdf.parse(dateAsString);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return true;
	}

	public static boolean EndBeforeBegin(Date dateBegin, Date dateEnd) {

		// return true, if end date is before begin date
		return dateEnd.before(dateBegin);
	}
}
