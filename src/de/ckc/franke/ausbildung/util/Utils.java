package de.ckc.franke.ausbildung.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

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
	public String cutString(String str, int maxLength) {
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
	public boolean isDigit(String input) {
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
	public Date getCurrentTime() {

		Calendar cal = Calendar.getInstance();
		System.out.println(Constants.DATE_LONG.format(cal.getTime()));
		return cal.getTime();

	}

	/**
	 * converts a string to either a date or date and time 
	 * @param dateInput
	 * @return
	 * @throws ParseException
	 */
	public Calendar convertDate(String dateInput){

		Calendar cal = Calendar.getInstance();
		if (dateInput.toString().length() > 10) {
			// user probably specified a time

			try {
				cal.setTime(Constants.DATE_LONG.parse(dateInput));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println("Invalid date");
				
			}

		} else {
			// user probably didn't specify a time
			try {
				cal.setTime(Constants.DATE_SHORT.parse(dateInput));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println("Invalid date");
			}
		}
		return cal;

	}
	
	public Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
