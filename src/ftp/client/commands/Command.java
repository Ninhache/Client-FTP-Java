package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Note;
import ftp.client.annotations.Syntax;
import ftp.client.exceptions.EarlyResponseException;
import ftp.client.response.Response;

/**
 * Base de toutes les commandes FTP gérées par le client
 */
public abstract class Command {
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
		} while (client.control.ready());
		
		return Response.parse(sb.toString());
	}
	
	public String getKeyword() {
		String[] aliases = getAliases();
		return aliases != null && aliases.length > 0
			? aliases[0]
			: null;
	}
	
	public String getName() {
		Name name = getClass().getAnnotation(Name.class);
		return name != null
			? name.value()
			: "";
	}
	
	public String getDescription() {
		Description description = getClass().getAnnotation(Description.class);
		return description != null
			? description.value()
			: "";
	}
	
	public String getSyntax() {
		Syntax syntax = getClass().getAnnotation(Syntax.class);
		return syntax != null
			? syntax.value()
			: "";
	}
	
	public String getNote() {
		Note note = getClass().getAnnotation(Note.class);
		return note != null && note.value().length > 0
			? String.join("\n", note.value())
			: "";
	}
	
	public String[] getAliases() {
		FTP ftp = getClass().getAnnotation(FTP.class);
		return ftp != null && ftp.value().length > 0
			? ftp.value()
			: new String[0];
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
