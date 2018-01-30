package it.unifi.rc.httpserver.m5436462;

import java.io.OutputStream;
import java.util.Map;
import java.io.IOException;
/**
 * A class that writes {@link HTTPRequest}/{@link HTTPReply} into a given {@link OutputStream}.
 * @author LUCA_P
 *
 */
public class MyHTTPOutputStream extends HTTPOutputStream {
	
	private OutputStream os;
	/**
	 * Crea una nuova istanza, con inizializzazione del suo campo {@link #os}
	 * per effettuare le dovute operazioni sull'OutputStream
	 * 
	 * @param os è {@link OutputStream} utilizzato per scrivere i messaggi di richiesta/risposta sull'OutputStream
	 */
	public MyHTTPOutputStream( OutputStream os ) {
		super(os);
		setOutputStream(os);
	}
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.HTTPOutputStream#writeHttpReply(it.unifi.rc.httpserver.m5436462.HTTPReply)
	 */
	@Override
	public void writeHttpReply(HTTPReply reply) {
		// TODO Auto-generated method stub
		try {																								//Scrive la risposta su una Stringa msg
			String msg = reply.getVersion()+" "+reply.getStatusCode()+" "+reply.getStatusMessage()+"\r\n";  //Ottenendo, secondo il giusto ordine, tutti i campi
			if (!reply.getParameters().isEmpty()) {															//della risposta dall'oggetto HTTPReply	
			for (Map.Entry<String,String> entry : reply.getParameters().entrySet()) {						//Questi vengono quindi scritti sulla Stringa msg
				  msg = msg + entry.getKey() + ": " + entry.getValue() + "\r\n";							//secondo il giusto ordine del formato messaggio HTTP
				}                                                                                           //Prima scrive i campi della riga di stato.
			}																								//Nel ciclo scrive invece gli header della risposta		
			msg = msg + "\r\n";																				//E infine scrive il corpo dell'entità, se presente			
			if(reply.getData()!=null) {
			msg = msg + reply.getData();
			}
			System.out.println(msg);
			os.write(msg.getBytes("UTF-8"));																		//Alla fine la risposte contenuta nella Stringa msg
			os.flush();																						//viene scritta sull'OutputStream	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.HTTPOutputStream#writeHttpRequest(it.unifi.rc.httpserver.m5436462.HTTPRequest)
	 */
	@Override
	public void writeHttpRequest(HTTPRequest request) {
		// TODO Auto-generated method stub
		try {
			String msg = request.getMethod() + " " + request.getPath() + " " + request.getVersion() + "\r\n";		//Scrive la riga di richiesta in una Stringa msg
			if (!request.getParameters().isEmpty()) {
				for (Map.Entry<String,String> entry : request.getParameters().entrySet()) {							//Nella Stringa msg aggiunge tramite il ciclo ogni 	
					  msg = msg + entry.getKey() + ": " + entry.getValue() + "\r\n";								//riga di intestazione presente nel messaggio di richiesta			
					}
				}
			msg = msg + "\r\n";
			if (request.getEntityBody()!=null) msg = msg + request.getEntityBody();                                 //Quindi scrive il corpo dell'entità nella Stringa msg.
			System.out.println(msg);
			os.write(msg.getBytes("UTF-8"));                                                                               //Scrive quindi la Stringa msg 
			os.flush();																								//rappresentante il messaggio di richiesta nell'OutputStream
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.HTTPOutputStream#setOutputStream(java.io.OutputStream)
	 */
	@Override
	protected void setOutputStream(OutputStream os) {
		// TODO Auto-generated method stub
		this.os=os;
	}
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.HTTPOutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		os.close();
	}

}
