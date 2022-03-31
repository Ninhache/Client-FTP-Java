package ftp.client.io;

public enum Mode {
	STREAM("S"),
	BLOCK("B"),
	COMPRESSED("C");
	
	protected final String PARAMETER_VALUE;
	
	private Mode(String parameterValue) {
		PARAMETER_VALUE = parameterValue;
	}
	
	@Override
	public String toString() {
		return PARAMETER_VALUE;
	}
}
