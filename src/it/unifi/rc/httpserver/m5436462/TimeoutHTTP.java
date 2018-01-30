package it.unifi.rc.httpserver.m5436462;

/**
 * Questa classe rappresenta il Timer per le connessioni HTTP
 * Server-Client
 * 
 * @author LUCA_P
 *
 */
public class TimeoutHTTP implements Runnable {

	private WorkerRunnable runnable;
	private boolean restart;

	/**
	 * Crea una istanza della Classe, inizializzando il riferimento all'Oggetto
	 * WorkerRunnable a cui è associato il Timer
	 * 
	 * @param runnable
	 *            {@link WorkerRunnable} rappresenta il riferimento al Thread cui è
	 *            associato il Timer
	 *            
	 * {@link #restart} : Questa variabile, con valore false, permette al Timer del Timeout HTTP di avviarsi
	 * Al contrario, con valore true, lascia il flusso di esecuzione del Thred Timeout
	 * all'interno di un ciclo che non permette l'avviarsi del Timer.
	 */
	public TimeoutHTTP(WorkerRunnable runnable) {
		this.runnable = runnable;
		this.restart = true;
	}
	
	/**
	 * Questo metodo modifica il valore della variabile restart per consentire o meno
	 * l'avviarsi del Timer per le connessioni HTTP
	 * Il metodo è utilizzato dal Thread WorkerRunnable che si occupa della comunicazione col Client.
	 * E' il thread WorkerRunnable a stabilire quando il Timer deve avviarsi e quando non deve.
	 * @param value
	 */
	protected synchronized void setRestart(boolean value) {
		this.restart = value;
	}
	
	/**
	 * Ritorna il valore della variabile {@link #restart}
	 * @return {@link boolean}
	 */
	protected synchronized boolean getRestart() {
		return this.restart;
	}
	/*
	 * Il seguente metodo è il corpo di esecuzione del thread Timer che è associato
	 * a uno specifico Thread WorkerRunnable (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				while (restart) {
					
				}
				if (this.runnable.client.isClosed()) {   //Se la Socket è stata chiusa a causa di una Connessione non persistente
					System.out.println("Connessione non persistente: Terminazione Timer"); //Allora anche il Timer può Terminare
					return;
				}
				System.out.println("Avviato il timer :" + this.toString() + " per il Client gestito dal Thread: "
						+ this.runnable.toString());
				
				Thread.sleep(120 * 1000); // Timer di 120 secondi
				System.out.println("Timer scaduto: Terminazione per Timed Out");
				this.runnable.setTimeoutVariable(true); // Scaduto il timer setta la variabile timeout del thread
														// WorkerRunnable a cui è associato per comunicare il Timed out
				this.runnable.close(); // Chiude quindi la socket del Thread WorkerRunnable associato e lo stesso.
				return;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println("InterrottoThreadTimer");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
