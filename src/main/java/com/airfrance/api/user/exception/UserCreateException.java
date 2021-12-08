package com.airfrance.api.user.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserCreateException extends RuntimeException {

	private static final long serialVersionUID = 4963566993769703058L;
	
	public UserCreateException(String message) {
        super(message);
    }

}
