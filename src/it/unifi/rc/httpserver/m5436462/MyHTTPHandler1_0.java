package it.unifi.rc.httpserver.m5436462;

import java.io.File;
import java.util.Map;
/**
 * La seguente classe gestisce le richieste generiche implementando la versione HTTP/1.0
 * 
 * @author LUCA_P
 *
 */

public class MyHTTPHandler1_0 extends MyHTTPHandler implements HTTPHandler {

	private File root;
	/**
	 * Crea una nuova istanza della classe
	 * 
	 * @param root è {@link File}: la directory da cui effettua le dovute operazioni
	 */
	public MyHTTPHandler1_0(File root) {
		super(root);
		this.root = root;
		this.root.mkdirs();

	}
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.MyHTTPHandler#foundResponsable(it.unifi.rc.httpserver.m5436462.HTTPRequest, java.util.Map, java.lang.String)
	 */
	public HTTPReply foundResponsable(HTTPRequest request, Map<String, String> parameters, String myVersion) {
		
	 	if (!(UtilityForMyProject.equalsIfNotNull(request.getVersion(), myVersion))) {  //Se la richiesta ha versione HTTP non supportata allora
			return null;																// l'handler non è in grado di rispondere e da specifica di progetto ritorna null
		} else if ((UtilityForMyProject.equalsIfNotNull(request.getMethod(), "GET")) || (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "HEAD"))) {
			return handleGETorHEAD(request, parameters, myVersion);						//altrimenti delega la richiesta al metodo opportuno per il tipo di richiesta ricevuto
		} else if (UtilityForMyProject.equalsIfNotNull(request.getMethod(), "POST")) {
			return handlePOST(request, parameters, myVersion);
		} else {																		//Se la richiesta non è un metodo GET, HEAD, POST e ha una versione HTTP supportata
			HTTPReply reply = new MyHTTPReply(myVersion, "400", "Bad Request", null, parameters); //allora si tratta di una Bad Request e ritorna una risposta tale
			return reply;
		} 
	}

	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.MyHTTPHandler#handle(it.unifi.rc.httpserver.m5436462.HTTPRequest)
	 */
	@Override
	public HTTPReply handle(HTTPRequest request) {
		// TODO Auto-generated method stub
		final String myVersion = "HTTP/1.0";
		Map<String, String> parameters = inizializationReply(request);	//Inizializza la risposta e delega la richiesta
		return foundResponsable(request, parameters, myVersion);        //al metodo opportuno che ritornerà una risposta
	}

}
