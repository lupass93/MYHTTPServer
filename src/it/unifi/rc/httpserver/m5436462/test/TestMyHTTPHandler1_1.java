package it.unifi.rc.httpserver.m5436462.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import it.unifi.rc.httpserver.m5436462.HTTPHandler;
import it.unifi.rc.httpserver.m5436462.HTTPReply;
import it.unifi.rc.httpserver.m5436462.HTTPRequest;
import it.unifi.rc.httpserver.m5436462.MyHTTPHandler1_1;
import it.unifi.rc.httpserver.m5436462.MyHTTPReply;
import it.unifi.rc.httpserver.m5436462.MyHTTPRequest;

public class TestMyHTTPHandler1_1 {

	@Test
	public void testHandleWithGET() {
		String version = "HTTP/1.1";
		String method = "GET";
		String path = "/fileInesistente.html";
		String entityBody = null;
		Map<String, String> parameters = new TreeMap<String,String>();
		HTTPRequest request = new MyHTTPRequest(version, method, path, entityBody, parameters);
		File root = new File("C:\\CartellaPerTest\\");
		HTTPHandler forTest = new MyHTTPHandler1_1(root);
		HTTPReply reply = forTest.handle(request);
		String replyVersion = "HTTP/1.1";
		String replyStatusCode = "404";
		String replyStatusMessage = "Not Found";
		String data = null;
		Map<String, String> replyParameters = new TreeMap<String,String>();
		HTTPReply replyExpected = new MyHTTPReply(replyVersion, replyStatusCode, replyStatusMessage, data, replyParameters);
		assertEquals(replyExpected.getVersion(), reply.getVersion());
		assertEquals(replyExpected.getStatusCode(), reply.getStatusCode());
		assertEquals(replyExpected.getStatusMessage(), reply.getStatusMessage());
		assertEquals(replyExpected.getData(), reply.getData());
		
	}
	
	public void testHandleWithHEAD() {
		File toFile = new File("C:\\CartellaPerTest\\fileesistente.html");
		if (!Files.exists(toFile.toPath())) {
				try {
					Files.createDirectories(toFile.toPath().getParent());
					Files.createFile(toFile.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		String version = "HTTP/1.1";
		String method = "HEAD";
		String path = "/fileesistente.html";
		String entityBody = null;
		Map<String, String> parameters = new TreeMap<String,String>();
		HTTPRequest request = new MyHTTPRequest(version, method, path, entityBody, parameters);
		File root = new File("C:\\CartellaPerTest\\");
		HTTPHandler forTest = new MyHTTPHandler1_1(root);
		HTTPReply reply = forTest.handle(request);
		String replyVersion = "HTTP/1.1";
		String replyStatusCode = "200";
		String replyStatusMessage = "OK";
		String data = null;
		Map<String, String> replyParameters = new TreeMap<String, String>();
		HTTPReply replyExpected = new MyHTTPReply(replyVersion, replyStatusCode, replyStatusMessage, data, replyParameters);
		assertEquals(replyExpected.getVersion(), reply.getVersion());
		assertEquals(replyExpected.getStatusCode(), reply.getStatusCode());
		assertEquals(replyExpected.getStatusMessage(), reply.getStatusMessage());
		assertEquals(replyExpected.getData(), reply.getData());
	}
	
	public void testHandleWithPOST() throws IOException {
		File toFile = new File("C:\\CartellaPerTest\\fileesistente.html");
		if (!Files.exists(toFile.toPath())) {
				try {
					Files.createDirectories(toFile.toPath().getParent());
					Files.createFile(toFile.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		String version = "HTTP/1.1";
		String method = "POST";
		String path = "/fileesistente.html";
		String entityBody = "AGGIUNTA AL FILE DI UNA RIGA TRAMITE METODO POST\r\n";
		Map<String, String> parameters = new TreeMap<String,String>();
		HTTPRequest request = new MyHTTPRequest(version, method, path, entityBody, parameters);
		File root = new File("C:\\CartellaPerTest\\");
		HTTPHandler forTest = new MyHTTPHandler1_1(root);
		HTTPReply reply = forTest.handle(request);
		String replyVersion = "HTTP/1.1";
		String replyStatusCode = "200";
		String replyStatusMessage = "OK";
		String data = new String(Files.readAllBytes(toFile.toPath()));
		Map<String, String> replyParameters = new TreeMap<String,String>();
		HTTPReply replyExpected = new MyHTTPReply(replyVersion, replyStatusCode, replyStatusMessage, data, replyParameters);
		assertEquals(replyExpected.getVersion(), reply.getVersion());
		assertEquals(replyExpected.getStatusCode(), reply.getStatusCode());
		assertEquals(replyExpected.getStatusMessage(), reply.getStatusMessage());
		assertEquals(replyExpected.getData(), reply.getData());
	}
	
	public void testHandleWithDELETE() {
		String version = "HTTP/1.1";
		String method = "DELETE";
		String path = "/fileInesistente.html";
		String entityBody = null;
		Map<String, String> parameters = new TreeMap<String,String>();
		HTTPRequest request = new MyHTTPRequest(version, method, path, entityBody, parameters);
		File root = new File("C:\\CartellaPerTest\\");
		HTTPHandler forTest = new MyHTTPHandler1_1(root);
		HTTPReply reply = forTest.handle(request);
		String replyVersion = "HTTP/1.1";
		String replyStatusCode = "404";
		String replyStatusMessage = "Not Found";
		String data = null;
		Map<String, String> replyParameters = new TreeMap<String, String>();
		HTTPReply replyExpected = new MyHTTPReply(replyVersion, replyStatusCode, replyStatusMessage, data, replyParameters);
		assertEquals(replyExpected.getVersion(), reply.getVersion());
		assertEquals(replyExpected.getStatusCode(), reply.getStatusCode());
		assertEquals(replyExpected.getStatusMessage(), reply.getStatusMessage());
		assertEquals(replyExpected.getData(), reply.getData());
	}
	
	public void testHandleWithPUT() throws IOException {
		String version = "HTTP/1.1";
		String method = "PUT";
		String path = "/NuovoFile.html";
		String entityBody = null;
		Map<String, String> parameters = new TreeMap<String,String>();
		HTTPRequest request = new MyHTTPRequest(version, method, path, entityBody, parameters);
		File root = new File("C:\\CartellaPerTest\\");
		File toFile = new File("C:\\CartellaPerTest\\NuovoFile.html");
		Files.createDirectories(toFile.toPath().getParent());
		Files.createFile(toFile.toPath());
		HTTPHandler forTest = new MyHTTPHandler1_1(root);
		HTTPReply reply = forTest.handle(request);
		String replyVersion = "HTTP/1.1";
		String replyStatusCode = "204";
		String replyStatusMessage = "No Content";
		String data = null;
		Map<String, String> replyParameters = new TreeMap<String,String>();
		HTTPReply replyExpected = new MyHTTPReply(replyVersion, replyStatusCode, replyStatusMessage, data, replyParameters);
		assertEquals(replyExpected.getVersion(), reply.getVersion());
		assertEquals(replyExpected.getStatusCode(), reply.getStatusCode());
		assertEquals(replyExpected.getStatusMessage(), reply.getStatusMessage());
		assertEquals(replyExpected.getData(), reply.getData());
	}

}
