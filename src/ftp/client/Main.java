package ftp.client;

import java.net.UnknownHostException;

public class Main {
	public static void main(String[] args) throws UnknownHostException {
		if (args.length < 2) {
			usage();
			System.exit(1);
		}
		
		try {
			String host = args[0];
			int port = Integer.parseInt(args[1]);
			
			try (Client client = new Client(host, port)) {
				client.start();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.err.println("Invalid port number : '" + args[1] + "'");
			System.exit(1);
		}
	}
	
	public static void usage() {
		System.out.println("Please provide valid command line arguments:");
		System.out.println("  <host> <port>");
		System.out.println("  host: an ip address or a domain name (examples: '127.0.0.1', 'localhost', 'ftp.debian.org')");
		System.out.println("  port: a number (port number)");
	}
}
