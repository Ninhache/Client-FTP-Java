package ftp.client.commands.standard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;

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

@FTP({ "retr", "retrieve", "download", "get", "acquire", "down", "dl" })
@Name("Download File")
@Description("Download a file from the server to the local machine")
@Syntax("RETR <server file> [local destination]")
public class RETR extends Command {
	public static final int BUFFER_SIZE = 2048;
	
	@Override
	protected String getParamsExpression() {
		return "(?<file>\\p{ASCII}+)( (?<dest>\\p{ASCII}+))?";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		String fileName = params.group("file");
		String destName = params.group("dest");
		
		Response resp = execServer(client, "RETR", fileName);
		
		requireOK(resp);
		
		File fileInfo = new File(fileName);
		File dest = destName != null
			? new File(destName)
			: new File(System.getProperty("user.dir") + File.separator + fileInfo.getName());
		
		try (Channel data = client.requireDC(Type.BINARY, Structure.FILE, Mode.STREAM)) {
			InputStream stream = data.getSocket().getInputStream();
			
    		try (FileOutputStream out = new FileOutputStream(dest)) {
    			byte[] buffer = new byte[BUFFER_SIZE];
    			int read = -1;
    			while ((read = stream.read(buffer)) > 0) {
    				out.write(buffer, 0, read);
    			}
            }
    		
    		return Response.create(resp.getStatusCode(), "Transfer complete");
    	}
	}
}
