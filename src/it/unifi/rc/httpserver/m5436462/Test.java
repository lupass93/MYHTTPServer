package it.unifi.rc.httpserver.m5436462;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket sock = new Socket("192.168.1.6", 1500);
			InputStream is = sock.getInputStream();
			OutputStream os = sock.getOutputStream();
			HTTPInputStream httpis = new MyHTTPInputStream(is);
			HTTPOutputStream httpos = new MyHTTPOutputStream(os);
			System.out.println("C'amm conness");
			Map<String, String> parameters = new TreeMap<String, String>();
			/*parameters.put("Host:", "example.org");
			parameters.put("Connection:", "Keep-Alive");
			String entityBody = null;
			HTTPRequest request = new MyHTTPRequest("HTTP/1.0", "GET", "/cartella/pagina.txt", entityBody, parameters);
			*/
			
			int count = 0;
			boolean connection=true;
			while (connection) {
			parameters = new TreeMap<String, String>();
			boolean ok = true;
			String method = null;
			String path = null;
			String version = null;
			String entityBody=null;
			MyHTTPRequest request = null;
			while (is.available() == 0) {
				if (count == 0) {
					System.out.println("INSERISCI METHOD: ");
					method = new BufferedReader(new InputStreamReader(System.in)).readLine();
					System.out.println("INSERISCI PATH: ");
					path = new BufferedReader(new InputStreamReader(System.in)).readLine();
					System.out.println("INSERISCI VERSION: ");
					version = new BufferedReader(new InputStreamReader(System.in)).readLine();
					parameters =  new TreeMap<String,String>();
					while (ok) {
						System.out.println("Do you want to add a header line? ");
						System.out.println("Y if you want to continue; N if not ");
						if (new BufferedReader(new InputStreamReader(System.in)).readLine().equals("Y")) {
							System.out.println("INSERISCI NOME HEADER SEGUITO DA :");
							String key = new BufferedReader(new InputStreamReader(System.in)).readLine();
							System.out.println("INSERISCI VALORE HEADER");
							String value = new BufferedReader(new InputStreamReader(System.in)).readLine();
							parameters.put(key, value);
						} else {
							ok = false;
						}
					}
					System.out.println("Ad entity body: ");
					entityBody = null;
					entityBody = new BufferedReader(new InputStreamReader(System.in)).readLine();
					request = new MyHTTPRequest(version, method, path, entityBody, parameters);
					httpos.writeHttpRequest(request);
					count++;
				}
			}
			count = 0;
			ok = true;
			HTTPReply reply = httpis.readHttpReply();
			System.out.println(reply.getVersion() + " " + reply.getStatusCode() + " " + reply.getStatusMessage());
			if (!reply.getParameters().isEmpty()) {
				for (Map.Entry<String, String> entry : reply.getParameters().entrySet()) {
					String key = entry.getKey();
					System.out.print(key);
					String value = entry.getValue();
					System.out.print(value);
					System.out.println();
				}
			}
			System.out.println(reply.getData());
			if (UtilityForMyProject.equalsIfNotNull(request.getParameters().get("Connection:"), "Keep-Alive")) {
				
			} else {
				connection = false;
				sock.close();
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
