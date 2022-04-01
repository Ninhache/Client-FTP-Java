package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP("mode")
public class MODE extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<mode>[SBCsbc])";
	}
	
	@Override
	public Response run(Client client, Matcher params) throws IOException {
		String mode = params.group("mode").toUpperCase();
		Response resp = execServer(client, "MODE", mode);
		
		if (!resp.ok()) {
			failRequest(resp);
		}
		
		return resp;
	}

}
