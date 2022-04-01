package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP({ "stru", "struct", "structure" })
public class STRU extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<struct>[FRPfrp])";
	}
	
	@Override
	public Response run(Client client, Matcher params) throws IOException {
		String struct = params.group("struct").toUpperCase();
		Response resp = execServer(client, "STRU", struct);

		if (!resp.ok()) {
			failRequest(resp);
		}
		
		return resp;
	}
}
