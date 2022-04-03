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

@FTP({ "stru", "struct", "structure" })
@Name("Transfer Structure")
@Description("Desides how data will be structured inside packets for transfer between the client and the server")
@Syntax("STRU <stru>")
@Note("<struc> :: F=File | R=Record | P=Page")
public class STRU extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<struct>[FRPfrp])";
	}
	
	@Override
	public Response run(Client client, Matcher params) throws IOException {
		Response resp = execServer(client, "STRU", params.group("struct").toUpperCase());
		requireOK(resp);
		return resp;
	}
}
