package it.unifi.rc.httpserver.m5436462;

import java.util.Map;
import java.util.TreeMap;

/**
 * Questa classe rappresenta il messaggio HTTP di risposta
 * 
 * @author LUCA_P
 *
 */
public class MyHTTPReply implements HTTPReply {
	private String version;
	private String statusCode;
	private String statusMessage;
	private String data;
	private Map<String, String> parameters;

	/**
	 * Crea una nuova istanza della Classe
	 * 
	 * @param version
	 *            è {@link String} utilizzato per indicare la versione del messaggio
	 *            HTTP
	 * @param statusCode
	 *            è {@link String} utlizzato per indicare il codice di stato del
	 *            messaggio HTTP
	 * @param statusMessage
	 *            è {@link String} utilizzato per indicare la frase di stato del
	 *            messaggio HTTP
	 * @param data
	 *            è {@link String} utlizzato per i dati del messaggio HTTP
	 * @param parameters
	 *            {@link Map<String,String>} è utilizzato per indicare le righe di
	 *            intestazione del messaggio HTTP
	 */
	public MyHTTPReply(String version, String statusCode, String statusMessage, String data,
			Map<String, String> parameters) {
		this.version = version;
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.data = data;
		if (parameters != null) {
			this.parameters = parameters;
		} else {
			this.parameters = new TreeMap<String, String>();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPReply#getVersion()
	 */
	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return this.version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPReply#getStatusCode()
	 */
	@Override
	public String getStatusCode() {
		// TODO Auto-generated method stub
		return this.statusCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPReply#getStatusMessage()
	 */
	@Override
	public String getStatusMessage() {
		// TODO Auto-generated method stub
		return this.statusMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPReply#getData()
	 */
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return this.data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPReply#getParameters()
	 */
	@Override
	public Map<String, String> getParameters() {
		// TODO Auto-generated method stub
		return this.parameters;
	}

}
