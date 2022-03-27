package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;

@FTP({ "pass", "password" })
public class PasswordCommand extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<password>\\S+)";
	}

	@Override
	public void run(Client client, Matcher params) throws IOException {
		send(client, "PASS", params.group("password"));
	}
}
