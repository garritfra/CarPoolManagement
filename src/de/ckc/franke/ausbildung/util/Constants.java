package de.ckc.franke.ausbildung.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Constants initialization
 * @author Garrit Franke
 *
 */
public final class Constants {
	public static final int MAX_FIELD_LENGTH = 30;	//max length of table
	public static final DateFormat DATE_LONG = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	public static final DateFormat DATE_SHORT = new SimpleDateFormat("dd.MM.yyyy");
	
}
