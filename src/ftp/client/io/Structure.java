package ftp.client.io;

public enum Structure {
	FILE("F"),
	RECORD("R"),
	PAGE("P");
	
	protected final String PARAMETER_VALUE;
	
	private Structure(String parameterValue) {
		PARAMETER_VALUE = parameterValue;
	}
	
	@Override
	public String toString() {
		return PARAMETER_VALUE;
	}
}
