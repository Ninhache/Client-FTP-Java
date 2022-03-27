package ftp.client.commands;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.response.Response;
import ftp.client.response.StatusType;

@FTP({ "login", "logn" })
public class LoginCommand extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<login>\\S+) (?<password>\\S+)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		Response userResp = exec(client, "USER", params.group("login"));
		
		if (userResp.getStatusType() != StatusType.POSITIVE_INTERMEDIATE) {
			return userResp;
		}
		
		return exec(client, "PASS", params.group("password"));
	}
}
