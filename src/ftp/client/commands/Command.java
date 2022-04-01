package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ftp.client.Client;
import ftp.client.exceptions.EarlyResponseException;
import ftp.client.response.Response;

/**
 * Base de toutes les commandes FTP gérées par le client
 */
public abstract class Command {
	public static boolean DISPLAY_OUTPUT = true;
	
	/**
	 * Exécute la commande client
	 */
	public Response run(Client client, String parameters) throws IOException {
		String regex = String.format("^%s$", getParamsExpression());
		Pattern paramsPattern = Pattern.compile(regex);
		Matcher paramsMatch = paramsPattern.matcher(parameters);
		
		if (paramsMatch.matches()) {
			return run(client, paramsMatch);
		}
		
		return null;
	}

	/** Exécute la commande client */
	public abstract Response run(Client client, Matcher params) throws IOException;

	/**
	 * Quitte l'exécution de la requête et retourne la réponse passée en paramètre
	 * @param response La réponse à retourner
	 */
	protected void failRequest(Response response) {
		throw new EarlyResponseException(response);
	}
	
	/**
	 * Envoie une commande textuelle brute au serveur et récupère la réponse
	 */
	protected Response execServer(Client client, String... params) throws IOException {
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
				//System.err.println(line);				
			}
		} while (client.control.ready());
		
		Response resp = Response.parse(sb.toString());
		
		if (DISPLAY_OUTPUT) {
			System.err.println(resp.toString(false));				
		}
		
		return resp;
	}
	
	/**
	 * Exécute une commande avec le gestionnaire local correspondant
	 */
	protected Response execLocal(Client client, String... params) throws IOException {
		return Commander.process(client, params);
	}
	
	/**
	 * Expression régulière utilisée pour lire les paramètres d'une commande
	 * @return Une expression régulière
	 */
	protected abstract String getParamsExpression();
}
