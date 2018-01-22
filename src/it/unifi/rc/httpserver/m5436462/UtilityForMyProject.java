package it.unifi.rc.httpserver.m5436462;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class UtilityForMyProject {
	
	public static boolean equalsIfNotNull(String first, String second) {
		if (first != null) return first.equals(second);
		return false;
	}
	
	public static SimpleDateFormat getDateFormatHTTP() {
		SimpleDateFormat actFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		actFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return actFormat;
	}

}
