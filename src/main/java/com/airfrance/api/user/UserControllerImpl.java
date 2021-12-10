package com.airfrance.api.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.airfrance.api.user.dto.UserDTO;
import com.airfrance.api.user.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * User controller implementation
 * Rest controller implementation for users, initialize and valid apis
 * @author Vezolles
 */
@Tag(name = "User's APIs", description = "APIs for reading, creating and deleting users")
@RestController
public class UserControllerImpl implements UserController {
	
	/**
	 * ModelMapper used to map different model
	 */
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * UserService used to make user's processing
	 */
	@Autowired
    private UserService userService;

	/**
	 * Api to get user's details
	 * @param username the username to get detail
	 * @return user'information and status HTTP 200
	 */
	@Operation(summary = "Get user details by username")
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "User found", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = UserDTO.class)) }),
		@ApiResponse(responseCode = "404", description = "User not found", 
			content = @Content(mediaType = "application/json", 
			schema = @Schema(description = "Error details", example = "User not found"))) })
	@Override
	@GetMapping(value="${airfrance.api.user.get}")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO getUser(String username) {
		
		// Call service to get user
		User userFound = userService.getUser(username);
		
		// Return user as DTO
		return modelMapper.map(userFound, UserDTO.class);
	}

	/**
	 * Api to create user
	 * @param user the user's details to create
	 * @return user'information for user created and status HTTP 201
	 */
	@Operation(summary = "Create new user with details")
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "201", description = "User created", 
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = UserDTO.class)) }),
		@ApiResponse(responseCode = "500", description = "User cannot be created", 
			content = @Content(mediaType = "application/json", 
			schema = @Schema(description = "Error details", example = "User cannot be created, user already exist"))) })
	@Override
	@PostMapping(value="${airfrance.api.user.create}")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO createUser(UserDTO user) {
		
		// Transform DTO to User
		User userReceived = modelMapper.map(user, User.class);
		
		// Call service to create user
		User userCreated = userService.createUser(userReceived);
		
		// Return user as DTO
		return modelMapper.map(userCreated, UserDTO.class);
	}

	/**
	 * Api to delete user, send status HTTP 204
	 * @param username the username to delete
	 */
	@Operation(summary = "Delete existing user")
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "204", description = "User deleted"),
		@ApiResponse(responseCode = "500", description = "User cannot be deleted", 
			content = @Content(mediaType = "application/json", 
			schema = @Schema(description = "Error details", example = "User cannot be deleted, user does not exist"))) })
	@Override
	@DeleteMapping(value="${airfrance.api.user.delete}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(String username) {

		// Call service to delete user
		userService.deleteUser(username);
	}

}
