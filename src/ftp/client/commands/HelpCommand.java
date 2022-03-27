package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;

@FTP({ "help", "?", "man", "manual"  })
public class HelpCommand extends Command {
	@Override
	protected String getParamsExpression() {
		return ".*";
	}

	@Override
	public void run(Client client, Matcher params) throws IOException {
		send(client, "HELP");
	}
}
