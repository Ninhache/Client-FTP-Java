package ftp.client.io;

import java.util.Arrays;

public enum ChannelDirection {
	ACTIVE('A'),
	PASSIVE('P');
	
	protected final char PARAMETER_VALUE;
	
	private ChannelDirection(char parameterValue) {
		PARAMETER_VALUE = parameterValue;
	}
	
	@Override
	public String toString() {
		return Character.toString(PARAMETER_VALUE);
	}
	
	public static final ChannelDirection get(char value) {
		return Arrays.stream(values())
			.filter(e -> e.PARAMETER_VALUE == value)
			.findFirst()
			.orElse(null);
	}
	
	public static final ChannelDirection get(String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		
		return get(value.toUpperCase().charAt(0));
	}
}
