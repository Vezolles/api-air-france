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

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;

	@Override
	public User getUser(String username) throws UserNotFoundException {
		
		log.info("get user {}", username);
		
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	@Override
	public User createUser(User user) throws UserCreateException {
		
		log.info("create user {}", user.getUsername());
		
		LocalDateTime now = LocalDateTime.now();
	    LocalDateTime birthdate = user.getBirthdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

	    if (ChronoUnit.YEARS.between(birthdate, now) < 18 || !Constants.FRANCE.equalsIgnoreCase(user.getCountry())) {
	    	throw new UserCreateException("User cannot be created, user must be adult French");
	    }
	    
	    Optional<User> userFound = userRepository.findByUsername(user.getUsername());
	    if (userFound.isEmpty()) {
	    	
	    	try {
    			return userRepository.save(user);
    		
    		} catch (IllegalArgumentException e) {
    			throw new UserCreateException("User cannot be created");
    		}
	    	
	    } else {
	    	throw new UserCreateException("User cannot be created, user already exists");
	    }
	}

	@Override
	public void deleteUser(String username) throws UserDeleteException {
		
		log.info("delete user {}", username);

		User userFound = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserDeleteException("User cannot be deleted, user does not exist"));
		
		try {
			userRepository.delete(userFound);
		
		} catch (IllegalArgumentException e) {
			throw new UserDeleteException("User cannot be deleted");
		}
	}

}
