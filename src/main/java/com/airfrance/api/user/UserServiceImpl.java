package com.airfrance.api.user;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airfrance.api.user.exception.UserCreateException;
import com.airfrance.api.user.exception.UserDeleteException;
import com.airfrance.api.user.exception.UserNotFoundException;
import com.airfrance.api.user.model.User;
import com.airfrance.api.util.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * User service implementation
 * Service implementation for users, make processing
 * @author Vezolles
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	/**
	 * UserRepository used to persist user
	 */
	@Autowired
    private UserRepository userRepository;

	/**
	 * Service to get user's details
	 * @param username the username to get detail
	 * @return user'information
	 * @throws UserNotFoundException if user doesn't exist
	 */
	@Override
	public User getUser(String username) throws UserNotFoundException {
		
		// Log start method
		log.info("get user {}", username);
		
		// Find user if exist, else throw error user not found
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	/**
	 * Service to create user
	 * @param user the user's details to create
	 * @return user'information for user created
	 * @throws UserCreateException if creation fails
	 */
	@Override
	public User createUser(User user) throws UserCreateException {
		
		// Log start method
		log.info("create user {}", user.getUsername());
		
		// Convert dates to local date time
		LocalDateTime now = LocalDateTime.now();
	    LocalDateTime birthdate = user.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

	    // If user is not adult French, throw error user cannot be created, user must be adult French
	    if (ChronoUnit.YEARS.between(birthdate, now) < 18 || !Constants.FRANCE.equalsIgnoreCase(user.getCountry())) {
	    	throw new UserCreateException("User cannot be created, user must be adult French");
	    }
	    
	    // Find user by username
	    Optional<User> userFound = userRepository.findByUsername(user.getUsername());
	    
	    // If user not exist
	    if (userFound.isEmpty()) {
	    	
	    	try {
	    		// Persist new user
    			return userRepository.save(user);
    		
    		} catch (IllegalArgumentException e) {
    			throw new UserCreateException("User cannot be created");
    		}
	    	
	    } else {
	    	// Throw error user already exists
	    	throw new UserCreateException("User cannot be created, user already exists");
	    }
	}

	/**
	 * Service to delete user
	 * @param username the username to delete
	 * @throws UserDeleteException if deletion fails
	 */
	@Override
	public void deleteUser(String username) throws UserDeleteException {
		
		// Log start method
		log.info("delete user {}", username);

		// Find user if exist, else throw error user does not exist
		User userFound = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserDeleteException("User cannot be deleted, user does not exist"));
		
		try {
			// Delete user
			userRepository.delete(userFound);
		
		} catch (IllegalArgumentException e) {
			throw new UserDeleteException("User cannot be deleted");
		}
	}

}
