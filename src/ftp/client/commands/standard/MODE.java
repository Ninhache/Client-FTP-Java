package ftp.client.commands.standard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP("mode")
public class MODE extends Command {
	public static final Map<String, String> MODES = new HashMap<>();
	
	static {
		MODES.put("S", "Stream");
		MODES.put("B", "Block");
		MODES.put("C", "Compressed");
	}
	
	@Override
	protected String getParamsExpression() {
		return "(?<mode>[SBCsbc])";
	}
	
	@Override
	public Response run(Client client, Matcher params) throws IOException {
		String mode = params.group("mode").toUpperCase();
		Response resp = execServer(client, "MODE", mode);
		
		if (resp.ok()) {
			System.out.println("Transfer mode set to " + MODES.get(mode));
		} else {
			System.out.println("Mode '" + mode + "' is unsupported");
			failRequest(resp);
		}
		
		return resp;
	}

}
