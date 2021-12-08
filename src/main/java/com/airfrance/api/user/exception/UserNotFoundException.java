package com.airfrance.api.user.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5177479758077051072L;
	
	public UserNotFoundException(String message) {
        super(message);
    }

}
