package com.kepler.rominfo.exception;
/**
 * File IncorrectValueException.java
 * Created on 8/30/2017, 11:01 AM.
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TODO: Document me
 *
 * @author Adrian Iasinschi (adrian.iasinschi@kepler-rominfo.com)
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectValueException extends RestWebserviceBusinessException {

	public IncorrectValueException() { }

	public IncorrectValueException(String message) {
		super(message);
	}

	public IncorrectValueException(Throwable cause) {
		super(cause);
	}

}
