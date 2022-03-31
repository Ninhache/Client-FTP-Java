package ftp.client.commands.aliases;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.response.Response;
import ftp.client.response.StatusType;

@FTP({ "login", "logn" })
public class LOGIN extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<login>\\p{ASCII}+) (?<password>\\p{ASCII}+)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		Response userResp = execLocal(client, "USER", params.group("login"));
		
		if (userResp.getStatusType() != StatusType.POSITIVE_INTERMEDIATE) {
			return userResp;
		}
		
		return execLocal(client, "PASS", params.group("password"));
	}
}
