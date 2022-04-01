package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP({ "rmd", "rmdir", "rm" } )
public class RMD extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<dir>\\p{ASCII}+)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		return execServer(client, "RMD", params.group("dir"));
	}
}
