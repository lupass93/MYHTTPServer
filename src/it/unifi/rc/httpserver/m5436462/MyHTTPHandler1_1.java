package it.unifi.rc.httpserver.m5436462;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public class MyHTTPHandler1_1 extends MyHTTPHandler implements HTTPHandler {

	private File root;
	
	public MyHTTPHandler1_1(File root) {
		super(root);
		this.root = root;
		this.root.mkdirs();

	}
	
	public HTTPReply foundResponsable(HTTPRequest request, Map<String, String> parameters, String myVersion) {
		if (!UtilityForMyProject.equalsIfNotNull(request.getVersion(), myVersion) && (!UtilityForMyProject.equalsIfNotNull(request.getVersion(), "HTTP/1.0"))) {
			HTTPReply reply = new MyHTTPReply(myVersion, "505", "HTTP VERSION NOT SUPPORTED", null, parameters);
			return reply;
		} else if ((UtilityForMyProject.equalsIfNotNull(request.getMethod(), "GET")) || (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "HEAD"))) {
			return handleGETorHEAD(request, parameters, myVersion);
		} else if (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "POST")) {
			return handlePOST(request, parameters, myVersion);
		} else if (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "PUT")) {
			return handlePUT(request, parameters, myVersion);
		} else if (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "DELETE")) {
			return handleDELETE(request, parameters, myVersion);
		} else {
			HTTPReply reply = new MyHTTPReply(myVersion, "400", "Bad Request", null, parameters);
			return reply;
		}
	}


	protected HTTPReply handlePUT(HTTPRequest request, Map<String, String> parameters, String myVersion){
		try {
			if (request.getParameters().get("Content-Range:") != null) {
				return new MyHTTPReply(myVersion, "400", "Bad Request", null, parameters);
			}
			if (request.getParameters().get("Excepect:") != null) {
				if (request.getParameters().get("Excepect:").equals(" 100-continue")) {
					return new MyHTTPReply(myVersion, "100", "Continue", null, parameters);
				}
				return new MyHTTPReply(myVersion, "417", "Expectation Failed", null, parameters);
			}	
			Path percorso = Paths.get(root.getAbsolutePath(), request.getPath());
			boolean val;
			val = Files.deleteIfExists(percorso);
			if (Files.exists(percorso.getParent())) {
				Files.createFile(percorso);
			} else {
				Files.createDirectories(percorso.getParent());
				Files.createFile(percorso);
			}
			BufferedWriter writer = Files.newBufferedWriter(percorso);
			writer.write(request.getEntityBody());
			writer.close();
			if (!val) {
				return new MyHTTPReply(myVersion, "201", "Created", null, parameters);
			} else {
				return new MyHTTPReply(myVersion, "204", "No Content", null, parameters);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	protected HTTPReply handleDELETE(HTTPRequest request, Map<String, String> parameters, String myVersion){

		Path percorso = Paths.get(root.getAbsolutePath(), request.getPath());
		try {
			if (Files.deleteIfExists(percorso)) {
				return new MyHTTPReply(myVersion, "204", "No Content", null, parameters);
			} else {
				return new MyHTTPReply(myVersion, "404", "Not Found", null, parameters);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public HTTPReply handle(HTTPRequest request){
		// TODO Auto-generated method stub
		final String myVersion="HTTP/1.1";
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("Server:", "Lupass Server 0.1");
		parameters.put("Date:", UtilityForMyProject.getDateFormatHTTP().format(Calendar.getInstance().getTime()));
		return foundResponsable(request, parameters, myVersion);
	}
}
