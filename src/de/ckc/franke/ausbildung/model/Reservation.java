package de.ckc.franke.ausbildung.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {

	private Date beginnDate;
	private Date endDate;
	private Vehicle vehicle;
	public Reservation(Date dateStart, Date dateEnd, Vehicle vehicle) {
		this.setBeginDate(dateStart);
		this.setEndDate(dateEnd);
		this.setVehicle(vehicle);

	}

	public Date getBeginnDate() {
		return beginnDate;
	}

	public void setBeginDate(Date dateStart) {
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

	public static boolean isValid(Date date) {

		try {
			DateFormat df = new SimpleDateFormat();
			df.setLenient(true);
			df.parse(date.toString());
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	




	

}
