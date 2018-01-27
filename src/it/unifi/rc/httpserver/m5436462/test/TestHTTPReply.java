package it.unifi.rc.httpserver.m5436462.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import it.unifi.rc.httpserver.m5436462.HTTPReply;
import it.unifi.rc.httpserver.m5436462.MyHTTPReply;
import it.unifi.rc.httpserver.m5436462.UtilityForMyProject;

public class TestHTTPReply {
	

	@Test
	public void testGetVersion() {
		String version = "HTTP/1.1";
		String statusCode = "400";
		String statusMessage = "Bad Request";
		String data = null;
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("Server", "Lupass Server 0.1");
		parameters.put("Date", UtilityForMyProject.getDateFormatHTTP().format(Calendar.getInstance().getTime()));
		HTTPReply forTest = new MyHTTPReply(version, statusCode, statusMessage, data, parameters);
		assertEquals(version, forTest.getVersion());
	}

	@Test
	public void testGetStatusCode() {
		String version = "HTTP/1.1";
		String statusCode = "400";
		String statusMessage = "Bad Request";
		String data = null;
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("Server", "Lupass Server 0.1");
		parameters.put("Date", UtilityForMyProject.getDateFormatHTTP().format(Calendar.getInstance().getTime()));
		HTTPReply forTest = new MyHTTPReply(version, statusCode, statusMessage, data, parameters);
		assertEquals(statusCode, forTest.getStatusCode());
	}

	@Test
	public void testGetStatusMessage() {
		String version = "HTTP/1.1";
		String statusCode = "400";
		String statusMessage = "Bad Request";
		String data = null;
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("Server", "Lupass Server 0.1");
		parameters.put("Date", UtilityForMyProject.getDateFormatHTTP().format(Calendar.getInstance().getTime()));
		HTTPReply forTest = new MyHTTPReply(version, statusCode, statusMessage, data, parameters);
		assertEquals(statusMessage, forTest.getStatusMessage());
	}

	@Test
	public void testGetData() {
		String version = "HTTP/1.1";
		String statusCode = "400";
		String statusMessage = "Bad Request";
		String data = null;
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("Server", "Lupass Server 0.1");
		parameters.put("Date", UtilityForMyProject.getDateFormatHTTP().format(Calendar.getInstance().getTime()));
		HTTPReply forTest = new MyHTTPReply(version, statusCode, statusMessage, data, parameters);
		assertEquals(data, forTest.getData());
	}

	@Test
	public void testGetParameters() {
		String version = "HTTP/1.1";
		String statusCode = "400";
		String statusMessage = "Bad Request";
		String data = null;
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("Server", "Lupass Server 0.1");
		parameters.put("Date", UtilityForMyProject.getDateFormatHTTP().format(Calendar.getInstance().getTime()));
		HTTPReply forTest = new MyHTTPReply(version, statusCode, statusMessage, data, parameters);
		assertEquals(parameters, forTest.getParameters());
	}

}
