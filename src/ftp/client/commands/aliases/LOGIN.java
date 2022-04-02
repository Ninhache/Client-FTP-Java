package ftp.client.commands.aliases;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Syntax;
import ftp.client.commands.Command;
import ftp.client.response.Response;
import ftp.client.response.StatusType;

@FTP({ "login", "logn" })
@Name("Login")
@Description("Authenticate using a username and password to start a FTP session")
@Syntax("LOGIN <username> <password>")
public class LOGIN extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<username>\\p{ASCII}+) (?<password>\\p{ASCII}+)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		Response resp = execLocal(client, "USER", params.group("username"));
		
		if (resp.getStatusType() != StatusType.POSITIVE_INTERMEDIATE) {
			failRequest(resp);
		}
		
		return execLocal(client, "PASS", params.group("password"));
	}
}
