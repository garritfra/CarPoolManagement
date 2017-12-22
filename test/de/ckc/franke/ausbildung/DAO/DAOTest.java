package de.ckc.franke.ausbildung.DAO;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class DAOTest {
	String url;
	String fileName;

	@Before
	public void setUp() {
		String url = null;
		String fileName = null;
	}

	@Test
	public void connectNotNull() {
		url = "jdbc:sqlite:E:\\Daten_Garrit_Franke\\Datenbanken\\Test;";
		assertNotNull(DAO.connect(url));
	}

}
