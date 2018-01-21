package it.unifi.rc.httpserver.m5436462;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.net.InetAddress;
import java.net.ServerSocket;

public class MyHTTPServer implements HTTPServer {
	
	private int port;
	private int backlog;
	private InetAddress address;
	private List<HTTPHandler> handlers;
	private ServerSocket sock;
	private Socket client;
	
	public MyHTTPServer(int port , int backlog , InetAddress address , HTTPHandler ... handlers) {
		this.port = port;
		this.backlog = backlog;
		this.address = address;
		this.handlers = new ArrayList<HTTPHandler>();
		for (int i=0; i<handlers.length; i++) {
			this.handlers.add(handlers[i]);
		}
		sock = null;
		client = null;
		
	}
	
	public InetAddress getInetAddress() {
		return address;
	}
	
	@Override
	public void addHandler(HTTPHandler handler) {
		// TODO Auto-generated method stub
		this.handlers.add(handler);
	}

	@Override
	public void start() throws IOException {
		// TODO Auto-generated method stub
		sock = new ServerSocket(this.port, this.backlog, this.address);
		client = sock.accept();
		boolean stop=false;
		InputStream is = client.getInputStream();
		OutputStream os= client.getOutputStream();
		HTTPInputStream httpis = new MyHTTPInputStream(is);
		HTTPOutputStream httpos = new MyHTTPOutputStream(os);
		HTTPRequest request = null;
		HTTPReply reply = null;
		while(!stop) {	
		while (is.available()==0) {
		}
		request = httpis.readHttpRequest();
		for(int i=0; i<handlers.size(); i++) {
			reply = handlers.get(i).handle(request);
			if (reply!=null) {
				System.out.println("L'handler di indice "+i+" si è occupato delle richiesta: "+handlers.get(i).toString());
				i = handlers.size();
			} 
		}
		httpos.writeHttpReply(reply);
		if (!UtilityForMyProject.equalsIfNotNull(request.getParameters().get("Connection:"), " Keep-Alive")) {
			stop=true;
			this.stop();
		}
		request = null;
		reply = null;
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub	
		try {
			client.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
