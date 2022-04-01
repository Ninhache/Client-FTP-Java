package ftp.client.commands.standard;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.io.Channel;
import ftp.client.io.Mode;
import ftp.client.io.Structure;
import ftp.client.io.Type;
import ftp.client.response.Response;

import java.io.IOException;
import java.util.regex.Matcher;

@FTP({ "list", "ls", "dir" })
public class LIST extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<dir>\\p{ASCII}+)?";
	}
	
    @Override
    public Response run(Client client, Matcher params) throws IOException {
    	String dir = params.group("dir");
    	
    	Response resp = dir != null 
    			? execServer(client, "LIST", dir)
    			: execServer(client, "LIST");
    	
    	try (Channel data = client.requireDC(Type.ASCII, Structure.FILE, Mode.STREAM)) {
    		System.out.println(data.readlns());
    	}
    	
    	return resp;
    }
}
