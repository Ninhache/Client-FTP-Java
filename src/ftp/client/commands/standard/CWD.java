package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP({ "cwd", "cd" })
public class CWD extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<dir>((\\.\\.|\\.)[\\/\\\\])?\\p{ASCII}+([\\/\\\\]\\p{ASCII}+)*)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}


}
