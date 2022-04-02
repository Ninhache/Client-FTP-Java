package ftp.client;

import java.net.UnknownHostException;
import java.nio.file.Path;

public class Main {
	public static final String HOST = "localhost";
	public static final int PORT = 9876;
	
	public static void main(String[] args) throws UnknownHostException {
		welcome();
		try (Client client = new Client(HOST, PORT)) {
			client.start();
		}
	}
	
	protected static void welcome() {
		String cwd = Path.of("").toAbsolutePath().toString();
		System.out.println("Curent directory: " + cwd);
	}
}
