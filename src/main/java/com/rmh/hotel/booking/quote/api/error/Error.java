package com.rmh.hotel.booking.quote.api.error;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * @author Reginaldo Machado
 * @since 2019.06.09
 *
 */

public class Error {
	 
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;
 
    public Error(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
 
    public Error(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getErrors() {
		return errors;
	}

}