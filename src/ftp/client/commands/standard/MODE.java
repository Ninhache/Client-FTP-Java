package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Note;
import ftp.client.annotations.Syntax;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP("mode")
@Name("Data Transmission Mode")
@Description("Defines the way data should be encoded for transfer between the client and the server")
@Syntax("MODE <mode>")
@Note("<mode> :: S=Stream | B=Block | C=Compressed")
public class MODE extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<mode>[SBCsbc])";
	}
	
	@Override
	public Response run(Client client, Matcher params) throws IOException {
		Response resp = execServer(client, "MODE", params.group("mode").toUpperCase());
		
		if (!resp.ok()) {
			failRequest(resp);
		}
		
		return resp;
	}
}
