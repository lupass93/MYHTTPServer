package it.unifi.rc.httpserver.m5436462;

import java.util.Map;
import java.util.TreeMap;

/**
 * Questa classe rappresenta il messaggio HTTP di richiesta.
 * 
 * @author LUCA_P
 *
 */
public class MyHTTPRequest implements HTTPRequest {
	private String version;
	private String method;
	private String path;
	private String entityBody;
	private Map<String, String> parameters;

	/**
	 * 
	 * Crea una nuova istanza della Classe
	 * 
	 * @param version
	 *            è {@link String} utlizzato per indicare la versione del messaggio
	 *            HTTP
	 * @param method
	 *            è {@link String} utlizzato per indicare il metodo del messaggio
	 *            HTTP
	 * @param path
	 *            è{@link String} utilizzato per indicare l'URL del messaggio HTTP
	 * @param entityBody
	 *            è {@link String} utilizzato per il corpo dell'entità del messaggio
	 *            HTTP
	 * @param parameters
	 *            è{@link Map<String,String} utilizzato per indicare le righe di
	 *            intestazione del messaggio HTTP
	 */

	public MyHTTPRequest(String version, String method, String path, String entityBody,
			Map<String, String> parameters) {
		this.version = version;
		this.method = method;
		this.path = path;
		this.entityBody = entityBody;
		if (parameters != null) {
			this.parameters = parameters;
		} else {
			this.parameters = new TreeMap<String, String>();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPRequest#getVersion()
	 */
	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return this.version;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPRequest#getMethod()
	 */
	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return this.method;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPRequest#getPath()
	 */
	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return this.path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPRequest#getEntityBody()
	 */
	@Override
	public String getEntityBody() {
		// TODO Auto-generated method stub
		return this.entityBody;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPRequest#getParameters()
	 */
	@Override
	public Map<String, String> getParameters() {
		// TODO Auto-generated method stub
		return this.parameters;
	}

}
