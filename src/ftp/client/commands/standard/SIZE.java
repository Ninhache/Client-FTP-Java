package ftp.client.commands.standard;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.io.Type;
import ftp.client.response.Response;

import java.io.IOException;
import java.util.regex.Matcher;

@FTP({ "size", "stat" })
public class SIZE extends Command {
    @Override
    protected String getParamsExpression() {
        return "(?<size>\\d+)";
    }

    @Override
    public Response run(Client client, Matcher params) throws IOException {
        Response resp = execLocal(client, "TYPE", Type.BINARY.toString());
        if (!resp.ok()) {
        	return resp;
        }
        
        return execServer(client, "SIZE", params.group("size"));
    }
}
