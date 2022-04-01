package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Syntax;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.response.Response;

@FTP("pwd")
@Name("Print Working Directory")
@Description("Displays the currently selected directory on the server")
@Syntax("PWD")
public class PWD extends CommandWithoutParameters {
	public static final String REGEX = ".*\"(?<dir>\\S+)\".*";
	public static final Pattern PATTERN = Pattern.compile(REGEX);

	@Override
	public Response run(Client client) throws IOException {
		Response resp = execServer(client, "PWD");
		
		Matcher m = PATTERN.matcher(resp.getStatusMessage());
		if (!m.matches()) {
			return resp;
		}
		
		String directory = m.group("dir");
		
		System.out.println("Current directory on server is '" + directory + "'");
		
		return resp;
	}
}
