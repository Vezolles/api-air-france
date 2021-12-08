package com.airfrance.api.user.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserDeleteException extends RuntimeException {

	private static final long serialVersionUID = 2292491813517932883L;
	
	public UserDeleteException(String message) {
        super(message);
    }
    
}
