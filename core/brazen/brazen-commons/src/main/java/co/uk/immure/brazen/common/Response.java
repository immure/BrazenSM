package co.uk.immure.brazen.common;

import java.io.Serializable;

public class Response<T extends Serializable> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -822354973744319758L;
	
	private final T payload;
	private final Throwable error;
	
	public Response(T payload) {
		this.payload = payload;
		this.error = null;
	}
	
	public Response(T payload, Throwable error) {
		this.payload = payload;
		this.error = error;
	}
	
	public Response(Throwable error) {
		this.payload = null;
		this.error = error;
	}

	public boolean hasError() {
		return error != null;
	}
	
	public T getPayload() {
		return payload;
	}
	
	public Throwable getError() {
		return error;
	}

}
