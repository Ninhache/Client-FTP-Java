package ftp.client.commands.standard;

import java.io.IOException;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Syntax;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.response.Response;

@FTP({ "quit", "bye", "exit", "disconnect", "leave", "ragequit", "osef", "sardoche" })
@Name("Quit")
@Description("Closes the connection to the server and terminates the session")
@Syntax("QUIT")
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
