package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.io.Channel;
import ftp.client.io.ClientChannel;
import ftp.client.response.Response;

@FTP("pasv")
public class PASV extends CommandWithoutParameters {
	public static final String REGEX = ".*\\((?<h1>\\d{1,3}+),(?<h2>\\d{1,3}+),(?<h3>\\d{1,3}+),(?<h4>\\d{1,3}+),(?<p1>\\d{1,3}+),(?<p2>\\d{1,3}+)\\).*";
	public static final Pattern PATTERN = Pattern.compile(REGEX);

	@Override
	public Response run(Client client) throws IOException {
		Response resp = send(client, "PASV");

		Matcher m = PATTERN.matcher(resp.getStatusMessage());
		if (!m.matches()) {
			return Response.create(500, "Unable to parse data channel address.");
		}
		
		String host = String.format("%s.%s.%s.%s", m.group("h1"), m.group("h2"), m.group("h3"), m.group("h4"));
		int port = Integer.parseInt(m.group("p1")) * 256 + Integer.parseInt(m.group("p2"));
		
		Channel passiveChannel = new ClientChannel(host, port);
		passiveChannel.connect();
		client.setDataChannel(passiveChannel);
		
		return resp;
	}
}
