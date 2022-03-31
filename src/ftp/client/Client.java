package ftp.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ftp.client.commands.Commander;
import ftp.client.io.Channel;
import ftp.client.io.ChannelWrapper;
import ftp.client.io.ClientChannel;
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
		dataWrapper = new ChannelWrapper();
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

		run("manual");
		
		run("login", "user", "12345");
		
		run("cwd", "pyftpdlib");

		run("username", "anonymous");
		run("password", "_");
		
		//run("pasv");
		
		run("pwd");
		run("cd", "pdf/");
		run("cdup");

		//run("structure", "F");
		//run("mode", "S");
		//run("type", "A");
		
		run("ls");
		
		run("noop");
		
		run("quit");
		
		//throw new IOException("End of loop");
	}
	
	public void setDataChannel(Channel channel) {
		dataWrapper.setChannel(channel);
	}
	
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
}
