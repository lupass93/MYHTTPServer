package it.unifi.rc.httpserver.m5436462;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Mon, 22 Jan 2018 14:08:54 GMT
		try {
			Socket sock = new Socket("LUCA_P-PC", 1500);
			InputStream is = sock.getInputStream();
			OutputStream os = sock.getOutputStream();
			HTTPInputStream httpis = new MyHTTPInputStream(is);
			System.out.println("C'amm conness");
			String[] msg = new String[32];
			// MESSAGGI DI TEST PER HTTP/1.1
			msg[0] = "GET /pagina.txt HTTP/1.1\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
			msg[1] = "POST /pagina.txt HTTP/1.1\r\nConnection: Keep-Alive\r\n\r\nAggiungiamo al file una riga\r\n";
			msg[2] = "HEAD /pagina.txt HTTP/1.1\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n";
			msg[3] = "GET /pagina.txt HTTP/1.1\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
			msg[4] = "PUT /pagina2.html HTTP/1.1\r\nConnection: Keep-Alive\r\n\r\nProva metodo PUT\r\n";
			msg[5] = "DELETE /pagina2.html HTTP/1.1\r\nConnection: Keep-Alive\r\n\r\n";
			msg[6] = "GET /paginaInesistente HTTP/1.1\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
			msg[7] = "HEAD /paginaInesistente HTTP/1.1\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
			msg[8] = "POST /paginaInesistente HTTP/1.1\r\nConnection: Keep-Alive\r\n\r\n AGGIUNGI UNA RIGA AL FILE\r\n";
			msg[9] = "DELETE /paginaInesistente HTTP/1.1\r\nConnection: Keep-Alive\r\n\r\n";
			// MESSAGGI DI TEST PER HTTP/1.0
			msg[10] = "GET /pagina.txt HTTP/1.0\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
			msg[11] = "POST /pagina.txt HTTP/1.0\r\nConnection: Keep-Alive\r\n\r\nAggiungiamo al file una riga\r\n";
			msg[11] = "HEAD /pagina.txt HTTP/1.0\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n";
			msg[12] = "GET /pagina.txt HTTP/1.0\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
			msg[13] = "GET /pagina.txt HTTP/1.0\r\nConnection: Keep-Alive\r\n\r\n";
			msg[14] = "GET /pagina.txt HTTP/1.0\r\nConnection: Keep-Alive\r\n\r\n";
			// MESSAGGI DI TEST PER HTTP/1.1 con Host
			msg[15] = "GET /pagina.txt HTTP/1.1\r\nHost: www.example.org\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
			msg[16] = "POST /pagina.txt HTTP/1.1\r\nHost: www.example.org\r\nConnection: Keep-Alive\r\n\r\nAggiungiamo al file una riga\r\n";
			msg[17] = "HEAD /pagina.txt HTTP/1.1\r\nHost: www.example.org\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n";
			msg[18] = "GET /pagina.txt HTTP/1.1\r\nHost: www.example.org\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
			msg[19] = "PUT /pagina2.html HTTP/1.1\r\nHost: www.example.org\r\nConnection: Keep-Alive\r\n\r\nProva metodo PUT\r\n";
			msg[20] = "DELETE /pagina2.html HTTP/1.1\r\nHost: www.example.org\r\nConnection: Keep-Alive\r\n\r\n";
			msg[21] = "GET /paginaInesistente HTTP/1.1\r\nHost: www.example.org\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
			msg[22] = "HEAD /paginaInesistente HTTP/1.1\r\nHost: www.example.org\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
			msg[23] = "POST /paginaInesistente HTTP/1.1\r\nHost: www.example.org\r\nConnection: Keep-Alive\r\n\r\n AGGIUNGI UNA RIGA AL FILE\r\n";
			msg[24] = "DELETE /paginaInesistente HTTP/1.1\r\nHost: www.example.org\r\nConnection: Keep-Alive\r\n\r\n";
			// MESSAGGI DI TEST PER HTTP/1.0 con Host
			msg[25] = "GET /pagina.txt HTTP/1.0\r\nHost: www.esempio.it\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
			msg[26] = "POST /pagina.txt HTTP/1.0\r\nHost: www.esempio.it\r\nConnection: Keep-Alive\r\n\r\nAggiungiamo al file una riga\r\n";
			msg[27] = "HEAD /pagina.txt HTTP/1.0\r\nHost: www.esempio.it\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n";
			msg[28] = "GET /pagina.txt HTTP/1.0\r\nHost: www.esempio.it\r\nConnection: Keep-Alive\r\nIf-Modified-Since: Mon, 22 Jan 2018 14:08:54 GMT\r\n\r\n";
			msg[29] = "GET /pagina.txt HTTP/1.0\r\nHost: www.esempio.it\r\nConnection: Keep-Alive\r\n\r\n";
			msg[30] = "GET /pagina.txt HTTP/1.0\r\nHost: www.esempio.it\r\nConnection: close\r\n\r\n";
			boolean connection = true;
			int i = 0;
			while (connection) {
				int count = 0;
				while (is.available() == 0) {
					if (count == 0) {
						if (i == 31) {
							connection = false;
							sock.close();
							break;
						}
						System.out.println("QUESTA E' LA RICHIESTA:");
						System.out.println(msg[i]);
						System.out.println("Rispondere con Y quando si è pronti.");
						String okToWrite = new BufferedReader(new InputStreamReader(System.in)).readLine();
						if (UtilityForMyProject.equalsIfNotNull(okToWrite, "Y")) {
							try {
							os.write(msg[i].getBytes());
							} catch (SocketException e) {
								System.out.println("Socket Closed From Server");
								e.printStackTrace();
								connection=false;
								break;
							}
							count++;
							i++;
						}
					}
				}
				if (i == 31) {
					break;
				}
				count = 0;
				httpis.readHttpReply();
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
