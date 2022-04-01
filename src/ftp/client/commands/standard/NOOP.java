package ftp.client.commands.standard;

import java.io.IOException;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Syntax;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.response.Response;

@FTP({ "noop", "nop", "keepalive", "heartbeat" })
@Name("No Operation")
@Description("Sends a dummy packed to keep the connection alive")
@Syntax("NOOP")
public class NOOP extends CommandWithoutParameters {
	@Override
	public Response run(Client client) throws IOException {
		return execServer(client, "NOOP");
	}
}
