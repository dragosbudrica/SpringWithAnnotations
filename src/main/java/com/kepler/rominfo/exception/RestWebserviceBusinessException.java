package com.kepler.rominfo.exception;

import com.kepler.rominfo.domain.logic.BusinessMessage;

public class RestWebserviceBusinessException extends RuntimeException {
	private BusinessMessage businessMessage;

	public RestWebserviceBusinessException() {
	}

	public RestWebserviceBusinessException(BusinessMessage message) {
		this.businessMessage = message;
	}

	public RestWebserviceBusinessException(String message) {
		this.businessMessage = new BusinessMessage(message);
	}

	public RestWebserviceBusinessException(String message, String... params) {
		this.businessMessage = new BusinessMessage(message, params);
	}

	public RestWebserviceBusinessException(Throwable cause, BusinessMessage message) {
		super(cause);
		this.businessMessage = message;
	}

	public RestWebserviceBusinessException(Throwable cause, String message) {
		super(cause);
		this.businessMessage = new BusinessMessage(message);
	}

	public RestWebserviceBusinessException(Throwable cause, String message, String... params) {
		super(cause);
		this.businessMessage = new BusinessMessage(message, params);
	}

	public RestWebserviceBusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessMessage getBusinessMessage() {
		return businessMessage;
	}

	public void setBusinessMessage(BusinessMessage businessMessage) {
		this.businessMessage = businessMessage;
	}
}