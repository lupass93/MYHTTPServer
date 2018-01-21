package it.unifi.rc.httpserver.m5436462;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyHTTPServerForThread implements HTTPServer {
	private int port;
	private int backlog;
	private InetAddress address;
	private List<HTTPHandler> handlers;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	protected boolean isStopped;
	
	public MyHTTPServerForThread(int port , int backlog , InetAddress address , HTTPHandler ... handlers) {
		this.port = port;
		this.backlog = backlog;
		this.address = address;
		this.handlers = new ArrayList<HTTPHandler>();
		for (int i=0; i<handlers.length; i++) {
			this.handlers.add(handlers[i]);
		}
		serverSocket = null;
		clientSocket = null;
		isStopped = false;
	}
	
	private void openServerSocket() {
		try {
			serverSocket = new ServerSocket(this.port, this.backlog, this.address);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void addHandler(HTTPHandler handler) {
		// TODO Auto-generated method stub
		this.handlers.add(handler);

	}

	public synchronized boolean isStopped() {
		return this.isStopped;
	}
	
	
	@Override
	public void start() throws IOException {
		// TODO Auto-generated method stub
		openServerSocket();
        while(! isStopped()){
            this.clientSocket = null;
            try {
                this.clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            new Thread(new WorkerRunnable(clientSocket, handlers)).start();
        }
        System.out.println("Server Stopped.") ;

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
