package ftp.client.response;

import java.util.Collection;

/**
 * Réponse du serveur FTP sur le canal de commandes
 */
public class Response {
	protected final Status STATUS;
	protected final String BODY;
	
	protected Response(Status status, String body) {
		STATUS = status;
		BODY = body;
	}
	
	public Status getStatus() {
		return STATUS;
	}
	
	public int getStatusCode() {
		return getStatus().getStatusCode();
	}
	
	public String getStatusMessage() {
		return getStatus().getMessage();
	}
	
	public String getBody() {
		return BODY;
	}
	
	public static final Response parse(String responseLines) {
		return parse(responseLines.split("\\R"));
	}
	
	public static final Response parse(Collection<String> responseLines) {
		return parse(responseLines.toArray(String[]::new));
	}
	
	public static final Response parse(String[] responseLines) {
		Status status = null;
		StringBuilder sb = new StringBuilder();
		
		for (String line : responseLines) {
			Status tmpStatus = Status.parse(line);
			if (tmpStatus != null) {
				status = tmpStatus;
				continue;
			}
			
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(line);
		}
		
		if (status == null) {
			return null;
		}
		
		return new Response(status, sb.toString());
	}
}
