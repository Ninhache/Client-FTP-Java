package ftp.client.io;

import static ftp.client.io.Utils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Encapsule une socket TCP ainsi que ses flux d'entrée et de sortie
 */
public class ClientChannel implements Channel {
	protected Socket socket;
	protected final InetAddress ADDRESS;
	protected final int PORT;
	
	public BufferedReader in;
	public PrintWriter out;
	
	/**
	 * Construit un canal sur l'hote et le port spécifiés
	 * @param host URL ou IP du serveur FTP
	 * @param port Port de connexion
	 * @throws UnknownHostException 
	 */
	public ClientChannel(String host, int port) throws UnknownHostException {
		this(InetAddress.getByName(host), port);
	}
	
	/**
	 * Construit un canal sur l'adresse et le port spécifiés
	 * @param address L'adresse sur laquelle se connecter
	 * @param port Port de connexion
	 */
	public ClientChannel(InetAddress address, int port) {
		ADDRESS = address;
		PORT = port;
	}
	
	@Override
	public Socket getSocket() {
		return socket;
	}

	@Override
	public InetAddress getAddress() {
		return ADDRESS;
	}
	
	@Override
	public String getHost() {
		return getAddress().getHostName();
	}

	@Override
	public int getPort() {
		return socket != null ? socket.getLocalPort() : PORT;
	}
	
	/**
	 * Initialise la connexion et les flux de données
	 * @throws IOException
	 */
	@Override
	public void connect() throws IOException {
		socket = new Socket(ADDRESS, PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}

	@Override
	public void println(String value) {
		out.println(value);
	}

	@Override
	public void println(String... value) {
		println(String.join(" ", value));
	}

	@Override
	public String readln() throws IOException {
		return in.readLine();
	}
	
	@Override
	public boolean ready() throws IOException {
		return in.ready();
	}
	
	@Override
	public void close() {
		try {
			out.close();
			in.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		closeSocket(socket);
	}
}
