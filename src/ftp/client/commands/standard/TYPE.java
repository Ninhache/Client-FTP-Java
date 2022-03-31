package ftp.client.commands.standard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP("type")
public class TYPE extends Command {
	public static final Map<String, String> TYPES = new HashMap<>();
	
	static {
		TYPES.put("A", "ASCII");
		TYPES.put("I", "Binary");
		TYPES.put("E", "EBCDIC");
	}
	
	@Override
	protected String getParamsExpression() {
		return "(?<type>[AIEaie])";
	}
	
	@Override
	public Response run(Client client, Matcher params) throws IOException {
		String type = params.group("type").toUpperCase();
		Response resp = execServer(client, "TYPE", type);

		if (resp.ok()) {
			System.out.println("Type set to " + TYPES.get(type));
		} else {
			System.out.println("Type '" + type + "' is unsupported");
			failRequest(resp);
		}
		
		return resp;
	}
}
