package com.kepler.rominfo.exception;
/**
 * File MissingInfoException.java
 * Created on 8/29/2017, 5:23 PM.
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TODO: Document me
 *
 * @author Adrian Iasinschi (adrian.iasinschi@kepler-rominfo.com)
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MissingInfoException extends RestWebserviceBusinessException {
	public MissingInfoException() {	}
	public MissingInfoException(String message) {
		super(message);
	}

	public MissingInfoException(Throwable cause) {
		super(cause);
	}


}

