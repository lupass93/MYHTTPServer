package it.unifi.rc.httpserver.m5436462;

import java.io.InputStream;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;


import java.io.IOException;

public class WorkerRunnable implements Runnable {

	protected Socket client = null;
	protected List<HTTPHandler> handlers;
	private boolean stop = false;
	private boolean timeout = false;
	private InputStream is;
	private HTTPInputStream httpis;
	private HTTPOutputStream httpos;
	private HTTPRequest request;
	private HTTPReply reply;

	public WorkerRunnable(Socket clientSocket, List<HTTPHandler> handlers) {
		this.client = clientSocket;
		this.handlers = new ArrayList<HTTPHandler>();
		for (int i = 0; i < handlers.size(); i++) {
			this.handlers.add(handlers.get(i));
		}

	}
	
	protected synchronized boolean getTimeoutVar() {
		return this.timeout;
	}
	
	protected synchronized void setTimeoutVariable(boolean value) {
		this.timeout=value;
	}
	
	private void inizializeConnection() {
		System.out.println("NUOVO THREAD: " + this);
		try {
			this.is = client.getInputStream();
			this.httpis = new MyHTTPInputStream(is);
			this.httpos = new MyHTTPOutputStream(client.getOutputStream());
			this.request = null;
			this.reply = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void requestToHandlers() {
		for (int i = 0; i < handlers.size(); i++) {
			this.reply = this.handlers.get(i).handle(request);
			if (this.reply != null) {
				i = this.handlers.size();
			}
		}
		
	}
	 
	private void checkTypeOfConnection() {
		if (!UtilityForMyProject.equalsIfNotNull(this.request.getParameters().get("Connection"), "Keep-Alive")) {
			close();
		}
	}
	
	
	@Override
	public void run()  {
		// TODO Auto-generated method stub
		try {
			inizializeConnection();
			TimeoutHTTP myTimeout = new TimeoutHTTP(this);
			Thread threadTimeout = new Thread (myTimeout);
			threadTimeout.start();
			while (!stop) {
				myTimeout.setCheck(false);
				while (is.available() == 0) {
					if (timeout) {
						close();
						break;
					}
				}
				if (timeout) {
					break;
				} else {
				myTimeout.setCheck(true);
				threadTimeout.interrupt();
				threadTimeout.interrupted();
				}
				this.request = httpis.readHttpRequest();
				requestToHandlers();
				this.httpos.writeHttpReply(reply);
				checkTypeOfConnection();
				this.request = null;
				this.reply = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void close() {
		try {
			System.out.println("Chiusura Thread e Socket :"+this.toString()+" per il client: "+client.toString());
			client.close();
			this.stop=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
