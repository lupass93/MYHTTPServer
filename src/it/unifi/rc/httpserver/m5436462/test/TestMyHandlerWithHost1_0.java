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
import it.unifi.rc.httpserver.m5436462.MyHTTPReply;
import it.unifi.rc.httpserver.m5436462.MyHTTPRequest;
import it.unifi.rc.httpserver.m5436462.MyHandlerWithHost1_0;

public class TestMyHandlerWithHost1_0 {

	@Test
	public void testHandleWithGET() {
		String version = "HTTP/1.0";
		String method = "GET";
		String path = "/fileInesistente.html";
		String entityBody = null;
		Map<String, String> parameters = new TreeMap<String,String>();
		String host = "www.example.org";
		parameters.put("Host", host);
		HTTPRequest request = new MyHTTPRequest(version, method, path, entityBody, parameters);
		File root = new File("C:\\CartellaPerTest\\");
		HTTPHandler forTest = new MyHandlerWithHost1_0(host, root);
		HTTPReply reply = forTest.handle(request);
		String replyVersion = "HTTP/1.0";
		String replyStatusCode = "404";
		String replyStatusMessage = "Not Found";
		String data = null;
		Map<String, String> replyParameters = null;
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
		String version = "HTTP/1.0";
		String method = "HEAD";
		String path = "/fileesistente.html";
		String entityBody = null;
		Map<String, String> parameters = new TreeMap<String,String>();
		String host = "www.example.org";
		parameters.put("Host", host);
		HTTPRequest request = new MyHTTPRequest(version, method, path, entityBody, parameters);
		File root = new File("C:\\CartellaPerTest\\");
		HTTPHandler forTest = new MyHandlerWithHost1_0(host, root);
		HTTPReply reply = forTest.handle(request);
		String replyVersion = "HTTP/1.0";
		String replyStatusCode = "200";
		String replyStatusMessage = "OK";
		String data = null;
		Map<String, String> replyParameters = null;
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
		String version = "HTTP/1.0";
		String method = "POST";
		String path = "/fileesistente.html";
		String entityBody = "AGGIUNTA AL FILE DI UNA RIGA TRAMITE METODO POST\r\n";
		Map<String, String> parameters = new TreeMap<String,String>();
		String host = "www.example.org";
		parameters.put("Host", host);
		HTTPRequest request = new MyHTTPRequest(version, method, path, entityBody, parameters);
		File root = new File("C:\\CartellaPerTest\\");
		HTTPHandler forTest = new MyHandlerWithHost1_0(host, root);
		HTTPReply reply = forTest.handle(request);
		String replyVersion = "HTTP/1.0";
		String replyStatusCode = "200";
		String replyStatusMessage = "OK";
		String data = new String(Files.readAllBytes(toFile.toPath()));
		Map<String, String> replyParameters = null;
		HTTPReply replyExpected = new MyHTTPReply(replyVersion, replyStatusCode, replyStatusMessage, data, replyParameters);
		assertEquals(replyExpected.getVersion(), reply.getVersion());
		assertEquals(replyExpected.getStatusCode(), reply.getStatusCode());
		assertEquals(replyExpected.getStatusMessage(), reply.getStatusMessage());
		assertEquals(replyExpected.getData(), reply.getData());
	}
	

}
