package ftpclient;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import ftpclient.io.Channel;

import static ftpclient.io.Utils.*;

/**
 * Classe gérant un client (canal de controle et canal de données)
 */
public class Client implements Closeable {
	protected Channel controlChannel;
	
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
		controlChannel = new Channel(address, port);
	}

	/**
	 * Connecte le client au serveur souhaité
	 * @throws IOException
	 */
	public void start() {
		try {
			controlChannel.connect();
			while (!controlChannel.getSocket().isClosed()) 
				loop();
		} catch (IOException exception) {
			close();
		}
	}
	
	protected void loop() {
		// Traiter la réception / réponse via les canaux de controle et de données 
	}
	
	/**
	 * Gère la destruction des ressources utilisées par l'objet (flux de données,...)
	 */
	@Override
	public void close() {
		if (controlChannel != null) {
			controlChannel.close();
		}
	}
}
