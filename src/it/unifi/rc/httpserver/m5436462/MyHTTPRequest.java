package it.unifi.rc.httpserver.m5436462;

import java.util.Map;

public class MyHTTPRequest implements HTTPRequest {
	private String version;
	private String method;
	private String path;
	private String entityBody;
	private Map<String, String> parameters;
	
	public MyHTTPRequest(String version, String method, String path, String entityBody, Map<String, String> parameters) {
		this.version = version;
		this.method = method;
		this.path = path;
		this.entityBody = entityBody;
		this.parameters = parameters;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return this.version;
	}

	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return this.method;
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return this.path;
	}

	@Override
	public String getEntityBody() {
		// TODO Auto-generated method stub
		return this.entityBody;
	}

	@Override
	public Map<String, String> getParameters() {
		// TODO Auto-generated method stub
		return this.parameters;
	}

}
