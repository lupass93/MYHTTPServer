package it.unifi.rc.httpserver.m5436462;

import java.io.InputStream;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

/**
 * Questa Classe si occupa di gestione la conessione HTTP tra Server-Client
 * 
 * Un Thread Worker Runnable per ogni Client che si connette al Server.
 * 
 * @author LUCA_P
 *
 */
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

	/**
	 * Crea una istanza con inizializzazione della Socket e della lista di handlers
	 * 
	 * @param clientSocket
	 *            {@link Socket} rappresenta la socket comunicante col Client
	 * @param handlers
	 *            {@link List<HTTPHandler>} rappresenta la lista handlers del Server
	 */
	public WorkerRunnable(Socket clientSocket, List<HTTPHandler> handlers) {
		this.client = clientSocket;
		this.handlers = new ArrayList<HTTPHandler>();
		for (int i = 0; i < handlers.size(); i++) {
			this.handlers.add(handlers.get(i));
		}

	}

	/**
	 * Questo metodo ritorna il valore della variabile {@link #timeout} la quale
	 * serve per indicare lo scadere del Timer e dunque della conessione
	 * 
	 * @return
	 */
	protected synchronized boolean getTimeoutVar() {
		return this.timeout;
	}

	/**
	 * Questo metodo permette di modificare il valore della variabile
	 * {@link #timeout} la quale serve per comunicare lo scadere del Timer e dunque
	 * della conessione
	 * 
	 * @param value
	 */
	protected synchronized void setTimeoutVariable(boolean value) {
		this.timeout = value;
	}

	/**
	 * Questo metodo inizializza la connessione con il Client per consentire la
	 * comuncazione tramite InputStream e OutputStream
	 * 
	 */
	private void inizializeConnection() {
		System.out.println("NUOVO THREAD: " + this + " per Client: "+client.toString());
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

	/**
	 * Il seguente metodo permette di assegnare la richiesta all'handler che è in
	 * grando di generare la risposta opportuna
	 * 
	 */
	private void requestToHandlers() {
		for (int i = 0; i < handlers.size(); i++) {
			this.reply = this.handlers.get(i).handle(request);
			if (this.reply != null) {
				i = this.handlers.size();
			}
		}

	}

	/**
	 * Questo metodo controlla il tipo di connessione con il Client: persistente/non
	 * persistente A seconda del tipo esegue l'operazione opportuna: chiude la
	 * connessione in caso di connessione non persistente o non fa nulla (e dunque
	 * la connessione continua) in caso di connessione persistente
	 * 
	 */
	private void checkTypeOfConnection() {
		if (!UtilityForMyProject.equalsIfNotNull(this.request.getParameters().get("Connection"), "Keep-Alive")
				|| (UtilityForMyProject.equalsIfNotNull(this.request.getVersion(), "HTTP/1.0"))) {
			try {
				Thread.sleep(3 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			System.out.println("Chiusura per Connessione non Persistente");
			close();
		}
	}

	/**
	 * Questo metodo è il corpo dell'esecuzione del Thread WorkerRunnable
	 * 
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			inizializeConnection();
			TimeoutHTTP myTimeout = new TimeoutHTTP(this);
			Thread threadTimeout = new Thread(myTimeout);
			threadTimeout.start(); // Avvia il Thread Timeout
			while (!stop) {
				myTimeout.setRestart(false); //Consenti al Timeout di partire
				while (is.available() == 0) {
					if (timeout) { // Se il Timeout della connessione è scaduto, return
						return;
					}
				}
				myTimeout.setRestart(true); // Per bloccare il riavvio del timeout
				threadTimeout.interrupt(); // Interrompe il timeout, poichè un messaggio è arrivato prima dello scadere
				this.request = httpis.readHttpRequest(); // legge il messaggio di richiesta
				requestToHandlers(); // delega la richiesta all'handler
				this.httpos.writeHttpReply(reply); // scrive la risposta sull'OutputStream
				checkTypeOfConnection(); // Controlla il tipo di connessione: persistente/non persistente
				myTimeout.setRestart(false); //Consente al Timer di avviarsi dopo aver scritto il messaggio sull'OutputStream
											//Inoltre se si tratta di connessione non persistente, consente al Timeout di terminare
				if (client.isClosed()) { 	// Se ha chiuso la Socket, termina
					return;
				}
				this.request = null;		//Resetta a null richiesta
				this.reply = null;			//Resetta a null risposta
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Questo metodo chiude la Socket comunicante col Client e inizializza la
	 * terminazione del thread settando {@link #stop} a true
	 * 
	 */
	public void close() {
		try {
			client.close();
			System.out.println("Chiusura Thread e Socket :" + this.toString() + " per il client: " + client.toString());
			this.stop = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
