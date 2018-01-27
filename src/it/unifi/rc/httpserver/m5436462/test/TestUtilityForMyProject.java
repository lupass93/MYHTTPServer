package it.unifi.rc.httpserver.m5436462.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

import it.unifi.rc.httpserver.m5436462.UtilityForMyProject;

public class TestUtilityForMyProject {

	@Test
	public void testEqualsIfNotNull() {
		assertEquals(true, UtilityForMyProject.equalsIfNotNull("PROVA", "PROVA"));
		assertEquals(false, UtilityForMyProject.equalsIfNotNull("FIRST", "SECOND"));
		assertEquals(false, UtilityForMyProject.equalsIfNotNull(null, "SECOND"));
		assertEquals(false, UtilityForMyProject.equalsIfNotNull(null, null));
		assertEquals(false, UtilityForMyProject.equalsIfNotNull("FIRST", null));
		
	}

	@Test
	public void testGetDateFormatHTTP() {
		SimpleDateFormat actFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		actFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		assertEquals(actFormat, UtilityForMyProject.getDateFormatHTTP());
	}

}
