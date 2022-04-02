package ftp.client.commands.standard;

import java.io.IOException;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Syntax;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.response.Response;

@FTP("pwd")
@Name("Print Working Directory")
@Description("Displays the currently selected directory on the server")
@Syntax("PWD")
public class PWD extends CommandWithoutParameters {
	@Override
	public Response run(Client client) throws IOException {
		return execServer(client, "PWD");
	}
}
