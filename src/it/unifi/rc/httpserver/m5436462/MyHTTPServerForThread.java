package it.unifi.rc.httpserver.m5436462;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * A HTTP Server
 * 
 * @author LUCA_P
 *
 */
public class MyHTTPServerForThread implements HTTPServer {
	private int port;
	private int backlog;
	private InetAddress address;
	private List<HTTPHandler> handlers;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	protected boolean isStopped;

	/**
	 * Crea una nuova istanza della Classe Server
	 * 
	 * @param port
	 *            {@link int} rappresenta la porta su cui il Server è in attesa
	 * @param backlog
	 *            {@link int} indica il numero massimo di connessioni che il Server
	 *            è diposto ad accettare
	 * @param address
	 *            {@link InetAddress} rappresenta l'indirizzo del Server
	 * @param handlers
	 *            {@link List<HTTPHandler>} rappresenta la lista di handlers del
	 *            Server
	 */
	public MyHTTPServerForThread(int port, int backlog, InetAddress address, HTTPHandler... handlers) {
		this.port = port;
		this.backlog = backlog;
		this.address = address;
		this.handlers = new ArrayList<HTTPHandler>();
		for (int i = 0; i < handlers.length; i++) {
			this.handlers.add(handlers[i]);
		}
		serverSocket = null;
		clientSocket = null;
		isStopped = false;
	}

	/**
	 * Questo metodo crea una ServerSocket
	 * 
	 */
	private void openServerSocket() {
		try {
			serverSocket = new ServerSocket(this.port, this.backlog, this.address);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.unifi.rc.httpserver.m5436462.HTTPServer#addHandler(it.unifi.rc.httpserver.
	 * m5436462.HTTPHandler)
	 */
	@Override
	public void addHandler(HTTPHandler handler) {
		// TODO Auto-generated method stub
		this.handlers.add(handler);

	}

	/**
	 * Ritorna il valore del campo privato isStopped della classe
	 * 
	 * @return
	 */
	public synchronized boolean isStopped() {
		return this.isStopped;
	}

	/*
	 * Questo metodo è il corpo di esecuzione del Server HTTP
	 * 
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPServer#start()
	 */
	@Override
	public void start() throws IOException {
		// TODO Auto-generated method stub
		openServerSocket();
		while (!isStopped()) { // Finchè il Server non viene fermato
			this.clientSocket = null;
			try {
				this.clientSocket = this.serverSocket.accept(); // Accetta connessioni con i Client
			} catch (IOException e) { // Cattura IOException
				if (isStopped()) { // Se il Server viene fermato
					System.out.println("Server Stopped."); // avverti tramite messaggio stampato che il Server è stopped
					return;
				}
				throw new RuntimeException("Error accepting client connection", e); // lancia eccezione nel caso di
																					// errore nell'accettare una
																					// connessione col Client
			}
			new Thread(new WorkerRunnable(clientSocket, handlers)).start(); // lancia un Thread Worker Runnable che si
																			// occupa della comunicazione col Client
		} // Un Thread per ogni Client che si connette al Server
		System.out.println("Server Stopped.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPServer#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		this.isStopped = true; // Pone a true la variabile che indica che il Server deve fermarsi
		try {
			this.serverSocket.close(); // Chiude la Socket tramite la quale accetta connessioni con i Client
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
