package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP({ "pass", "password" })
public class PASS extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<password>\\p{ASCII}+)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		Response resp = send(client, "PASS", params.group("password"));
		
		if (resp.ok()) {
			System.out.println("Login successful!");
		}
		
		return resp;
	}
}
