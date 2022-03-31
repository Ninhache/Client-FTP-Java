package ftp.client.commands.standard;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.io.Channel;
import ftp.client.io.Mode;
import ftp.client.io.Structure;
import ftp.client.io.Type;
import ftp.client.response.Response;

import java.io.IOException;

@FTP({ "list", "ls", "dir" })
public class LIST extends CommandWithoutParameters {
    @Override
    public Response run(Client client) throws IOException {
    	try (Channel data = client.requireDC(Type.ASCII, Structure.FILE, Mode.STREAM)) {
    		Response resp = execServer(client, "LIST");
    		
    		System.out.println("Received data from DC:\n" + data.readlns());
    		
    		return resp;
    	}
    }
}
