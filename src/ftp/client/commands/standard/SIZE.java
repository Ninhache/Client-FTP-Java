package ftp.client.commands.standard;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Syntax;
import ftp.client.commands.Command;
import ftp.client.io.Type;
import ftp.client.response.Response;

import java.io.IOException;
import java.util.regex.Matcher;

@FTP({ "size", "stat" })
@Name("File Size")
@Description("Displays a file size in bytes")
@Syntax("SIZE <file path>")
public class SIZE extends Command {
    @Override
    protected String getParamsExpression() {
        return "(?<file>\\p{ASCII}+)";
    }

    @Override
    public Response run(Client client, Matcher params) throws IOException {
        Response resp = execLocal(client, "TYPE", Type.BINARY.toString());
        requireOK(resp);
        
        String file = params.group("file");
        resp = execServer(client, "SIZE", file);
        
        requireOK(resp);
        
        return Response.create(resp.getStatusCode(), String.format("%s is %s bytes", file, resp.getStatusMessage()), resp.getBody());
    }
}
