package ftp.client.io;

public enum Type {
	ASCII("A"),
	BINARY("I"),
	EBCDIC("E");
	
	protected final String PARAMETER_VALUE;
	
	private Type(String parameterValue) {
		PARAMETER_VALUE = parameterValue;
	}
	
	@Override
	public String toString() {
		return PARAMETER_VALUE;
	}
}
