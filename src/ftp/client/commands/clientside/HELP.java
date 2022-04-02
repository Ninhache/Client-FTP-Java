package ftp.client.commands.clientside;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Syntax;
import ftp.client.commands.Command;
import ftp.client.commands.Commander;
import ftp.client.response.Response;

import static ftp.client.Utils.*;

@FTP({ "help", "manual", "?" })
@Name("User Manual (Help)")
@Description("Shows the list of commands that this client supports as well as command-specific details")
@Syntax("")
public class HELP extends Command {
	public static int HELP_SUCCESS = 214;
	@Override
	protected String getParamsExpression() {
		return "(?<command>\\p{ASCII}+)?";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		String command = params.group("command");
		return command != null
			? commandHelp(client, command)
			: generalHelp(client);
	}
	
	protected Response generalHelp(Client client) {
		StringBuilder sb = new StringBuilder();
		
		for (Command handler : Commander.getCommandHandlers()) {
			Class<? extends Command> type = handler.getClass();
			
			FTP annFtp = type.getAnnotation(FTP.class);
			Name annName = type.getAnnotation(Name.class);
			
			if (annFtp != null && annName != null) {
				String command = annFtp.value()[0];
				String friendlyName = annName.value();
				
				println(sb, "%8s : %s", command.toUpperCase(), friendlyName);
			}
		}
		
		if (sb.length() == 0) {
			println(sb, "No command found.");
		}
		
		return Response.create(HELP_SUCCESS, "List of available commands", sb.toString());
	}
	
	protected Response commandHelp(Client client, String command) {
		Command cmd = Commander.getAllCommands().get(command.toLowerCase());
		
		if (cmd == null) {
			return Response.create(500, "Command unknown '" + command + "'");
		}
		
		StringBuilder sb = new StringBuilder();
		final String keyword = cmd.getKeyword(),
					 name = cmd.getName(),
					 description = cmd.getDescription(),
					 syntax = cmd.getSyntax(),
					 note = cmd.getNote();
		final String[] aliases = cmd.getAliases();
		
		println(sb, "%s - %s", keyword.toUpperCase(), name);
		if (description.length() > 0) {
			println(sb, description);
		}
		
		if (syntax.length() > 0) {
			println(sb, "Syntax : %s", syntax);
		}
		
		if (aliases.length > 0) {
			println(sb, "Aliases : %s", String.join(" ", aliases));
		}
		
		if (note.length() > 0) {
			println(sb, "Note : %s", note);
		}
		
		return Response.create(HELP_SUCCESS, "Help for '" + command + "'", sb.toString());
	}
}
