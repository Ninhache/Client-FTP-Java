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

import static ftp.client.Keyboard.*;

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
		welcome();
	}

	protected void welcome() {
		System.out.println("FTP Client - M4105C");
		System.out.println("Type 'HELP' for a list of commands and 'HELP <command>' for command-specific help");
		System.out.println("Type 'SVHP' if you intent to use the default FTP HELP command instead");
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
		System.out.print("$ ");
		String prompt = keyboard.readLine();
		System.out.flush();
		command(prompt);
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
	protected Response command(String... values) throws IOException {
		Response resp = run(values);
		if (resp != null) {
			System.out.println(resp.toString(true));			
		}
		return resp;
	}
	
	protected Response run(String... values) throws IOException {
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
