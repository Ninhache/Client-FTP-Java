package ftp.client;

import java.net.UnknownHostException;

public class Main {
	public static final String HOST = "localhost";
	public static final int PORT = 9876;
	
	public static void main(String[] args) throws UnknownHostException {
		try (Client client = new Client(HOST, PORT)) {
			client.start();
		}
	}
}
