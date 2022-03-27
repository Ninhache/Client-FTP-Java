package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.response.Response;

@FTP({ "user", "username" })
public class UserCommand extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<login>\\S+)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		return send(client, "USER", params.group("login"));
	}
}
