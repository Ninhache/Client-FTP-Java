package ftp.client.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import static ftp.client.io.Utils.*;

public class ServerChannel extends ClientChannel {
	public static final int AUTOMATICALLY_ASSIGNED_PORT = 0;
	public static final int TIMEOUT = 5000;
	
	protected ServerSocket server;
	
	public BufferedReader in;
	public PrintWriter out;
	
	protected boolean waiting = false;
	
	public ServerChannel() throws IOException {
		server = new ServerSocket(AUTOMATICALLY_ASSIGNED_PORT);
		server.setSoTimeout(TIMEOUT);
	}
	
	@Override
	public void connect() throws IOException {
		Thread thread = new Thread(this::accept);
		try {
			thread.start();
		} catch (Throwable exception) {
			throw new IOException(exception);
		}
	}
	
	protected void accept() {
		try {
			waiting = true;
			socket = server.accept();
			initializeSocket();
		} catch (Throwable exception) {
			throw new RuntimeException(exception);
		} finally {
			waiting = false;
		}
	}
	
	public boolean isWaitingForClient() {
		return waiting;
	}
	
	@Override
	public InetAddress getAddress() {
		try {
			InetSocketAddress addr = (InetSocketAddress) server.getLocalSocketAddress();
			return addr.getAddress();
		} catch (ClassCastException cce) {
			cce.printStackTrace();
			return server.getInetAddress();
		}
	}
	
	@Override
	public int getPort() {
		return server.getLocalPort();
	}
	
	@Override
	public void close() {
		closeSocket(socket);
		closeServerSocket(server);
	}
}
