package it.unifi.rc.httpserver.m5436462;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainHTTPServerThread {
	/**
	 * Questa classe rappresenta il main per mandare in esecuzione il Server HTTP
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int port = 1500;
		int backlog = 5;
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File root = new File("C:\\CARTELLASERVER\\");
		
		HTTPHandler handler1 = new MyHTTPHandler1_0(root);
		HTTPHandler handler2 = new MyHTTPHandler1_1(root);
		HTTPHandler handler3 = new MyHandlerWithHost1_0("www.esempio.it", root);
		HTTPHandler handler4 = new MyHandlerWithHost1_1("www.example.org", root);
		HTTPHandler[] handlers = new HTTPHandler[4];
		handlers[0] = handler4;
		handlers[1]= handler3;
		handlers[2] = handler1;
		handlers[3] = handler2;
		System.out.println(address.getHostAddress());
		System.out.println(address.getHostName());
		HTTPServer myServer = new MyHTTPServerForThread(port, backlog, address, handlers);
		myServer.start();
	}

}
