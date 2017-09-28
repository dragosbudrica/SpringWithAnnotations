package com.kepler.rominfo.domain.logic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class BusinessMessageResponseEntity extends ResponseEntity<BusinessMessage> {
	public BusinessMessageResponseEntity(HttpStatus statusCode) {
		super(statusCode);
	}

	public BusinessMessageResponseEntity(BusinessMessage body, HttpStatus statusCode) {
		super(body, statusCode);
	}

	public BusinessMessageResponseEntity(MultiValueMap<String, String> headers, HttpStatus statusCode) {
		super(headers, statusCode);
	}

	public BusinessMessageResponseEntity(BusinessMessage body, MultiValueMap<String, String> headers, HttpStatus statusCode) {
		super(body, headers, statusCode);
	}

	public BusinessMessageResponseEntity(String body, HttpStatus statusCode) {
		super(new BusinessMessage(body), statusCode);
	}

	public BusinessMessageResponseEntity(String body, String params, HttpStatus statusCode) {
		super(new BusinessMessage(body, params), statusCode);
	}

	public BusinessMessageResponseEntity(String body, MultiValueMap<String, String> headers, HttpStatus statusCode) {
		super(new BusinessMessage(body), headers, statusCode);
	}

	public BusinessMessageResponseEntity(String body, String params, MultiValueMap<String, String> headers, HttpStatus statusCode) {
		super(new BusinessMessage(body, params), headers, statusCode);
	}
}
		