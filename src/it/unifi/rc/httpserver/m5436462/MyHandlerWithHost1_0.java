package it.unifi.rc.httpserver.m5436462;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class MyHandlerWithHost1_0 extends MyHTTPHandler1_0 implements HTTPHandler {
	
	
	private File root;
	private String host;

	
	public MyHandlerWithHost1_0(String host, File root) {
		super(root);
		this.root=root;
		this.host=host;
	}

	
	@Override
	public HTTPReply handle(HTTPRequest request){
		// TODO Auto-generated method stub
		final String myVersion="HTTP/1.0";
		if (!UtilityForMyProject.equalsIfNotNull(request.getParameters().get("Host:"), " "+this.host)) {
			return null;
		} else {
			Calendar calAct = Calendar.getInstance();
			SimpleDateFormat actFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		   	actFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			Map<String,String> parameters = new TreeMap<String,String>();
			parameters.put("Server:", "Lupass Server 0.1");
			parameters.put("Date:", actFormat.format(calAct.getTime()));
			if (!request.getVersion().equals(myVersion)) {
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
}
