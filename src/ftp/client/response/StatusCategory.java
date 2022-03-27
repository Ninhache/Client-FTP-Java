package ftp.client.response;

import java.util.Arrays;

public enum StatusCategory {
	SYNTAX_ERROR(0),
	INFORMATION(1),
	CONNEXIONS(2),
	AUTHENTICATION(3),
	UNSPECIFIED(4),
	FILE_SYSTEM(5);

	private final int NUMBER;
	
	private StatusCategory(int number) {
		NUMBER = number;
	}
	
	public int number() {
		return NUMBER;
	}
	
	public static final StatusCategory fromStatus(Status status) {
		return fromStatus(status.getStatusCode());
	}
	
	public static final StatusCategory fromStatus(int status) {
		int code = (status / 10) % 10;
		return Arrays.stream(values())
			.filter(e -> e.number() == code)
			.findFirst()
			.orElse(null);
	}
}
