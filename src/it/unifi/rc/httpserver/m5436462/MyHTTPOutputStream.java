package it.unifi.rc.httpserver.m5436462;

import java.io.OutputStream;
import java.util.Map;
import java.io.IOException;

public class MyHTTPOutputStream extends HTTPOutputStream {
	
	private OutputStream os;
	
	public MyHTTPOutputStream( OutputStream os ) {
		super(os);
		this.os = os;
	}

	@Override
	public void writeHttpReply(HTTPReply reply) {
		// TODO Auto-generated method stub
		try {
			String msg = reply.getVersion()+" "+reply.getStatusCode()+" "+reply.getStatusMessage()+"\r\n";
			if (!reply.getParameters().isEmpty()) {
			for (Map.Entry<String,String> entry : reply.getParameters().entrySet()) {
				  msg = msg + entry.getKey() + " " + entry.getValue() + "\r\n";
				}
			}
			msg = msg + "\r\n";
			if(reply.getData()!=null) {
			msg = msg + reply.getData();
			}
			os.write(msg.getBytes());
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void writeHttpRequest(HTTPRequest request) {
		// TODO Auto-generated method stub
		try {
			String msg = request.getMethod() + " " + request.getPath() + " " + request.getVersion() + "\r\n";
			if (!request.getParameters().isEmpty()) {
				for (Map.Entry<String,String> entry : request.getParameters().entrySet()) {
					  msg = msg + entry.getKey() + " " + entry.getValue() + "\r\n";
					}
				}
			msg = msg + "\r\n";
			if (request.getEntityBody()!=null) msg = msg + request.getEntityBody();
			os.write(msg.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
