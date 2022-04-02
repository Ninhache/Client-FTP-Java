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

@FTP({ "pass", "password" })
@Name("Password")
@Description("Send the password of your account to complete the login procedure")
@Syntax("PASS <password>")
@Note("This command must follow the USER command")
public class PASS extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<password>\\p{ASCII}+)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		return execServer(client, "PASS", params.group("password"));
	}
}
