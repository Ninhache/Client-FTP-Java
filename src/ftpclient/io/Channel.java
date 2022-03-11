package ftpclient.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static ftpclient.io.Utils.*;

/**
 * Encapsule une socket TCP ainsi que ses flux d'entrée et de sortie
 */
public class Channel implements Closeable {
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
	public Channel(String host, int port) throws UnknownHostException {
		this(InetAddress.getByName(host), port);
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public InetAddress getAddress() {
		return ADDRESS;
	}
	
	public int getPort() {
		return socket != null ? socket.getLocalPort() : PORT;
	}
	
	/**
	 * Initialise la connexion et les flux de données
	 * @throws IOException
	 */
	public void connect() throws IOException {
		socket = new Socket(ADDRESS, PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}

	/**
	 * Construit un canal sur l'adresse et le port spécifiés
	 * @param address L'adresse sur laquelle se connecter
	 * @param port Port de connexion
	 */
	public Channel(InetAddress address, int port) {
		ADDRESS = address;
		PORT = port;
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
