package it.unifi.rc.httpserver.m5436462.test;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import it.unifi.rc.httpserver.m5436462.HTTPRequest;
import it.unifi.rc.httpserver.m5436462.MyHTTPRequest;

public class TestHTTPRequest {

	@Test
	public void testGetVersion() {
		String version = "HTTP/1.0";
		String method = "GET";
		String path = "/cartella/file.txt";
		String entityBody = null;
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("If-Modified-Since", "Mon, 22 Jan 2018 14:08:54 GMT");
		HTTPRequest forTest = new MyHTTPRequest(version, method, path, entityBody, parameters);
		assertEquals(version, forTest.getVersion());
	}

	@Test
	public void testGetMethod() {
		String version = "HTTP/1.0";
		String method = "GET";
		String path = "/cartella/file.txt";
		String entityBody = null;
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("If-Modified-Since", "Mon, 22 Jan 2018 14:08:54 GMT");
		HTTPRequest forTest = new MyHTTPRequest(version, method, path, entityBody, parameters);
		assertEquals(method, forTest.getMethod());
	}

	@Test
	public void testGetPath() {
		String version = "HTTP/1.0";
		String method = "GET";
		String path = "/cartella/file.txt";
		String entityBody = null;
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("If-Modified-Since", "Mon, 22 Jan 2018 14:08:54 GMT");
		HTTPRequest forTest = new MyHTTPRequest(version, method, path, entityBody, parameters);
		assertEquals(path, forTest.getPath());
	}

	@Test
	public void testGetEntityBody() {
		String version = "HTTP/1.0";
		String method = "GET";
		String path = "/cartella/file.txt";
		String entityBody = null;
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("If-Modified-Since", "Mon, 22 Jan 2018 14:08:54 GMT");
		HTTPRequest forTest = new MyHTTPRequest(version, method, path, entityBody, parameters);
		assertEquals(entityBody, forTest.getEntityBody());
	}

	@Test
	public void testGetParameters() {
		String version = "HTTP/1.0";
		String method = "GET";
		String path = "/cartella/file.txt";
		String entityBody = null;
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("If-Modified-Since", "Mon, 22 Jan 2018 14:08:54 GMT");
		HTTPRequest forTest = new MyHTTPRequest(version, method, path, entityBody, parameters);
		assertEquals(parameters, forTest.getParameters());
	}

}
