package it.unifi.rc.httpserver.m5436462;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.TIMEOUT;

import java.io.IOException;

public class WorkerRunnable implements Runnable {

	protected Socket client = null;
	protected List<HTTPHandler> handlers;
	private boolean stop;
	private boolean timeout = false;

	public WorkerRunnable(Socket clientSocket, List<HTTPHandler> handlers) {
		this.client = clientSocket;
		this.handlers = new ArrayList<HTTPHandler>();
		for (int i = 0; i < handlers.size(); i++) {
			this.handlers.add(handlers.get(i));
		}
		stop = false;

	}
	
	protected void setTimeoutVariable(boolean value) {
		this.timeout=value;
	}

	@Override
	public void run()  {
		// TODO Auto-generated method stub
		try {
			System.out.println("NUOVO THREAD: " + this);
			InputStream is;
			is = client.getInputStream();
			OutputStream os = client.getOutputStream();
			HTTPInputStream httpis = new MyHTTPInputStream(is);
			HTTPOutputStream httpos = new MyHTTPOutputStream(os);
			HTTPRequest request = null;
			HTTPReply reply = null;
			while (!stop) {
				TimeoutHTTP thread = null;
				new Thread ( thread = new TimeoutHTTP(this)).start();
				while (is.available() == 0) {
					if (timeout) {
						close();
						break;
					}
				}
				thread.setCheck(false);
				if (timeout) break;
				request = httpis.readHttpRequest();
				System.out.println("E' stato letto il messaggio");
				for (int i = 0; i < handlers.size(); i++) {
					reply = handlers.get(i).handle(request);
					if (reply != null) {
						System.out.println("L'handler di indice " + i + " si è occupato delle richiesta: "
								+ handlers.get(i).toString());
						i = handlers.size();
					}
				}
				httpos.writeHttpReply(reply);
				if (!UtilityForMyProject.equalsIfNotNull(request.getParameters().get("Connection:"), " Keep-Alive")) {
					close();
				}
				request = null;
				reply = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void close() {
		try {
			System.out.println("Chiusura Thread :"+this.toString());
			this.stop=true;
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
