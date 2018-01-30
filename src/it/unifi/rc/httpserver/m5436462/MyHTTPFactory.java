package it.unifi.rc.httpserver.m5436462;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
/**
 * Questa classe fornisce l'implementazione ai metodi per la creazione di tutte le classi considerate nel progetto.
 * 
 * @author LUCA_P
 *
 */
public class MyHTTPFactory implements HTTPFactory {
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.HTTPFactory#getHTTPInputStream(java.io.InputStream)
	 */
	@Override
	public HTTPInputStream getHTTPInputStream(InputStream is) {
		// TODO Auto-generated method stub
		return new MyHTTPInputStream(is);
	}
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.HTTPFactory#getHTTPOutputStream(java.io.OutputStream)
	 */
	@Override
	public HTTPOutputStream getHTTPOutputStream(OutputStream os) {
		// TODO Auto-generated method stub
		return new MyHTTPOutputStream(os);
	}
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.HTTPFactory#getHTTPServer(int, int, java.net.InetAddress, it.unifi.rc.httpserver.m5436462.HTTPHandler[])
	 */
	@Override
	public HTTPServer getHTTPServer(int port, int backlog, InetAddress address, HTTPHandler... handlers) {
		// TODO Auto-generated method stub
		return new MyHTTPServerForThread(port, backlog, address, handlers);
	}
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.HTTPFactory#getFileSystemHandler1_0(java.io.File)
	 */
	@Override
	public HTTPHandler getFileSystemHandler1_0(File root) {
		// TODO Auto-generated method stub
		return new MyHTTPHandler1_0(root);
	}
	/*
	 * (non-Javadoc)
	 * @see it.unifi.rc.httpserver.m5436462.HTTPFactory#getFileSystemHandler1_0(java.lang.String, java.io.File)
	 */
	@Override
	public HTTPHandler getFileSystemHandler1_0(String host, File root) {
		// TODO Auto-generated method stub
		return new MyHandlerWithHost1_0(host, root);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.unifi.rc.httpserver.m5436462.HTTPFactory#getFileSystemHandler1_1(java.io.
	 * File)
	 */
	@Override
	public HTTPHandler getFileSystemHandler1_1(File root) {
		// TODO Auto-generated method stub
		return new MyHTTPHandler1_1(root);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.unifi.rc.httpserver.m5436462.HTTPFactory#getFileSystemHandler1_1(java.lang
	 * .String, java.io.File)
	 */
	@Override
	public HTTPHandler getFileSystemHandler1_1(String host, File root) {
		// TODO Auto-generated method stub
		return new MyHandlerWithHost1_1(host, root);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.unifi.rc.httpserver.m5436462.HTTPFactory#getProxyHandler()
	 */
	@Override
	public HTTPHandler getProxyHandler() {
		// TODO Auto-generated method stub
		return null;
	}

}
