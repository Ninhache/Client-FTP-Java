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

@FTP("type")
@Name("Data Type")
@Description("Sets the data format used for communicating over the data channel between the client and the server")
@Syntax("TYPE <type>")
@Note("<type> :: A=ASCII | I=Binary | E=EBCDIC")
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
