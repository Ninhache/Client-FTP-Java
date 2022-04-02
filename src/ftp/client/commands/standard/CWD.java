package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Syntax;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP({ "cwd", "cd" })
@Name("Change Working Directory")
@Description("navigates to the desired directory")
@Syntax("CWD <directory>")
public class CWD extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<dir>((\\.\\.|\\.)[\\/\\\\])?\\p{ASCII}+([\\/\\\\]\\p{ASCII}+)*)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		return execServer(client, "CWD", params.group("dir"));
	}
}
