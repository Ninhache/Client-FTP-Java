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
import ftp.client.io.Channel;
import ftp.client.io.ClientChannel;
import ftp.client.response.Response;

@FTP("pasv")
@Name("Passive")
@Description("Establishes a data channel by connecting the client to the server")
@Syntax("PASV")
public class PASV extends CommandWithoutParameters {
	public static final String REGEX = ".*\\((?<h1>\\d{1,3}+),(?<h2>\\d{1,3}+),(?<h3>\\d{1,3}+),(?<h4>\\d{1,3}+),(?<p1>\\d{1,3}+),(?<p2>\\d{1,3}+)\\).*";
	public static final Pattern PATTERN = Pattern.compile(REGEX);

	@Override
	public Response run(Client client) throws IOException {
		Response resp = execServer(client, "PASV");

		requireOK(resp);
		
		Matcher m = PATTERN.matcher(resp.getStatusMessage());
		if (!m.matches()) {
			return resp;
		}
		
		String host = String.format("%s.%s.%s.%s", m.group("h1"), m.group("h2"), m.group("h3"), m.group("h4"));
		int port = Integer.parseInt(m.group("p1")) * 256 + Integer.parseInt(m.group("p2"));
		
		Channel ch = new ClientChannel(host, port);
		ch.connect();
		client.setDC(ch);
		
		return resp;
	}
}
