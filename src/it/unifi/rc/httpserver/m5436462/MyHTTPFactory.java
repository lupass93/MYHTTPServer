package it.unifi.rc.httpserver.m5436462;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public class MyHTTPFactory implements HTTPFactory {

	@Override
	public HTTPInputStream getHTTPInputStream(InputStream is) {
		// TODO Auto-generated method stub
		return new MyHTTPInputStream(is);
	}

	@Override
	public HTTPOutputStream getHTTPOutputStream(OutputStream os) {
		// TODO Auto-generated method stub
		return new MyHTTPOutputStream(os);
	}

	@Override
	public HTTPServer getHTTPServer(int port, int backlog, InetAddress address, HTTPHandler... handlers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HTTPHandler getFileSystemHandler1_0(File root) {
		// TODO Auto-generated method stub
		return new MyHTTPHandler1_0(root);
	}

	@Override
	public HTTPHandler getFileSystemHandler1_0(String host, File root) {
		// TODO Auto-generated method stub
		return new MyHandlerWithHost1_0(host, root);
	}

	@Override
	public HTTPHandler getFileSystemHandler1_1(File root) {
		// TODO Auto-generated method stub
		return new MyHTTPHandler1_1(root);
	}

	@Override
	public HTTPHandler getFileSystemHandler1_1(String host, File root) {
		// TODO Auto-generated method stub
		return new MyHandlerWithHost1_1(host, root);
	}

	@Override
	public HTTPHandler getProxyHandler() {
		// TODO Auto-generated method stub
		return null;
	}

}
