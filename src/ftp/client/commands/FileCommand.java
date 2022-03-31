package ftp.client.commands;

import ftp.client.Client;
import ftp.client.annotations.FTP;

import java.io.IOException;
import java.util.regex.Matcher;

@FTP({ "list", "ls" })
public class FileCommand extends Command {

    @Override
    public void run(Client client, Matcher params) throws IOException {
        send(client, "LIST");
    }

    @Override
    protected String getParamsExpression() {
        return ".*";
    }
}
