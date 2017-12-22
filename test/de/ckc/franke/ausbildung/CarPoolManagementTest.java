package de.ckc.franke.ausbildung;

import static org.junit.Assert.assertNull;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
