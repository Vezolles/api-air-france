package com.airfrance.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used if user creation fails
 * @author Vezolles
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserCreateException extends RuntimeException {

	private static final long serialVersionUID = 4963566993769703058L;
	
	/**
	 * UserCreateException default constructor 
	 * @param message exception's message
	 */
	public UserCreateException(String message) {
        super(message);
    }

}
