package ftp.client.commands.standard;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.Command;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.response.Response;

import java.io.IOException;
import java.util.regex.Matcher;

@FTP({ "list", "ls", "dir" })
public class LIST extends CommandWithoutParameters {

    @Override
    public Response run(Client client) throws IOException {
        return execServer(client, "LIST");
    }
}
