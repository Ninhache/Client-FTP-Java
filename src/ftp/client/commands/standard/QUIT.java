package ftp.client.commands.standard;

import java.io.IOException;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.response.Response;

@FTP({ "quit", "bye", "exit", "disconnect", "leave" })
public class QUIT extends CommandWithoutParameters {
	@Override
	public Response run(Client client) throws IOException {
		Response resp = execServer(client, "QUIT");
		if (resp.ok()) {
			System.out.println("Quitting FTP client...");
			client.close();
		}
		return resp;
	}

}
