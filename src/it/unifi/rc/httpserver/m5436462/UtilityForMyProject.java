package it.unifi.rc.httpserver.m5436462;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Questa classe contiene utility per operazioni che occorrono più volte nel
 * Progetto.
 * 
 * @author LUCA_P
 *
 */
public class UtilityForMyProject {
	/**
	 * Questo metodo permette di confrontare due Stringhe controllando che la prima
	 * non sia null. Ritorna true se l'uguaglianza è esatta, false altrimenti.
	 * 
	 * @param first
	 *            is {@link String}
	 * @param second
	 *            is {@link String}
	 * @return boolean value true/false
	 */
	public static boolean equalsIfNotNull(String first, String second) {
		if (first != null)
			return first.equals(second);
		return false;
	}

	/**
	 * Questo metodo ritorna un oggetto SimpleDateFormat che rappresenta il formato
	 * della data per i messaggi HTTP
	 * 
	 * @return SimpleDateFormat in formato data per messaggi HTTP
	 */
	public static SimpleDateFormat getDateFormatHTTP() {
		SimpleDateFormat actFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		actFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return actFormat;
	}

}
