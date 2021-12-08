package com.airfrance.api.user;

import com.airfrance.api.user.exception.UserCreateException;
import com.airfrance.api.user.exception.UserDeleteException;
import com.airfrance.api.user.exception.UserNotFoundException;
import com.airfrance.api.user.model.User;

public interface UserService {
	
	User getUser(String username) throws UserNotFoundException;
    
	User createUser(User user) throws UserCreateException;
    
    void deleteUser(String username) throws UserDeleteException;

}
