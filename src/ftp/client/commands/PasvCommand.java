package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.response.Response;

@FTP("pasv")
public class PasvCommand extends Command {
	@Override
	protected String getParamsExpression() {
		return ".*";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		return send(client, "PASV");
	}
}
