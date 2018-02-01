package de.ckc.franke.ausbildung.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UtilsTest {

	Utils utils = new Utils();
	Calendar dateStart = Calendar.getInstance();
	Calendar dateEnd = Calendar.getInstance();
	Calendar cal = Calendar.getInstance();
	Calendar expected = Calendar.getInstance();
	SimpleDateFormat sdf;

	@Before
	public void setUp() {
		dateStart.set(2017, 01, 01);
		dateEnd.set(2018, 01, 01);
		sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	}

	@Test
	public void isDigitTrue() {
		Assert.assertTrue(Utils.isDigit("7"));
	}

	@Test
	public void isDigitFalse() {
		Assert.assertFalse(Utils.isDigit("This is a String"));
	}

	@Test
	// TODO
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
	public void cutStringDigits() {
		Assert.assertEquals("1234567890", Utils.cutString("1234567890123456789", 10));
	}

	@Test
	public void endBeforeBeginTrue() {

		Calendar dateBegin = Calendar.getInstance();
		Calendar dateEnd = Calendar.getInstance();
		dateBegin.set(2018, 01, 01);
		dateEnd.set(2017, 01, 01);

		Assert.assertTrue(Utils.endBeforeBegin(dateBegin.getTime(), dateEnd.getTime()));
	}

	@Test
	public void endBeforeBeginFalse() {

		dateStart.set(2017, 01, 01);
		dateEnd.set(2018, 01, 01);

		Assert.assertFalse(Utils.endBeforeBegin(dateStart.getTime(), dateEnd.getTime()));
	}

	@Test
	public void getCurrentTimeTest() {
		Calendar cal = Calendar.getInstance();

		assertEquals(cal.getTime(), Utils.getCurrentTime());
	}

	@Test
	public void isInFutureTrue() {

		cal.set(2100, 01, 01);

		Assert.assertTrue(Utils.isInFuture(cal.getTime()));
	}

	@Test
	public void isInFutureFalse() {

		cal.set(2000, 01, 01);

		Assert.assertFalse(Utils.isInFuture(cal.getTime()));
	}

	@Test
	public void dateExistsTrue() {
		cal.set(2017, 01, 01);

		Assert.assertTrue(Utils.dateExists(cal.getTime()));
	}


}
