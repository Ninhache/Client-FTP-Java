package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ftp.client.Client;
import ftp.client.response.Response;

/**
 * Base de toutes les commandes FTP gérées par le client
 */
public abstract class Command {
	public static boolean DISPLAY_OUTPUT = false;
	
	/** Exécute la commande client */
	public Response run(Client client, String parameters) throws IOException {
		Pattern paramsPattern = Pattern.compile(getParamsExpression());
		Matcher paramsMatch = paramsPattern.matcher(parameters);
		
		if (paramsMatch.matches()) {
			return run(client, paramsMatch);
		}
		
		return null;
	}

	/** Exécute la commande client */
	public abstract Response run(Client client, Matcher params) throws IOException;

	/**
	 * Envoie une commande textuelle brute au serveur
	 */
	protected Response send(Client client, String... params) throws IOException {
		client.control.println(params);
		
		StringBuilder sb = new StringBuilder();
		String line = "";
		
		do {
			line = client.control.readln();
			
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(line);
			
			if (DISPLAY_OUTPUT) {
				System.out.println(line);				
			}
		} while (client.control.ready());
		
		return Response.parse(sb.toString());
	}
	
	/**
	 * Execute une commande client
	 */
	protected Response exec(Client client, String... params) throws IOException {
		return Commander.run(client, params);
	}
	
	/**
	 * Expression régulière utilisée pour lire les paramètres d'une commande
	 * @return Une expression régulière
	 */
	protected abstract String getParamsExpression();
}
