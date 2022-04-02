package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Note;
import ftp.client.annotations.Syntax;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP({ "user", "username" })
@Name("Username")
@Description("Authenticate as the apecified user")
@Syntax("USER <username>")
@Note("This command must be followed by the PASS command to complete the login procedure")
public class USER extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<login>\\p{ASCII}+)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		return execServer(client, "USER", params.group("login"));
	}
}
