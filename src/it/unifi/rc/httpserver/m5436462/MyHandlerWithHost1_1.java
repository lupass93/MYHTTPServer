package it.unifi.rc.httpserver.m5436462;

import java.io.File;
import java.util.Map;
/**
 * La seguente classe gestisce le richieste verso un particolare host implementando la versione HTTP/1.1
 * 
 * @author LUCA_P
 *
 */
public class MyHandlerWithHost1_1 extends MyHTTPHandler1_1 implements HTTPHandler {

	private File root;
	private String host;
	/**
	 * Crea una nuova istanza della classe
	 * 
	 * @param host è {@link String} rappresenta l'host per il quale l'handler risposnde alle richieste
	 * @param root è {@link File} è la directory a partire dalla quale effettua le dovute operazioni
 
	 */
	public MyHandlerWithHost1_1(String host, File root) {
		super(root);
		this.host = host;
		this.root = root;
		this.root.mkdirs();

	}
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.MyHTTPHandler1_1#handle(it.unifi.rc.httpserver.m5436462.HTTPRequest)
	 */
	@Override
	public HTTPReply handle(HTTPRequest request){
		// TODO Auto-generated method stub
		final String myVersion="HTTP/1.1";															//Se l'host della richiesta 
		if (!UtilityForMyProject.equalsIfNotNull(request.getParameters().get(("Host")),this.host)){ //non corrisponde al proprio host
			return null;																			//ritorna null	
		}																							//altrimenti inizializza la risposta	
		Map<String, String> parameters = inizializationReply(request);								//e delega la richiesta al metodo opportuno
		return foundResponsable(request, parameters, myVersion);									//che provvederà a generare una risposta e ritornarla	
	}
}
