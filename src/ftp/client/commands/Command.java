package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ftp.client.Client;

/**
 * Base de toutes les commandes FTP gérées par le client
 */
public abstract class Command {
	/** Exécute la commande client */
	public void run(Client client, String parameters) throws IOException {
		Pattern paramsPattern = Pattern.compile(getParamsExpression());
		Matcher paramsMatch = paramsPattern.matcher(parameters);
		
		if (paramsMatch.matches()) {
			run(client, paramsMatch);
		}
	}

	/** Exécute la commande client */
	public abstract void run(Client client, Matcher params) throws IOException;

	/**
	 * Envoie une commande textuelle brute au serveur
	 */
	protected void send(Client client, String... params) throws IOException {
		client.control.println(params);
		do {
			System.out.println(client.control.readln());
		} while (client.control.ready());
	}
	
	/**
	 * Execute une commande client
	 */
	protected void exec(Client client, String... params) throws IOException {
		Commander.run(client, params);
	}
	
	
	
	/**
	 * Expression régulière utilisée pour lire les paramètres d'une commande
	 * @return Une expression régulière
	 */
	protected abstract String getParamsExpression();
}
