package ftp.client.commands.standard;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.io.Channel;
import ftp.client.io.Type;
import ftp.client.response.Response;

import java.io.IOException;
import java.util.regex.Matcher;

@FTP({ "list", "ls", "dir" })
public class LIST extends CommandWithoutParameters {
    @Override
    public Response run(Client client) throws IOException {
    	try (Channel data = setupDataChannel(client, Type.ASCII)) {
    		Response resp = execServer(client, "LIST");
    		
    		System.out.println("Data channel:\n" + data.readlns());
    		
    		return resp;
    	}
    }
}
