package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP({ "cwd", "cd" })
public class CWD extends Command {
	public static final String REGEX = ".*\"(?<dir>\\S+)\".*";
	public static final Pattern PATTERN = Pattern.compile(REGEX);
	
	@Override
	protected String getParamsExpression() {
		return "(?<dir>((\\.\\.|\\.)[\\/\\\\])?\\p{ASCII}+([\\/\\\\]\\p{ASCII}+)*)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		Response resp = execServer(client, "CWD", params.group("dir"));
		
		Matcher m = PATTERN.matcher(resp.getStatusMessage());
		if (!m.matches()) {
			return resp;
		}
		
		String directory = m.group("dir");
		
		System.out.println("Current directory on server is '" + directory + "'");
		
		return resp;
	}


}
