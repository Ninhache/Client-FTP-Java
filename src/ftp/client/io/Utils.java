package ftp.client.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class Utils {
	private Utils() {}
	
	/**
	 * Ferme une Socket TCP si elle est ouverte en gérant les exceptions
	 * @param socket la socket à fermer
	 */
	public static final void closeSocket(Socket socket) {
		if (socket != null && !socket.isClosed()) {
			try {
				closeInputStream(socket.getInputStream());
				closeOutputStream(socket.getOutputStream());
				socket.close();				
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	/**
	 * Ferme une Socket serveur TCP si elle est ouverte en gérant les exceptions
	 * @param socket la socket serveur à fermer
	 */
	public static final void closeServerSocket(ServerSocket socket) {
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();				
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	/**
	 * Ferme un flux de données entrant
	 * @param stream Flux à fermer
	 */
	public static final void closeInputStream(InputStream stream) {
		try {
			stream.close();			
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Ferme un flux de données sortant
	 * @param stream Flux à fermer
	 */
	public static final void closeOutputStream(OutputStream stream) {
		try {
			stream.close();			
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
