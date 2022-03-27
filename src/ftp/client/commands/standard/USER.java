package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP({ "user", "username" })
public class USER extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<login>\\S+)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		String username = params.group("login");
		System.out.println("Authenticating as '" + username + "'...");
		Response resp = send(client, "USER", username);
		return resp;
	}
}
