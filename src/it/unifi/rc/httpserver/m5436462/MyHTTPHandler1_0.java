package it.unifi.rc.httpserver.m5436462;

import java.io.File;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public class MyHTTPHandler1_0 extends MyHTTPHandler implements HTTPHandler {

	private File root;
	
	public MyHTTPHandler1_0(File root) {
		super(root);
		this.root = root;
		this.root.mkdirs();

	}
	public HTTPReply foundResponsable(HTTPRequest request, Map<String, String> parameters, String myVersion) {
		
	 	if (!(UtilityForMyProject.equalsIfNotNull(request.getVersion(), myVersion))) {
			return null;
		} else if ((UtilityForMyProject.equalsIfNotNull(request.getMethod(), "GET")) || (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "HEAD"))) {
			return handleGETorHEAD(request, parameters, myVersion);
		} else if (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "POST")) {
			return handlePOST(request, parameters, myVersion);
		} else {
			HTTPReply reply = new MyHTTPReply(myVersion, "400", "Bad Request", null, parameters);
			return reply;
		} 
	}


	@Override
	public HTTPReply handle(HTTPRequest request) {
		// TODO Auto-generated method stub
		final String myVersion = "HTTP/1.0";
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("Server", "Lupass Server 0.1");
		parameters.put("Date", UtilityForMyProject.getDateFormatHTTP().format(Calendar.getInstance().getTime()));
		return foundResponsable(request, parameters, myVersion);
	}

}
