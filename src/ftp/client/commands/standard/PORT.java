package ftp.client.commands.standard;

import java.io.IOException;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Syntax;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.io.ServerChannel;
import ftp.client.response.Response;

@FTP("port")
@Name("Data Port")
@Description("Establishes a data channel by connecting the server to the client")
@Syntax("PORT")
public class PORT extends CommandWithoutParameters {
	@Override
	public Response run(Client client) throws IOException {
		ServerChannel ch = new ServerChannel();
		byte[] addr = client.control.getSocket().getLocalAddress().getAddress();
		
		int h1 = addr[0],
			h2 = addr[1],
			h3 = addr[2],
			h4 = addr[3],
			p1 = Math.floorDiv(ch.getPort(), 256),
		    p2 = Math.floorMod(ch.getPort(), 256);
		
		String port = String.format("%d,%d,%d,%d,%d,%d", h1, h2, h3, h4, p1, p2);
		
		ch.connect();
		Response resp = execServer(client, "PORT", port);
		
		while(ch.isWaitingForClient());
		
		requireOK(resp);
		
		client.setDC(ch);
		
		return resp;
	}
}
