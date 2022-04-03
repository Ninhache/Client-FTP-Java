package ftp.client.commands.clientside;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Note;
import ftp.client.annotations.Syntax;
import ftp.client.commands.Command;
import ftp.client.io.ChannelDirection;
import ftp.client.response.Response;

@FTP({ "data", "link", "direction" })
@Name("Data Channel Attribution Mode")
@Description("Defines who will be the data channel server and client")
@Syntax("DATA <direction>")
@Note("<direction> :: A=Active (PORT: server) | P=Passive (PASV: client)")
public class DATA extends Command {
	public static final Map<String, String> VALUES = new HashMap<>();
	
	static {
		VALUES.put("A", "Active");
		VALUES.put("P", "Passive");
	}
	
	@Override
	protected String getParamsExpression() {
		return "(?<direction>[APap])";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		String dir = params.group("direction").toUpperCase();
		client.setPreferedChannelDirection(ChannelDirection.get(dir));
		return Response.create(200, String.format("Switched automated data channel direction to %s (%s)", VALUES.get(dir).toLowerCase(), dir));
	}
}
