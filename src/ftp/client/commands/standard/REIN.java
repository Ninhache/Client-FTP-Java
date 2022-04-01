package ftp.client.commands.standard;

import java.io.IOException;

import ftp.client.Client;
import ftp.client.annotations.FTP;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.response.Response;

@FTP({ "reset", "rein" })
public class REIN extends CommandWithoutParameters {
    @Override
    public Response run(Client client) throws IOException {
        Response resp = execServer(client, "REIN");
        if (resp.ok()) {
            System.out.println("Reinitialize connexion ...");
        }
        return resp;
    }
}
