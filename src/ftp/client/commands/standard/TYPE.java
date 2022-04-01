package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP("type")
public class TYPE extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<type>[AIEaie])";
	}
	
	@Override
	public Response run(Client client, Matcher params) throws IOException {
		String type = params.group("type").toUpperCase();
		Response resp = execServer(client, "TYPE", type);

		if (!resp.ok()) {
			failRequest(resp);
		}
		
		return resp;
	}
}
