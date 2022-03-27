package ftp.client.response;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Code de status d'une requête vers le serveur FTP
 */
public class Status {
	public static final String STATUS_REGEX = "(?<status>\\d{3}+)([ -](?<message>.*))";
	public static final Pattern STATUS_PATTERN = Pattern.compile(STATUS_REGEX);
	
	protected final int STATUS;
	protected final String MESSAGE;
	
	protected Status(int status, String message) {
		STATUS = status;
		MESSAGE = message;
	}

	public int getStatusCode() {
		return STATUS;
	}
	
	public String getMessage() {
		return MESSAGE;
	}
	
	public static Status parse(String value) {
		Matcher matcher = STATUS_PATTERN.matcher(value);
		
		if (!matcher.matches()) {
			return null;
		}
		
		int code = Integer.parseInt(matcher.group("status"));
		String message = matcher.group("message");
		
		if (message == null) {
			message = "";
		}
		
		return new Status(code, message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(STATUS);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Status other = (Status) obj;
		return STATUS == other.STATUS;
	}
}
