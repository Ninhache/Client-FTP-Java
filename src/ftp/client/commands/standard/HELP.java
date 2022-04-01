package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Syntax;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP({ "help", "?", "man", "manual"  })
@Name("Help")
@Description("Displays manual entries for FTP commands accepted by the server")
@Syntax("HELP [command]")
public class HELP extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<command>\\p{ASCII}+)?";
	}
	
	@Override
	public Response run(Client client, Matcher params) throws IOException {
		String command = params.group("command");
		
		Response resp = command != null
				? execServer(client, "HELP", command)
				: execServer(client, "HELP");
		
		if (resp.ok()) {
			//System.out.println("List of FTP commands accepted by the server");
			System.out.println(resp.getBody());			
		}
		
		return resp;
	}

}
