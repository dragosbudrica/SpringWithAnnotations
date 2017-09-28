package com.kepler.rominfo.domain.logic;

import java.io.Serializable;

/**
 * File BusinessMessage.java
 * Created on 8/30/2017, 2:55 PM.
 * <p>
 * TODO: Document me
 *
 * @author Adrian Iasinschi (adrian.iasinschi@kepler-rominfo.com)
 */

public class BusinessMessage implements Serializable {
	private String message;
	private String[] params;

	public BusinessMessage(String message) {
		this.message = message;
	}


	public BusinessMessage(String message, String... params) {
		this.message = message;
		this.params = params;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the params
	 */
	public String[] getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(String[] params) {
		this.params = params;
	}

}