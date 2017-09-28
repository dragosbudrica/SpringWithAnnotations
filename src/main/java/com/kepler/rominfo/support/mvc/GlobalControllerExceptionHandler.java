package com.kepler.rominfo.support.mvc;

import com.kepler.rominfo.domain.logic.BusinessMessageResponseEntity;
import com.kepler.rominfo.domain.logic.ErrorResponse;
import com.kepler.rominfo.exception.IncorrectValueException;
import com.kepler.rominfo.exception.MissingInfoException;
import com.kepler.rominfo.exception.RestWebserviceBusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger("com.givaudan");

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ErrorResponse> defaultErrorHandler(Exception e) throws Exception {
        logger.error("Internal error", e);
/*
        if (findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            // Exception already handle by the framework, we let it go
            throw e;
        }*/

        String error = e.getMessage();
        return new ResponseEntity<>(new ErrorResponse(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

	@ExceptionHandler({MissingInfoException.class, IncorrectValueException.class})
	public BusinessMessageResponseEntity defaultRestWebserviceBusinessExceptionHandler(RestWebserviceBusinessException exc) throws Exception {
    if (exc.getBusinessMessage() != null) {
        logger.warn(exc.getBusinessMessage().getMessage(), exc);
        return new BusinessMessageResponseEntity(exc.getBusinessMessage(), HttpStatus.BAD_REQUEST);
    }

    logger.warn(exc.getMessage(), exc);

    return new BusinessMessageResponseEntity(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
}