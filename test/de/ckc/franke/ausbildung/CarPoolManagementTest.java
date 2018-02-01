package de.ckc.franke.ausbildung;

import java.util.LinkedList;

import org.junit.Before;
import de.ckc.franke.ausbildung.model.Vehicle;

public class CarPoolManagementTest {
	CarPoolManagement carPoolManagement = new CarPoolManagement();

	String err;
	String milageStr;
	boolean test;
	LinkedList<Vehicle> vehicleList;

	@Before
	public void setUp() {
		err = null;
		carPoolManagement = new CarPoolManagement();
		CarPoolManagement.vehicleList = null;
		test = true;
	}

}
