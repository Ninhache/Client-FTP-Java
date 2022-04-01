package ftp.client.commands.standard;

import java.io.IOException;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.response.Response;

@FTP({ "rein", "reinitialize", "reset" })
public class REIN extends CommandWithoutParameters {
    @Override
    public Response run(Client client) throws IOException {
        return execServer(client, "REIN");
    }
}
