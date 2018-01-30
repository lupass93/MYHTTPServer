package it.unifi.rc.httpserver.m5436462;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * A class that reads {@link HTTPRequest}/{@link HTTPReply} from a given
 * {@link InputStream}.
 * 
 * @author LUCA_P
 *
 */
public class MyHTTPInputStream extends HTTPInputStream {

	private InputStream is;
	private String method = "", path = "", version = "", statusCode = "", statusMessage = "", entityBody = "";
	private Map<String,String> parameters = new TreeMap<String, String>();
	private Scanner scanInput = null;
	/**
	 * Creates a new instance that uses <code>is</code>.
	 * 
	 * @param is {@link InputStream} used to retrieves data.
	 */
	public MyHTTPInputStream(InputStream is) {
		super(is);
		this.is = is;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPInputStream#setInputStream(java.io.
	 * InputStream)
	 */
	@Override
	protected void setInputStream(InputStream is) {
		// TODO Auto-generated method stub
		this.is = is;
	}
	
	/**
	 * Resetta i valori dei campi per potere effetuare correttamente la lettura da InpuStream
	 * 
	 */
	protected void resetFields() {
		this.method = ""; this.path = ""; this.version = ""; this.statusCode = ""; this.statusMessage = ""; this.entityBody = "";
		this.parameters = new TreeMap<String, String>();
		this.scanInput = null;
	}
	/**
	 * Effettua la lettura della riga di Richiesta di un messaggio di richiesta HTTP
	 * E' necessario che lo {@link Scanner}: {@link #scanInput} punti all'inizio del messaggio
	 */
	protected void readRequestLine() {
		if (this.scanInput.hasNext()) {  this.method = this.scanInput.next(); }//Viene letto il nome del metodo, se possibile.
		if (this.scanInput.hasNext()) {  this.path = this.scanInput.next();   }//Viene letto l'URL della richiesta, se possibile.
		if (this.scanInput.hasNext()) {  this.version = this.scanInput.next(); }//Viene letto la versione HTTP che implementa la richiesta, se possibile.
		if (this.scanInput.hasNextLine()) this.scanInput.nextLine();       //Letta la riga di richiesta, si passa alla linea successiva, se possibile.
	}
	/**
	 * Effettua la lettura della riga di Stato di un messaggio di risposta HTTP
	 * E' necessario che lo {@link Scanner}: {@link #scanInput} punti all'inizio del messaggio
	 */
	protected void readStatusLine() {
		if (this.scanInput.hasNext())  this.version = this.scanInput.next(); //legge la versione della risposta, se possibile.
		if (this.scanInput.hasNext()) this.statusCode = this.scanInput.next();//legge lo status Code della risposta, se possibile.
		if (this.scanInput.hasNext()) this.statusMessage = this.scanInput.next(); //legge lo stauts Message dalla risposta, se possibile
		if (this.scanInput.hasNextLine()) this.scanInput.nextLine();		//scorre alla successiva linea, se esiste.
	}
	
	/**
	 * Effettua la lettura degli header di un messaggio HTTP
	 * E' necessario che lo {@link Scanner}: {@link #scanInput} punti alla riga successiva alla prima 
	 * 
	 */
	protected void readHeaderMessage() {
		String temp;
		Scanner scanLine = null;
		while ((this.scanInput.hasNextLine()) && ((temp = this.scanInput.nextLine()).length() > 0)) {  //Se è presente una linea e questa ha dimensione maggiore di 0
			scanLine = new Scanner(temp);                                                    //allora deve trattarsi degli header della richiesta, altrimenti
			scanLine.useDelimiter("\\:s*");                                                  //si tratta della linea \r\n che divide gli header della richiesta
			temp = scanLine.next();						//legge nome header					 // dal corpo dell'entità della richiesta, nel cui caso esce dal ciclo
			scanLine.useDelimiter("\\p{Alnum}");                                             // Dagli headers legge nome e valore e li salva in parameters
			scanLine.next();
			parameters.put(temp, scanLine.nextLine()); //salva in parameters nome header e valore corrispondente
			if (scanLine != null)
				scanLine.close();
		}
	}
	
	
	/**
	 * Effettua la lettura del corpo dell'entità di un messaggio HTTP
	 * E' necessario che lo {@link Scanner}: {@link #scanInput} 
	 * punti all'inizio della prima riga del corpo dell'entità del messaggio HTTP
	 * 
	 */
	protected void readEntityBodyMessage() {
		this.scanInput.useDelimiter("\\z");
		if (this.scanInput.hasNext())					//Se è presente il corpo dell'entità,
			this.entityBody = scanInput.next();			//lo legge e lo salva nella stringa opportuna
	}
	
	/**
	 * Chiude lo Scanner 
	 */
	protected void closeScanner() {
		this.scanInput.close();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPInputStream#readHttpRequest()
	 */
	@Override
	public HTTPRequest readHttpRequest() throws HTTPProtocolException {
		try {
			resetFields();
			byte[] byteIs = new byte[this.is.available()];				//Crea un vettore di byte della dimensione di byte disponibile nello stream
			this.is.read(byteIs);										//Legge questi N byte dallo stream salvandoli nel vettore di byte appena creato
			String stringIs = new String(byteIs, "UTF-8");          //Quindi crea una Stringa a partire dal vettore di byte
			System.out.println(stringIs);
			this.scanInput = new Scanner(stringIs);              //La lettura della richiesta viene effettuata tramite Scanner
			this.readRequestLine();
			this.readHeaderMessage();
			this.readEntityBodyMessage();
			this.closeScanner();
			return new MyHTTPRequest(this.version, this.method, this.path, this.entityBody, this.parameters);  //ritorna quindi la richiesta letta.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPInputStream#readHttpReply()
	 */
	@Override
	public HTTPReply readHttpReply() throws HTTPProtocolException {
		// TODO Auto-generated method stub
		try {
			resetFields();
			byte[] byteIs = new byte[this.is.available()];		//Crea un vettore di byte della dimesione di byte disponibile nell'InputStream
			this.is.read(byteIs);								//Legge questi dall'InputStream e li salva nel vettore
			String stringIs = new String(byteIs, "UTF-8");	//Crea una Stringa a partire dal vettore di byte appena riempito, ora la Stringa rappresenta il messaggio ricevuto
			System.out.println(stringIs);
			this.scanInput = new Scanner(stringIs);      //Legge dalla Stringa attraverso Scanner
			readStatusLine();
			readHeaderMessage();
			readEntityBodyMessage();
			this.closeScanner();
			return new MyHTTPReply(this.version, this.statusCode, this.statusMessage, this.entityBody, this.parameters); //ritorna il messaggio risposta letto.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPInputStream#close()
	 */
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		this.is.close();						//chiude l'InputStream

	}

}
