package be.ugent.systemdesign.inpatient_management.application;

import lombok.Getter;

public class Response {

	public final String message;
	public final ResponseStatus status;
	
	public Response(ResponseStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
