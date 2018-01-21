package it.unifi.rc.httpserver.m5436462;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class MyHTTPHandler1_0 implements HTTPHandler {

	private File root;
	
	public MyHTTPHandler1_0(File root) {
		this.root = root;
		root.mkdirs();

	}

	protected HTTPReply handleGETorHEAD (HTTPRequest request, Map<String, String> parameters, SimpleDateFormat actFormat, String myVersion) {
			try {
				Path percorso = Paths.get(root.getAbsolutePath(), request.getPath());
				if (!Files.exists(percorso)) {
					return new MyHTTPReply(myVersion, "404", "NOTFOUND", null, parameters);
				} else {
					String modSince = request.getParameters().get("If-Modified-Since:");
				if ((modSince == null)
						|| (((" "+actFormat.format(Files.getLastModifiedTime(percorso).toMillis())).compareTo(modSince))>0)) {
					parameters.put("Content-Length:", Long.toString(Files.size(percorso)));
					parameters.put("Content-Type:", Files.probeContentType(percorso));
					parameters.put("Last-Modified:", actFormat.format(Files.getLastModifiedTime(percorso).toMillis()));
					if (request.getMethod().equals("GET")) {
						return new MyHTTPReply(myVersion, "200", "OK", new String(Files.readAllBytes(percorso)) ,parameters);
					} else {
						return new MyHTTPReply(myVersion, "200", "OK", null, parameters);
					}

				} else {
					return new MyHTTPReply(myVersion, "304", "Not Modified", null, parameters);
				}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		

	}

	protected HTTPReply handlePOST(HTTPRequest request, Map<String, String> parameters, SimpleDateFormat actFormat, String myVersion){
			try {
				Path percorso = Paths.get(root.getAbsolutePath(), request.getPath());
				if (!Files.exists(percorso)) {
					return new MyHTTPReply(myVersion, "403", "Forbidden", null, parameters);
				} else {
					FileWriter writer;
					writer = new FileWriter(percorso.toFile(), true);
					writer.write(request.getEntityBody()+"\r\n");
					writer.close();
					parameters.put("Content-Type:", Files.probeContentType(percorso));
					parameters.put("Content-Length:", Long.toString(Files.size(percorso)));
					return new MyHTTPReply(myVersion, "200", "OK", new String(Files.readAllBytes(percorso)), parameters);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			

	}

	@Override
	public HTTPReply handle(HTTPRequest request) {
		// TODO Auto-generated method stub
		final String myVersion = "HTTP/1.0";
		Calendar calAct = Calendar.getInstance();
		SimpleDateFormat actFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		actFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("Server:", "Lupass Server 0.1");
		parameters.put("Date:", actFormat.format(calAct.getTime()));
		if (!(UtilityForMyProject.equalsIfNotNull(request.getVersion(), myVersion))) {
			return null;
		} else if ((UtilityForMyProject.equalsIfNotNull(request.getMethod(), "GET")) || (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "HEAD"))) {
			return handleGETorHEAD(request, parameters, actFormat, myVersion);
		} else if (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "POST")) {
			return handlePOST(request, parameters, actFormat, myVersion);
		} else {
			HTTPReply reply = new MyHTTPReply(myVersion, "400", "Bad Request", null, parameters);
			return reply;
		}
	}

}
