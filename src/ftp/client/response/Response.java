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
	
	public StatusType getStatusType() {
		return StatusType.fromStatus(getStatus());
	}

	public StatusCategory getStatusCategory() {
		return StatusCategory.fromStatus(getStatus());
	}
	
	public int getStatusCode() {
		return getStatus().getStatusCode();
	}
	
	public String getStatusMessage() {
		return getStatus().getMessage();
	}
	
	public boolean ok() {
		return getStatus().ok();
	}
	
	public boolean error() {
		return !ok();
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
	
	public static final Response create(int status, String message, String body) {
		return parse(String.format("%s\n%d %s", body, status, message));
	}
	
	public static final Response create(int status, String message) {
		return create(status, message, "");
	}
	
	public static final Response create(int status) {
		return create(status, "");
	}
	
	@Override
	public String toString() {
		return String.format("%d %s", getStatusCode(), getStatusMessage());
	}
}
