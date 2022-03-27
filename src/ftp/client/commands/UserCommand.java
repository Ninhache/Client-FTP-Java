package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;

@FTP({ "user", "username", "utilisateur" })
public class UserCommand extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<login>\\S+)";
	}

	@Override
	public void run(Client client, Matcher params) throws IOException {
		send(client, "USER", params.group("login"));
	}
}
