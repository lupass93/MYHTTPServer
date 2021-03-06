package it.unifi.rc.httpserver.m5436462;

import java.io.File;
import java.util.Map;
/**
 * La seguente classe gestisce le richieste verso un particolare host implementando la versione HTTP/1.0
 * 
 * @author LUCA_P
 *
 */
public class MyHandlerWithHost1_0 extends MyHTTPHandler1_0 implements HTTPHandler {
	
	
	private File root;
	private String host;

	/**
	 * Crea una nuova istanza della Classe 
	 * 
	 * @param host � {@link String} rappresenta l'host per il quale l'handler risponde alle richieste
	 * @param root � {@link File} rappresenta la directory a partire dalla effettua le dovute operazioni
	 */
	public MyHandlerWithHost1_0(String host, File root) {
		super(root);
		this.root=root;
		this.root.mkdirs();
		this.host=host;
	}

	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.MyHTTPHandler1_0#handle(it.unifi.rc.httpserver.m5436462.HTTPRequest)
	 */
	@Override
	public HTTPReply handle(HTTPRequest request){
		// TODO Auto-generated method stub
		final String myVersion="HTTP/1.0";
		if (!UtilityForMyProject.equalsIfNotNull(request.getParameters().get("Host"),this.host)) {  //Se l' Host della richiesta non corrisponde
			return null;																			// al proprio host ritorna null;
		} else {																					//altrimenti inizializza la risposta			
			Map<String,String> parameters = inizializationReply(request);                           //e delega la richiesta al metodo responsabile
			return foundResponsable(request, parameters, myVersion);								//che provveder� a ritornare una risposta
		}
			
	}
}
