package de.ckc.franke.ausbildung.util;

import static org.junit.Assume.assumeNotNull;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import de.ckc.franke.ausbildung.CarPoolManagement;

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
	 * @return
	 */
	public static Date getCurrentTime() {

		Calendar cal = Calendar.getInstance();
		System.out.println(Constants.DATE_LONG.format(cal.getTime()));
		return cal.getTime();

	}

	/**
	 * converts a string to either a date or date and time 
	 * @param dateInput
	 * @return
	 * @throws Exception 
	 * @throws ParseException
	 */
	public static Calendar convertDate(String dateInput) throws Exception{

		boolean err = false;
		
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
	
	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
