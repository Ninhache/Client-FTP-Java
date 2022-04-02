package ftp.client.commands.standard;

import java.io.IOException;
import java.util.regex.Matcher;

import ftp.client.Client;
import ftp.client.annotations.Description;
import ftp.client.annotations.FTP;
import ftp.client.annotations.Name;
import ftp.client.annotations.Note;
import ftp.client.annotations.Syntax;
import ftp.client.commands.Command;
import ftp.client.response.Response;

@FTP({ "pass", "password" })
@Name("Password")
@Description("Send the password of your account to complete the login procedure")
@Syntax("PASS <password>")
@Note("This command must follow the USER command")
public class PASS extends Command {
	public static final int AUTH_FAILED_READTIMEOUT = 10;
	
	@Override
	protected String getParamsExpression() {
		return "(?<password>\\p{ASCII}+)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		Response resp = execServer(client, "PASS", params.group("password"));
		
		if (!resp.ok()) {
			int retries = 0;
			while (resp.getStatusCode() == STATUS_TIMEOUT && (++retries < AUTH_FAILED_READTIMEOUT)) {
				resp = execLocal(client, "NOOP");
			}
			client.control.readlns();
		}
		
		return resp;
	}
}
