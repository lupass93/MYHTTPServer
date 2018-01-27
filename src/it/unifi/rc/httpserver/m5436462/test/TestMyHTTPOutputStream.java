package it.unifi.rc.httpserver.m5436462.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import it.unifi.rc.httpserver.m5436462.HTTPOutputStream;
import it.unifi.rc.httpserver.m5436462.HTTPReply;
import it.unifi.rc.httpserver.m5436462.HTTPRequest;
import it.unifi.rc.httpserver.m5436462.MyHTTPOutputStream;
import it.unifi.rc.httpserver.m5436462.MyHTTPReply;
import it.unifi.rc.httpserver.m5436462.MyHTTPRequest;
import it.unifi.rc.httpserver.m5436462.UtilityForMyProject;

public class TestMyHTTPOutputStream {

	@Test
	public void testWriteHttpReply() {
		String version = "HTTP/1.0";
		String statusCode = "304";
		String statusMessage = "Not Modified";
		Map<String, String> parameters = new TreeMap<String, String>();
		String date = UtilityForMyProject.getDateFormatHTTP().format(Calendar.getInstance().getTime());
		parameters.put("Date", date);
		parameters.put("Server", "Lupass Server 0.1");
		String data = null;
		HTTPReply reply = new MyHTTPReply(version, statusCode, statusMessage, data, parameters);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HTTPOutputStream httpos = new MyHTTPOutputStream(os);
		httpos.writeHttpReply(reply);
		String osExpected = "HTTP/1.0 304 Not Modified\r\nDate: "+date+"\r\nServer: Lupass Server 0.1\r\n\r\n";
		String osReal = new String(os.toByteArray());
		assertEquals(osExpected, osReal);
	}
	@Test
	public void testWriteHttpRequest() {
		String version = "HTTP/1.0";
		String method = "GET";
		String path = "/dir/pagina.html";
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("Host", "www.lupass.org");
		parameters.put("User-Agent", "Mozilla FireFox");
		String entityBody = null;
		HTTPRequest request = new MyHTTPRequest(version, method, path, entityBody, parameters);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HTTPOutputStream httpos = new MyHTTPOutputStream(os);
		httpos.writeHttpRequest(request);
		String osExpected = "GET /dir/pagina.html HTTP/1.0\r\nHost: www.lupass.org\r\nUser-Agent: Mozilla FireFox\r\n\r\n";
		String osReal = new String(os.toByteArray());
		assertEquals(osExpected, osReal);
	}
}
