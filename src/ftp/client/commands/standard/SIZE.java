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

@FTP("size")
public class SIZE extends Command {

    @Override
    protected String getParamsExpression() {
        return "(?<size>\\p{ASCII}+)";
    }

    // TODO: CHECK IF THE COMMAND IS VALID BECAUSE RETURN IS IN "ERROR" SECTION
    @Override
    public Response run(Client client, Matcher params) throws IOException {
        execServer(client, "TYPE", Type.BINARY.toString());
        Response resp = execServer(client, "SIZE", params.group("size"));

        System.out.println("RESPONSE:" + resp.getBody());
        return resp;
    }
}
