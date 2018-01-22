package it.unifi.rc.httpserver.m5436462;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class MyHTTPInputStream extends HTTPInputStream {

	private InputStream is;


	public MyHTTPInputStream(InputStream is) {
		super(is);
		this.is = is;
		

	}

	@Override
	protected void setInputStream(InputStream is) {
		// TODO Auto-generated method stub
		this.is = is;
	}
	

	@Override
	public HTTPRequest readHttpRequest() throws HTTPProtocolException {
		try {
			byte[] byteIs = new byte[is.available()];
			is.read(byteIs);
			String stringIs = new String(byteIs);
			System.out.println(stringIs);
			Scanner scanInput = new Scanner(stringIs);
			String method = scanInput.next();
			String path = scanInput.next();
			String version = scanInput.next();
			scanInput.nextLine();
			String temp;
			Scanner scanLine = null;
			Map<String,String> parameters = new TreeMap<String,String>();
			while ((scanInput.hasNextLine()) && ((temp = scanInput.nextLine()).length() > 0)) {
				scanLine = new Scanner(temp);
				temp = scanLine.next();
				parameters.put(temp, scanLine.nextLine());
			}
			if (scanLine != null) scanLine.close();
			scanInput.useDelimiter("\\z");
			String entityBody = null;
			if (scanInput.hasNext())
				entityBody = scanInput.next();
			scanInput.close();
			return new MyHTTPRequest(version, method, path, entityBody, parameters);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public HTTPReply readHttpReply() throws HTTPProtocolException {
		// TODO Auto-generated method stub
		try {
			byte[] byteIs = new byte[is.available()];
			is.read(byteIs);
			String stringIs = new String(byteIs);
			System.out.println(stringIs);
			Scanner scanInput = new Scanner(stringIs);
			Map<String, String> parameters = new TreeMap<String, String>();
			String version = scanInput.next();
			String statusCode = scanInput.next();
			String statusMessage = scanInput.nextLine();
			String temp;
			Scanner scanLine = null;
			while ((scanInput.hasNextLine()) && ((temp = scanInput.nextLine()).length() > 0)) {
				scanLine = new Scanner(temp);
				temp = scanLine.next();
				parameters.put(temp, scanLine.nextLine());
			}
			scanLine.close();
			String data = null;
			scanInput.useDelimiter("\\z");
			if (scanInput.hasNext())
				data = scanInput.next();
			scanInput.close();
			return new MyHTTPReply(version, statusCode, statusMessage, data, parameters);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		this.is.close();

	}

}
