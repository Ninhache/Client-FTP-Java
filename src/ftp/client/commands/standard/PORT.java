package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Syntax;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP("port")
@Name("Data Port")
@Description("Establishes a data channel by connecting the server to the client")
@Syntax("PORT")
public class PORT extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<port>\\d+)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		return execServer(client, "PORT", params.group("port"));
	}
}
