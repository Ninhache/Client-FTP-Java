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
	
	public static final void run(Client client, String... input) throws IOException {
		String command = String.join(" ", input);
		
		Matcher match = COMMAND_PATTERN.matcher(command);
		
		if (!match.matches()) {
			System.err.println("Invalid command: " + command);
			return;
		}
		
		String name = match.group("command").toLowerCase();
		String params = match.group("params");
		
		Command comm = COMMANDS.get(name);
		
		if (comm != null) {
			System.out.println("Executing '" + command + "' with handler " + comm.getClass().getSimpleName());
			comm.run(client, params);			
		} else {
			System.err.println("Command '" + name + "' is unknown");
		}
	}
	
	public static final Set<String> commandList() {
		return COMMANDS.keySet();
	}
}
