package it.unifi.rc.httpserver.m5436462;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public abstract class MyHTTPHandler implements HTTPHandler {
	
	private File root;
	
	MyHTTPHandler(File root) {
		this.root = root;
		this.root.mkdirs();
	}

	@Override
	public abstract HTTPReply handle(HTTPRequest request);
	
	
	public abstract HTTPReply foundResponsable(HTTPRequest request, Map<String, String> parameters, String myVersion);

	
	protected HTTPReply handleGETorHEAD (HTTPRequest request, Map<String, String> parameters, String myVersion) {
		try {
			Path percorso = Paths.get(root.getAbsolutePath(), request.getPath());
			if (!Files.exists(percorso)) {
				return new MyHTTPReply(myVersion, "404", "Not Found", null, parameters);
			} else {
				String modSince = request.getParameters().get("If-Modified-Since");
			if ((modSince == null)
					|| (((UtilityForMyProject.getDateFormatHTTP().format(Files.getLastModifiedTime(percorso).toMillis())).compareTo(modSince))>0)) {
				parameters.put("Content-Length", Long.toString(Files.size(percorso)));
				parameters.put("Content-Type", Files.probeContentType(percorso));
				parameters.put("Last-Modified", UtilityForMyProject.getDateFormatHTTP().format(Files.getLastModifiedTime(percorso).toMillis()));
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

protected HTTPReply handlePOST(HTTPRequest request, Map<String, String> parameters, String myVersion){
		try {
			Path percorso = Paths.get(root.getAbsolutePath(), request.getPath());
			if (!Files.exists(percorso)) {
				return new MyHTTPReply(myVersion, "403", "Forbidden", null, parameters);
			} else {
				FileWriter writer;
				writer = new FileWriter(percorso.toFile(), true);
				writer.write(request.getEntityBody()+"\r\n");
				writer.close();
				parameters.put("Content-Type", Files.probeContentType(percorso));
				parameters.put("Content-Length", Long.toString(Files.size(percorso)));
				return new MyHTTPReply(myVersion, "200", "OK", new String(Files.readAllBytes(percorso)), parameters);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		

}
}
