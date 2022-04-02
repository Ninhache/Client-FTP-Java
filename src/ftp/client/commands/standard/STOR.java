package ftp.client.commands.standard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

@FTP({ "stor", "store", "upload", "send", "post", "put", "up", "publish" })
@Name("Upload File")
@Description("Upload a local file to the server")
@Syntax("STOR <local file> [server destination]")
@Note("You can add quotes around file names if they fcontain spaces")
public class STOR extends Command {
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
				return Response.create(500, "Please specify a file to upload");
			}
		}
		
		File file = new File(source);
		
		if (!file.exists()) {
			return Response.create(500, "The file you specified does not exist '" + file.getAbsolutePath() + "'");
		}
		
		if (file.isDirectory()) {
			return Response.create(500, "The file you specified is a directory '" + file.getAbsolutePath() + "'");
		}
		
		if (dest == null) {
			dest = file.getName();
		}
		
		Response resp = execServer(client, "STOR", dest);

		requireOK(resp);
		
		try (Channel data = client.requireDC(Type.BINARY, Structure.FILE, Mode.STREAM)) {
			OutputStream stream = data.getSocket().getOutputStream();
			
    		try (FileInputStream in = new FileInputStream(file)) {
    			byte[] buffer = new byte[BUFFER_SIZE];
    			int read = -1;
    			while ((read = in.read(buffer)) > 0) {
    				stream.write(buffer, 0, read);
    			}
            }
    		
    		client.control.readlns();

    		return Response.create(resp.getStatusCode(), "Uploaded '" + file.getAbsolutePath() + "' as '" + dest + "'");
    	}
	}
}