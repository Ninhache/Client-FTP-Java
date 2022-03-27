package ftp.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ftp.client.commands.Commander;
import ftp.client.io.Channel;

/**
 * Classe gérant un client (canal de controle et canal de données)
 */
public class Client implements Closeable {
	public final Channel control;
	
	/**
	 * Construit un client en se connectant à l'hote sur le port spécifié
	 * @param host URL ou IP du serveur FTP
	 * @param port Port de connexion au serveur FTP
	 * @throws UnknownHostException 
	 */
	public Client(String host, int port) throws UnknownHostException {
		this(InetAddress.getByName(host), port);
	}

	/**
	 * Construit un client se connectant à l'addresse et au port spécifié
	 * @param address L'adresse du serveur FTP
	 * @param port Port de connexion au serveur FTP
	 */
	public Client(InetAddress address, int port) {
		control = new Channel(address, port);
	}

	/**
	 * Connecte le client au serveur souhaité
	 * @throws IOException
	 */
	public void start() {
		try {
			control.connect();
			while (!control.getSocket().isClosed()) 
				loop();
		} catch (IOException exception) {
			exception.printStackTrace();
			close();
		}
	}
	
	protected void loop() throws IOException {
		// Traiter la réception / réponse via les canaux de controle et de données 

		Commander.run(this, "manual");
		
		Commander.run(this, "username", "anonymous");
		Commander.run(this, "password", "1234");
		
		Commander.run(this, "login", "anonymous", "1234");
		
		Commander.run(this, "pasv");
		
		throw new IOException("End of loop");
	}
	
	/**
	 * Gère la destruction des ressources utilisées par l'objet (flux de données,...)
	 */
	@Override
	public void close() {
		if (control != null) {
			control.close();
		}
	}
}
