package it.unifi.rc.httpserver.m5436462;

import java.util.Map;

public class MyHTTPReply implements HTTPReply {
	private String version;
	private String statusCode;
	private String statusMessage;
	private String data;
	private Map<String, String> parameters;
	
	public MyHTTPReply(String version, String statusCode, String statusMessage, String data, Map<String, String> parameters) {
		this.version = version;
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.data = data;
		this.parameters = parameters;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return this.version;
	}

	@Override
	public String getStatusCode() {
		// TODO Auto-generated method stub
		return this.statusCode;
	}

	@Override
	public String getStatusMessage() {
		// TODO Auto-generated method stub
		return this.statusMessage;
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return this.data;
	}

	@Override
	public Map<String, String> getParameters() {
		// TODO Auto-generated method stub
		return this.parameters;
	}

}
