package it.unifi.rc.httpserver.m5436462;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe astratta che implementa l'interfaccia HTTPHandler,
 * rappresenta la gestione delle richieste HTTP
 * 
 * @author LUCA_P
 *
 */


public abstract class MyHTTPHandler implements HTTPHandler {
	
	private File root;
	/**
	 * 
	 * @param root è {@link File} rappresenta la directory a partire dalla quale effettua le dovute operazioni
	 */
	MyHTTPHandler(File root) {
		this.root = root;
		this.root.mkdirs();
	}
	
	
	/**
	 * Il seguente metodo inizializza la risposta rispetto a una determinata richiesta.
	 * 
	 * Dunque, il compito è quello di determinare le righe di intestazione
	 * che prescindono dal tipo di richiesta(tipo inteso come: GET, HEAD, POST, ecc.)
	 * Il metodo quindi ritorna le righe di intestazione generate, attraverso la struttura dati Map<String, String>
	 * 
	 * 
	 * @param request è {@link HTTPRequest} è la richiesta
	 * @return parameters è {@link Map<String,String>} rappresenta le righe di intestazione della risposta
	 */
	
	protected Map<String,String> inizializationReply(HTTPRequest request) {
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("Server", "Lupass Server 0.1");
		parameters.put("Date", UtilityForMyProject.getDateFormatHTTP().format(Calendar.getInstance().getTime()));
		if(UtilityForMyProject.equalsIfNotNull(request.getVersion(),"HTTP/1.1")&&(UtilityForMyProject.equalsIfNotNull(request.getParameters().get("Connection"),"Keep-Alive"))) {
			parameters.put("Connection", "Keep-Alive");     //Se la richiesta è HTTP/1.1 e richiede una connessione permanente
			parameters.put("Keep-Alive", "timeout=120");	//inserisce righe di intestazione che indicano connessione permanente
		} else {											//e durata (in secondi) del timeout
			parameters.put("Connection", "Close");			//altrimenti inserisce la riga di intestazione che indica connessione 
		}													// non permanente
		return parameters;
	}
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.HTTPHandler#handle(it.unifi.rc.httpserver.m5436462.HTTPRequest)
	 */
	@Override
	public abstract HTTPReply handle(HTTPRequest request);
	
	/**
	 * Questo metodo si occupa di trovare il metodo responsabile per rispondere a un determinato tipo di richiesta
	 * (tipo inteso come: GET, HEAD, POST, ecc.)
	 * 
	 * Ritorna quindi la risposta generata dal metodo responsabile a partire da una determinata richiesta
	 * 
	 * @param request è {@link HTTPRequest} rappresenta la richiesta
	 * @param parameters è {@link Map<String,String>} rappresenta le righe di intestazione della risposta
	 * @param myVersion è {@link String} rappresenta la versione HTTP implementata 
	 * @return {@link HTTPReply} la risposta generata
	 */
	public abstract HTTPReply foundResponsable(HTTPRequest request, Map<String, String> parameters, String myVersion);

	/**
	 * Il seguente metodo si occupa di generare una risposta per le richieste di tipo GET o HEAD
	 * Ritorna dunque la risposta generata.
	 * 
	 * @param  request è {@link HTTPRequest} rappresenta la richiesta
	 * @param  parameters è {@link Map<String,String>} rappresenta le righe di intestazione della risposta
	 * @param  myVersion è {@link String} rappresenta la versione HTTP implementata
	 * @return {@link HTTPReply} risposta generata
	 */
	protected HTTPReply handleGETorHEAD (HTTPRequest request, Map<String, String> parameters, String myVersion) {
		try {
			if (UtilityForMyProject.equalsIfNotNull(request.getPath(), "/")) return new MyHTTPReply(myVersion, "404", "Forbidden", null, parameters);
			Path percorso = Paths.get(root.getAbsolutePath(), request.getPath());
			if (!Files.exists(percorso)) {												//Se il file non esiste
				return new MyHTTPReply(myVersion, "404", "Not Found", null, parameters);//ritorna messaggio di errore Not Found
			} else {																	//altrimenti verifica la presenza dell'header 	
				String modSince = request.getParameters().get("If-Modified-Since");		//If-Modified-Since e effettua l'oportuno confronto
			if ((modSince == null)
					|| (((UtilityForMyProject.getDateFormatHTTP().format(Files.getLastModifiedTime(percorso).toMillis())).compareTo(modSince))>0)) {
				parameters.put("Content-Length", Long.toString(Files.size(percorso)));  //Se la versione del File del Server è più aggiornata 
				parameters.put("Content-Type", Files.probeContentType(percorso));		//inserisce nella risposta con gli opportuni header che descrivono il File 
				parameters.put("Last-Modified", UtilityForMyProject.getDateFormatHTTP().format(Files.getLastModifiedTime(percorso).toMillis()));
				if (request.getMethod().equals("GET")) {								//Se il metodo è GET inserisce il contenuto del File nel corpo dell'entità della risposta
					return new MyHTTPReply(myVersion, "200", "OK", new String(Files.readAllBytes(percorso), "UTF-8") ,parameters);
				} else {																//Altrimenti (si tratta di HEAD) nel messaggio ci sono solo informazioni sul File e non il contenuto
					return new MyHTTPReply(myVersion, "200", "OK", null, parameters);
				}

			} else {																	//Se la versione del file del Server non è più aggiornata 
				return new MyHTTPReply(myVersion, "304", "Not Modified", null, parameters); //rispetto a quella del Client allora ritorna messaggio Not Modified
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	

}
	/**
	 * Il seguente metodo si occupa di generare una risposta per le richieste di tipo POST
	 * Ritorna dunque la risposta generata.
	 * @param  request è {@link HTTPRequest} rappresenta la richiesta
	 * @param  parameters è {@link Map<String,String>} rappresenta le righe di intestazione della risposta
	 * @param  myVersion è {@link String} rappresenta la versione HTTP implementata
	 * @return {@link HTTPReply} risposta generata 
	 */

protected HTTPReply handlePOST(HTTPRequest request, Map<String, String> parameters, String myVersion){
		try {
			Path percorso = Paths.get(root.getAbsolutePath(), request.getPath());     //Se il File non è presente nel Server
			if (!Files.exists(percorso)) {											  // questo risponde con messaggio Forbidden
				return new MyHTTPReply(myVersion, "403", "Forbidden", null, parameters); 
			} else {                                                                  // altrimenti scrive sul File
				FileWriter writer;													  // il contenuto del corpo dell'entità
				writer = new FileWriter(percorso.toFile(), true);					  // in modalità append	
				writer.write(request.getEntityBody()+"\r\n");
				writer.close();
				parameters.put("Content-Type", Files.probeContentType(percorso));     //aggiunge informazioni del File nelle righe di intestazione 
				parameters.put("Content-Length", Long.toString(Files.size(percorso))); //della risposta
				return new MyHTTPReply(myVersion, "200", "OK", new String(Files.readAllBytes(percorso), "UTF-8"), parameters);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		

}
}
