package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.response.Response;

@FTP({ "login", "logn" })
public class LoginCommand extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<login>\\S+) (?<password>\\S+)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		exec(client, "USER", params.group("login"));
		return exec(client, "PASS", params.group("password"));
	}
}
