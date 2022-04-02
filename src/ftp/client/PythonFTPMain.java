package ftp.client;

/**
 * Main utilisé pour lancer le client depuis eclipse, inutile en production
 */
public class PythonFTPMain {
	public static final String HOST = "localhost";
	public static final int PORT = 9876;
	
	public static void main(String[] args) throws Exception {
		Main.main(new String[] { HOST, "" + PORT });
	}
}
