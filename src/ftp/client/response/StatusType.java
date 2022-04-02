package ftp.client.response;

import java.util.Arrays;

public enum StatusType {
	POSITIVE_PRELIMINARY(1, true),
	POSITIVE_COMPLETION(2, true),
	POSITIVE_INTERMEDIATE(3, true),
	TRANSIENT_NEGATIVE_COMPLETION(4, false),
	PERMANENT_NEGATIVE_COMPLETION(5, false);

	private final int NUMBER;
	private final boolean IS_OK;
	
	private StatusType(int number, boolean ok) {
		NUMBER = number;
		IS_OK = ok;
	}
	
	public boolean ok() {
		return IS_OK;
	}
	
	public int number() {
		return NUMBER;
	}
	
	public static final StatusType fromStatus(Status status) {
		return fromStatus(status.getStatusCode());
	}
	
	public static final StatusType fromStatus(int status) {
		int code = status / 100;
		return Arrays.stream(values())
			.filter(e -> e.number() == code)
			.findFirst()
			.orElse(null);
	}
}
