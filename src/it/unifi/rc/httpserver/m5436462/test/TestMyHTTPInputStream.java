package it.unifi.rc.httpserver.m5436462.test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import it.unifi.rc.httpserver.m5436462.HTTPInputStream;
import it.unifi.rc.httpserver.m5436462.HTTPProtocolException;
import it.unifi.rc.httpserver.m5436462.HTTPReply;
import it.unifi.rc.httpserver.m5436462.HTTPRequest;
import it.unifi.rc.httpserver.m5436462.MyHTTPInputStream;
import it.unifi.rc.httpserver.m5436462.MyHTTPReply;
import it.unifi.rc.httpserver.m5436462.MyHTTPRequest;

public class TestMyHTTPInputStream {

	@Test
	public void testReadHttpRequest() throws HTTPProtocolException {
		String msg = "GET /pagina.txt HTTP/1.1\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
		InputStream is = new ByteArrayInputStream(msg.getBytes());
		HTTPInputStream httpis = new MyHTTPInputStream(is);
		String version = "HTTP/1.1";
		String method = "GET";
		String path ="/pagina.txt";
		Map<String,String> parameters = new TreeMap<String, String>();
		parameters.put("Connection", "Keep-Alive");
		parameters.put("If-Modified-Since", "Mon, 22 Jan 2018 14:08:54 GMT");
		String entityBody = null;
		HTTPRequest requestExpected = new MyHTTPRequest(version, method, path, entityBody, parameters);
		HTTPRequest request = httpis.readHttpRequest();
		assertEquals(requestExpected.getVersion(), request.getVersion());
		assertEquals(requestExpected.getMethod(), request.getMethod());
		assertEquals(requestExpected.getPath(), request.getPath());
		assertEquals(requestExpected.getEntityBody(), request.getEntityBody());
		assertEquals(requestExpected.getParameters(), request.getParameters());
	}

	@Test
	public void testReadHttpReply() throws HTTPProtocolException {
		String msg = "HTTP/1.1 200 OK\r\nServer: Lupass Server 0.1\r\nDate: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\nCONTENUTO DEL FILE";
		InputStream is = new ByteArrayInputStream(msg.getBytes());
		HTTPInputStream httpis = new MyHTTPInputStream(is);
		String version = "HTTP/1.1";
		String statusCode = "200";
		String statusMessage = "OK";
		Map<String,String> parameters = new TreeMap<String, String>();
		parameters.put("Server", "Lupass Server 0.1");
		parameters.put("Date", "Mon, 22 Jan 2018 14:08:54 GMT");
		String data = "CONTENUTO DEL FILE";
		HTTPReply replyExpected = new MyHTTPReply(version, statusCode, statusMessage, data, parameters);
		HTTPReply reply = httpis.readHttpReply();
		assertEquals(replyExpected.getVersion(), reply.getVersion());
		assertEquals(replyExpected.getStatusCode(), reply.getStatusCode());
		assertEquals(replyExpected.getStatusMessage(), reply.getStatusMessage());
		assertEquals(replyExpected.getData(), reply.getData());
		assertEquals(replyExpected.getParameters(), reply.getParameters());
	}

}
