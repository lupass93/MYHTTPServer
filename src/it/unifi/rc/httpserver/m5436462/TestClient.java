package it.unifi.rc.httpserver.m5436462;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class TestClient {
	private static Scanner scanInput;
	private static String method = "", path = "", version = "", entityBody = "";
	private static Map<String,String> parameters;
	private static String msg = "";
	private static boolean connection = true;
	private static 	HTTPRequest request = null;
	private static HTTPReply reply = null;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket sock = new Socket("LUCA_P-PC", 1500);
			InputStream is = sock.getInputStream();
			OutputStream os = sock.getOutputStream();
			HTTPInputStream httpis = new MyHTTPInputStream(is);
			HTTPOutputStream httpos = new MyHTTPOutputStream(os);
			while (connection) {
				method = ""; path = ""; version = ""; entityBody = "";
				parameters = new TreeMap<String, String>();
				scanInput = null;
				msg = "";
				request = null;
				reply = null;
				boolean header = true;
				System.out.println("Nella scrittura del messaggio ignorare solo carriage return e new line.");
				System.out.println();
				System.out.println("Inserisci la riga di richiesta del Messaggio HTTP seguendo le specifiche del protocollo:");
				System.out.println();
				msg = new BufferedReader(new InputStreamReader(System.in)).readLine();
				msg = msg + "\r\n";
				String tem = "";
				while (header) {
					System.out.println();
					System.out.println("Inserire Header line oppure premere invio: ");
					System.out.println();
					
					if (!UtilityForMyProject.equalsIfNotNull(tem = new BufferedReader(new InputStreamReader(System.in)).readLine(), "")) {
						msg = msg + tem + "\r\n";
						} else {
							header = false;
						}
					}
					msg = msg + "\r\n";
					System.out.println();
					System.out.println("Inserire entity body oppure premere invio: ");
					System.out.println();
					msg = msg + new BufferedReader(new InputStreamReader(System.in)).readLine();
					//System.out.println(msg);
					scanInput = new Scanner(msg);
					if (TestClient.scanInput.hasNext()) {  TestClient.method = TestClient.scanInput.next(); }//Viene letto il nome del metodo, se possibile.
					if (TestClient.scanInput.hasNext()) {  TestClient.path = TestClient.scanInput.next();   }//Viene letto l'URL della richiesta, se possibile.
					if (TestClient.scanInput.hasNext()) {  TestClient.version = TestClient.scanInput.next(); }//Viene letto la versione HTTP che implementa la richiesta, se possibile.
					if (TestClient.scanInput.hasNextLine()) TestClient.scanInput.nextLine();       //Letta la riga di richiesta, si passa alla linea successiva, se possibile.
					String temp;
					Scanner scanLine = null;
					while ((scanInput.hasNextLine()) && ((temp = scanInput.nextLine()).length() > 0)) {  //Se è presente una linea e questa ha dimensione maggiore di 0
						scanLine = new Scanner(temp);                                                    //allora deve trattarsi degli header della richiesta, altrimenti
						scanLine.useDelimiter("\\:s*");                                                  //si tratta della linea \r\n che divide gli header della richiesta
						temp = scanLine.next();						//legge nome header					 // dal corpo dell'entità della richiesta, nel cui caso esce dal ciclo
						scanLine.useDelimiter("\\p{Alnum}");                                             // Dagli headers legge nome e valore e li salva in parameters
						scanLine.next();
						parameters.put(temp, scanLine.nextLine()); //salva in parameters nome header e valore corrispondente
						if (scanLine != null)
							scanLine.close();
					}
					TestClient.scanInput.useDelimiter("\\z");
					if (TestClient.scanInput.hasNext())					//Se è presente il corpo dell'entità,
						TestClient.entityBody = scanInput.next();			//lo legge e lo salva nella stringa opportuna
					TestClient.scanInput.close();
					request = new MyHTTPRequest(version, method, path, entityBody, parameters);
					httpos.writeHttpRequest(request);
					while (is.available()==0) {
						
					}
					reply = httpis.readHttpReply();
					if (!UtilityForMyProject.equalsIfNotNull(reply.getParameters().get("Connection"), "Keep-Alive")) {
						System.out.println("Connessione non permanente: In Terminazione");
						sock.close();
						httpis.close();
						httpos.close();
						connection = false;
						return;
					}
			}
			sock.close();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
