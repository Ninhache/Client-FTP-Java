package ftp.client.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.reflections.Reflections;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.exceptions.EarlyResponseException;
import ftp.client.response.Response;

/** 
 * Système d'analyse de la saisie utilisateur pour l'execution de commandes client
 */
@SuppressWarnings("deprecation")
public final class Commander {
	private Commander() { } // Classe utilitaire statique
	
	public static final String COMMAND_REGEX = "(?<command>\\w+)( (?<params>.*))?";
	public static final Pattern COMMAND_PATTERN = Pattern.compile(COMMAND_REGEX);
	
	protected static final Map<String, Command> COMMANDS;
	
	static {
		COMMANDS = new HashMap<>();
		Reflections reflect = new Reflections("ftp.client.commands");
		
		for (Class<? extends Command> commType : reflect.getSubTypesOf(Command.class)) {
			FTP info = commType.getAnnotation(FTP.class);
			if (info == null) {
				continue;
			}
			
			Command instance = null;
			try {
				instance = commType.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				continue;
			}
			
			System.out.println("Registering " + commType.getName() + " as " + String.join(", ", info.value()));

			for (String alias : info.value()) {
				COMMANDS.put(alias.toLowerCase(), instance);
			}
		}
	}
	
	public static final Response run(Client client, String... input) throws IOException {
		try {
			return process(client, input);
		} catch (EarlyResponseException e) {
			return e.getResponse();
		}
	}
	
	public static final Response process(Client client, String... input) throws IOException {
		String command = String.join(" ", input);
		
		Matcher match = COMMAND_PATTERN.matcher(command);
		
		if (!match.matches()) {
			System.err.println("Invalid command: " + command);
			return null;
		}
		
		String name = match.group("command").toLowerCase();
		String params = match.group("params");
		if (params == null) {
			params = "";
		}
		
		Command comm = COMMANDS.get(name);
		
		if (comm != null) {
			//System.out.println("Executing '" + command + "' with handler " + comm.getClass().getSimpleName());
			Response resp = comm.run(client, params);
			//System.out.println(resp);
			return resp;
		} else {
			System.err.println("Command '" + name + "' is unknown");
		}
		
		return null;
	}
	
	public static final Set<String> commandList() {
		return COMMANDS.keySet();
	}
}
