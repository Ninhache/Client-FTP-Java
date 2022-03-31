package ftp.client.commands.standard;

import java.io.IOException;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.response.Response;

@FTP({ "noop", "nop", "keepalive", "heartbeat" })
public class NOOP extends CommandWithoutParameters {
	@Override
	public Response run(Client client) throws IOException {
		return execServer(client, "NOOP");
	}
}
