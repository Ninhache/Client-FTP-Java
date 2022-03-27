package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;

@FTP("login")
public class LoginCommand extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<login>\\S+) (?<password>\\S+)";
	}

	@Override
	public void run(Client client, Matcher params) throws IOException {
		exec(client, "USER", params.group("login"));
		exec(client, "PASS", params.group("password"));
	}
}
