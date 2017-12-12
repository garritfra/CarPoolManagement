package de.ckc.franke.ausbildung.util;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

	Utils utils = new Utils();
	@Test
	public void isDigitTrue() {
		Assert.assertTrue(Utils.isDigit("7"));
	}

	@Test
	public void isDigitFalse() {
		Assert.assertFalse(Utils.isDigit("This is a String"));
	}

	@Test
	//TODO
	public void isDigitNotString() {
		String variable = null;
		Assert.assertFalse(Utils.isDigit(variable));
	}
	
	@Test
	public void cutStringShort() {
		Assert.assertEquals("asd", Utils.cutString("asd", 10));
	}
	
	@Test
	public void cutStringLong() {
		Assert.assertEquals("abcdefghij", Utils.cutString("abcdefghijklmnop", 10));
	}
	
	@Test 
	public void cutStringDigits(){
		Assert.assertEquals("1234567890", Utils.cutString("1234567890123456789", 10));
	}
}
