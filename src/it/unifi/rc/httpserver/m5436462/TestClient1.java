package it.unifi.rc.httpserver.m5436462;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

public class TestClient1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket sock = new Socket("LUCA_P-PC", 1500);
			InputStream is = sock.getInputStream();
			OutputStream os = sock.getOutputStream();
			HTTPInputStream httpis = new MyHTTPInputStream(is);
			HTTPOutputStream httpos = new MyHTTPOutputStream(os);
			System.out.println("C'amm conness");
			Map<String, String> parameters = new TreeMap<String, String>();
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
							System.out.println("INSERISCI NOME HEADER");
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
			httpis.readHttpReply();
			if (UtilityForMyProject.equalsIfNotNull(request.getParameters().get("Connection"), "Keep-Alive")) {
				
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
