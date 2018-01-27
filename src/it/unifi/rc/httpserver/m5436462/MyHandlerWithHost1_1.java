package it.unifi.rc.httpserver.m5436462;

import java.io.File;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public class MyHandlerWithHost1_1 extends MyHTTPHandler1_1 implements HTTPHandler {

	private File root;
	private String host;

	public MyHandlerWithHost1_1(String host, File root) {
		super(root);
		this.host = host;
		this.root = root;
		this.root.mkdirs();

	}
	@Override
	public HTTPReply handle(HTTPRequest request){
		// TODO Auto-generated method stub
		final String myVersion="HTTP/1.1";
		if (!UtilityForMyProject.equalsIfNotNull(request.getParameters().get(("Host")),this.host)){
			return null;
		}
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("Server", "Lupass Server 0.1");
		parameters.put("Date", UtilityForMyProject.getDateFormatHTTP().format(Calendar.getInstance().getTime()));
		return foundResponsable(request, parameters, myVersion);
	}
}
