package ftp.client;

import static ftp.client.Keyboard.*;

/**
 * Main utilisé par `mvn exec:java`
 */
public class MavenMain {
	public static void main(String[] args) throws Exception {
		System.out.print("Adresse du serveur hôte: ");
		String host = keyboard.readLine();
		System.out.flush();
		
		System.out.print("Port du service FTP: ");
		String port = keyboard.readLine();
		System.out.flush();
		
		Main.main(new String[] { host, port });
	}
}
