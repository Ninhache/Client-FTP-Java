package ftp.client.commands.standard;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Syntax;
import ftp.client.commands.Command;
import ftp.client.io.Channel;
import ftp.client.io.Mode;
import ftp.client.io.Structure;
import ftp.client.io.Type;
import ftp.client.response.Response;

import java.io.IOException;
import java.util.regex.Matcher;

@FTP({ "nlst", "nls", "ndir", "nlist", "namelist" })
@Name("Name List")
@Description("Lists the names of the files and folders in the curent working directory")
@Syntax("LIST [directory]")
public class NLST extends Command {
	@Override
	protected String getParamsExpression() {
		return "(?<dir>\\p{ASCII}+)?";
	}
	
    @Override
    public Response run(Client client, Matcher params) throws IOException {
    	String dir = params.group("dir");
    	
    	Response resp = dir != null 
    			? execServer(client, "NLST", dir)
    			: execServer(client, "NLST");
    	
    	requireOK(resp);
    	
    	try (Channel data = client.requireDC(Type.ASCII, Structure.FILE, Mode.STREAM)) {
    		resp = Response.create(resp.getStatus(), data.readlns());
    		
    		client.control.readlns();
    		
    		return resp;
    	}
    }
}
