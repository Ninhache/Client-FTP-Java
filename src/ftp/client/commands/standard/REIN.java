package ftp.client.commands.standard;

import java.io.IOException;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Note;
import ftp.client.annotations.Syntax;
import ftp.client.commands.CommandWithoutParameters;
import ftp.client.response.Response;

@FTP({ "rein", "reinitialize", "reset" })
@Name("Reinitialize")
@Description("Terminates the current FTP session and closes established channels")
@Syntax("REIN")
@Note("You will need to use LOGIN or USER & PASS to log back in after sending this command")
public class REIN extends CommandWithoutParameters {
    @Override
    public Response run(Client client) throws IOException {
        return execServer(client, "REIN");
    }
}
