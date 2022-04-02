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
import ftp.client.annotations.Note;
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
@Note("You can add quotes around file names if they fcontain spaces")
public class RETR extends Command {
	public static final int BUFFER_SIZE = 2048;
	
	@Override
	protected String getParamsExpression() {
		return "(\\\"(?<source1>[a-zA-Z0-9\\\\\\/\\._+-=]+)\\\"( \\\"(?<dest1>[a-zA-Z0-9\\\\\\/\\._+-=]+)\\\")?|(?<source2>[a-zA-Z0-9\\\\\\/\\._+-=]+)( (?<dest2>[a-zA-Z0-9\\\\\\/\\._+-=]+))?)";
	}

	@Override
	public Response run(Client client, Matcher params) throws IOException {
		String source = params.group("source1");
		String dest = params.group("dest1");;
		if (source == null) {
			source = params.group("source2");
			dest = params.group("dest2");
			if (source == null) {
				return Response.create(500, "Please specify a file to retrieve");
			}
		}
		
		File sourceFile = new File(source);
		File destFile = dest != null
			? new File(dest)
			: new File(System.getProperty("user.dir") + File.separator + sourceFile.getName());
		
		if (destFile.isDirectory()) {
			destFile = new File(destFile.getAbsolutePath() + File.separator + sourceFile.getName());
		}
		
		Response resp = execServer(client, "RETR", source);
		
		requireOK(resp);
		
		try (Channel data = client.requireDC(Type.BINARY, Structure.FILE, Mode.STREAM)) {
			InputStream stream = data.getSocket().getInputStream();
			
    		try (FileOutputStream out = new FileOutputStream(destFile)) {
    			byte[] buffer = new byte[BUFFER_SIZE];
    			int read = -1;
    			while ((read = stream.read(buffer)) > 0) {
    				out.write(buffer, 0, read);
    			}
            }
    		
    		client.control.readlns();

    		return Response.create(resp.getStatusCode(), "Saved '" + source + "' as '" + destFile.getAbsolutePath() + "'");
    	}
	}
}
