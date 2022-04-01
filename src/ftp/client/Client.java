package ftp.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ftp.client.commands.Commander;
import ftp.client.io.Channel;
import ftp.client.io.ChannelWrapper;
import ftp.client.io.ClientChannel;
import ftp.client.io.Mode;
import ftp.client.io.Structure;
import ftp.client.io.Type;
import ftp.client.response.Response;

/**
 * Classe gérant un client (canal de controle et canal de données)
 */
public class Client implements Closeable {
	public final Channel control;
	public final Channel data;
	
	protected final ChannelWrapper dataWrapper;
	
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
		control = new ClientChannel(address, port);
		dataWrapper = new ChannelWrapper(this::onDataChannelClosed);
		data = dataWrapper;
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

		//run("manual");
		
		run("login", "user", "12345");
		
		run("cwd", "pyftpdlib");

		run("username", "anonymous");
		run("password", "_");
		
		run("pwd");
		run("cd", "pdf/");
		run("list");
		run("cdup");

		run("ls");
		
		//run("noop");
		run("list");
		
		
		run("quit");
		
		//throw new IOException("End of loop");
	}
	
	@SuppressWarnings("resource")
	public Channel requireDC(Type type, Structure stru, Mode mode) throws IOException {
		return configureDC(type, stru, mode).createDC().data;
	}
	
	/**
	 * Configure le canal de données du client FTP
	 */
	public Client configureDC(Type type, Structure stru, Mode mode) throws IOException {
		run("TYPE", type.toString());
		run("STRU", stru.toString());
		run("MODE", mode.toString());
		
		return this;
	}
	
	/**
	 * Créée le canal de données
	 */
	public Client createDC() throws IOException {
		run("PASV");
		return this;
	}
	
	/**
	 * Assigne le canal de données du client FTP
	 */
	public Client setDC(Channel channel) {
		dataWrapper.setChannel(channel);
		return this;
	}
	
	/**
	 * Exécute une commande et retourne sa réponse
	 */
	public Response run(String... values) throws IOException {
		return Commander.run(this, values);
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
	
	protected void onDataChannelClosed(Channel channel) {
		System.out.println("=== DATA CHANNEL CLOSED ===");
	}
}
