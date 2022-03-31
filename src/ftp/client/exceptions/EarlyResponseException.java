package ftp.client.exceptions;

import ftp.client.response.Response;

public class EarlyResponseException extends RuntimeException {
	private static final long serialVersionUID = 583096951570310410L;
	
	protected final Response RESPONSE;
	
	public EarlyResponseException(Response response) {
		RESPONSE = response;
	}

	public Response getResponse() {
		return RESPONSE;
	}
}