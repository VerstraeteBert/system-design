package be.ugent.systemdesign.inpatient_management.application;

import lombok.Getter;

public class Response {
	@Getter
	private Boolean successful;
	@Getter
	private String message;
	
	public Response(Boolean success, String message) {
		this.successful = success;
		this.message = message;
	}
}
