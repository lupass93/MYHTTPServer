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
		int port = 1500;        //inizializzazione porta su cui accettare connessioni
		int backlog = 5;		//inizializzazione backlog
		InetAddress address = null;  
		try {
			address = InetAddress.getLocalHost();   
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File root = new File("C:\\CARTELLASERVER\\");
		
		HTTPHandler handler1 = new MyHTTPHandler1_0(root);    //Istanziazione Handler generico HTTP/1.0
		HTTPHandler handler2 = new MyHTTPHandler1_1(root);    //Instanziazione Handler generico HTTP/1.1
		HTTPHandler handler3 = new MyHandlerWithHost1_0("www.esempio.it", root);  //Instanziazione handler per host: www.esempio.it con HTTP/1.0
		HTTPHandler handler4 = new MyHandlerWithHost1_1("www.example.org", root); //Instanziazione handler per host: www.example.org con HTTP/1.1
		HTTPHandler[] handlers = new HTTPHandler[4];   // Inizializzazione lista da passare al Server
		handlers[0] = handler4;
		handlers[1]= handler3;
		handlers[2] = handler1;
		handlers[3] = handler2;
		System.out.println(address.getHostAddress()); //Stampa a video di indirizzo IP locale
		System.out.println(address.getHostName());  //Stampa a video di nome Host
		HTTPServer myServer = new MyHTTPServerForThread(port, backlog, address, handlers); //Instanziazione Server
		myServer.start(); //Avvio del Server
	}

}
