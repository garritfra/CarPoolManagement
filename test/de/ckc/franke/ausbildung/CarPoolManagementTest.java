package de.ckc.franke.ausbildung;

import org.junit.Before;

public class CarPoolManagementTest {
	CarPoolManagement carPoolManagement;

	String err;
	String milageStr;
	boolean test;

	@Before
	public void setUp() {
		err = null;
		carPoolManagement = new CarPoolManagement();
		test = true;
	}



}
