package ftp.client.commands.standard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP({ "stru", "struct", "structure" })
public class STRU extends Command {
	public static final Map<String, String> STRUCTURES = new HashMap<>();
	
	static {
		STRUCTURES.put("F", "File");
		STRUCTURES.put("R", "Record");
		STRUCTURES.put("P", "Page");
	}
	
	@Override
	protected String getParamsExpression() {
		return "(?<struct>[FRPfrp])";
	}
	
	@Override
	public Response run(Client client, Matcher params) throws IOException {
		String struct = params.group("struct").toUpperCase();
		Response resp = send(client, "STRU", struct);

		if (resp.ok()) {
			System.out.println("File transfer structure set to " + STRUCTURES.get(struct));
		} else {
			System.out.println("Structure '" + struct + "' is unsupported");
		}
		
		return resp;
	}
}
