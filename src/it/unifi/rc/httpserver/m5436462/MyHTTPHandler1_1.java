package it.unifi.rc.httpserver.m5436462;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
/**
 * La seguente classe gestisce le richieste generiche implementando la versione HTTP/1.1
 * 
 * @author LUCA_P
 *
 */
public class MyHTTPHandler1_1 extends MyHTTPHandler implements HTTPHandler {

	private File root;
	/**
	 * Crea una nuova istanza della classe
	 * 
	 * @param root è {@link File}: la directory da cui effettua le dovute operazioni
	 */
	public MyHTTPHandler1_1(File root) {
		super(root);
		this.root = root;
		this.root.mkdirs();

	}
	
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.MyHTTPHandler#foundResponsable(it.unifi.rc.httpserver.m5436462.HTTPRequest, java.util.Map, java.lang.String)
	 */
	public HTTPReply foundResponsable(HTTPRequest request, Map<String, String> parameters, String myVersion) {
		if (!UtilityForMyProject.equalsIfNotNull(request.getVersion(), myVersion) && (!UtilityForMyProject.equalsIfNotNull(request.getVersion(), "HTTP/1.0"))) {
			HTTPReply reply = new MyHTTPReply(myVersion, "505", "HTTP Version Not Supported", null, parameters); //Se la versione HTTP della richiesta non è supportata allora ritorna un messaggio VERSION NOT SUPPORTED
			return reply;
		} else if ((UtilityForMyProject.equalsIfNotNull(request.getMethod(), "GET")) || (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "HEAD"))) {
			return handleGETorHEAD(request, parameters, myVersion);						//Se la richiesta è un metodo GET o HEAD delega la richiesta al metodo opportuno
		} else if (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "POST")) {  
			return handlePOST(request, parameters, myVersion);							//Se la richiesta è un metodo POST, la delega al metodo opportuno
		} else if (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "PUT")) {
			return handlePUT(request, parameters, myVersion);							//Se la richiesta è un metodo PUT, la delega la metodo opportuno
		} else if (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "DELETE")) {
			return handleDELETE(request, parameters, myVersion);						//Se la richiesta è un metodo DELETE, la delega al metodo opportuno
		} else {
			HTTPReply reply = new MyHTTPReply(myVersion, "400", "Bad Request", null, parameters); //altrimenti se non è nessuno dei casi precedenti, si tratta di una Bad Request e ritorna una risposta tale
			return reply;
		}
	}
	
	/**
	 * Il seguente metodo si occupa di generare una risposta per per le richieste di tipo PUT
	 * Ritorna la risposta generata.
	 * 
	 * @param  request è {@link HTTPRequest} rappresenta la richiesta
	 * @param  parameters è {@link Map<String,String>} rappresenta le righe di intestazione della risposta
	 * @param  myVersion è {@link String} rappresenta la versione HTTP implementata
	 * @return {@link HTTPReply} risposta generata
	 */

	protected HTTPReply handlePUT(HTTPRequest request, Map<String, String> parameters, String myVersion){
		try {
			if (request.getParameters().get("Content-Range") != null) {			//Se la richiesta contiene una riga di intestazione Content-Rrange
				return new MyHTTPReply(myVersion, "400", "Bad Request", null, parameters); //come da protocollo risponde con una Bad Request
			}
			if (request.getParameters().get("Excepect") != null) {						//Se la richiesta contine una riga di intestazione Excepect con valore '100-continue'
				if (request.getParameters().get("Excepect").equals("100-continue")) {  //risponde con una risposta Continue
					return new MyHTTPReply(myVersion, "100", "Continue", null, parameters);
				}																		//altrimenti risponde con un messaggio Expectation Failed		
				return new MyHTTPReply(myVersion, "417", "Expectation Failed", null, parameters);
			}	
			Path percorso = Paths.get(root.getAbsolutePath(), request.getPath());
			boolean val;								
			val = Files.deleteIfExists(percorso);		//Elimina il File se esiste
			if (Files.exists(percorso.getParent())) {   //crea il file se esistono tutte le directories necessarie
				Files.createFile(percorso);
			} else {									//altrimenti crea directories necessarie e File
				Files.createDirectories(percorso.getParent());
				Files.createFile(percorso);
			}															
			BufferedWriter writer = Files.newBufferedWriter(percorso); //Quindi può scrivere sul File
			writer.write(request.getEntityBody());					   //il contenuto del corpo dell'entità
			writer.close();	
			if (!val) {
				return new MyHTTPReply(myVersion, "201", "Created", null, parameters);  //ritorna una risposta Created se il File è stato creato
			} else {                                                                    //altrimenti ritorna una risposta No Content se il file era già presente
				return new MyHTTPReply(myVersion, "204", "No Content", null, parameters); //ed è stato quindi sovrascritto
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * Il seguente metodo si occupa di generare una risposta per le richieste di tipo DELETE
	 * Ritorna la risposta generata
	 * 
	 * @param  request è {@link HTTPRequest} rappresenta la richiesta
	 * @param  parameters è {@link Map<String,String>} rappresenta le righe di intestazione della risposta
	 * @param  myVersion è {@link String} rappresenta la versione HTTP implementata
	 * @return {@link HTTPReply} risposta generata
	 */

	protected HTTPReply handleDELETE(HTTPRequest request, Map<String, String> parameters, String myVersion){

		Path percorso = Paths.get(root.getAbsolutePath(), request.getPath());
		if (UtilityForMyProject.equalsIfNotNull(request.getPath(), "/")) return new MyHTTPReply(myVersion, "404", "Forbidden", null, parameters);
		try {													
			if (Files.deleteIfExists(percorso)) {										//Elimina il File se esiste
				return new MyHTTPReply(myVersion, "204", "No Content", null, parameters); //quindi ritorna una risposta No Content se è stato eliminato il file
			} else {																	  // altrimenti ritorna una risposta Not Found se il File non esiste	
				return new MyHTTPReply(myVersion, "404", "Not Found", null, parameters);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/* La seguente implementazione del metodo handle è l'unica sempre in grado di rispondere
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.MyHTTPHandler#handle(it.unifi.rc.httpserver.m5436462.HTTPRequest)
	 */
	@Override
	public HTTPReply handle(HTTPRequest request){
		// TODO Auto-generated method stub
		final String myVersion="HTTP/1.1";								
		Map<String, String> parameters = inizializationReply(request);	//inizializza la risposta
		return foundResponsable(request, parameters, myVersion);		//e delega la richiesta al metodo opportuno che ritornerà una risposta
	}
}
