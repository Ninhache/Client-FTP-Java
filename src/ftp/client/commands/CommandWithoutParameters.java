package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.response.Response;

public abstract class CommandWithoutParameters extends Command {
	@Override
	protected String getParamsExpression() {
		return ".*";
	}
	
	@Override
	public Response run(Client client, Matcher params) throws IOException {
		return run(client);
	}
	
	public abstract Response run(Client client) throws IOException;
}