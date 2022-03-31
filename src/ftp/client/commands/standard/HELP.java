package ftp.client.commands.standard;

import java.io.IOException;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.response.Response;

@FTP({ "help", "?", "man", "manual"  })
public class HELP extends CommandWithoutParameters {
	@Override
	public Response run(Client client) throws IOException {
		Response resp = execServer(client, "HELP");
		
		if (resp.ok()) {
			System.out.println("List of FTP commands accepted by the server");
			System.out.println(resp.getBody());			
		}
		
		return resp;
	}
}
