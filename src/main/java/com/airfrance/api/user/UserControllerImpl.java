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

@RestController
public class UserControllerImpl implements UserController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private UserService userService;

	@Override
	@GetMapping(value="${airfrance.api.user.get}")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO getUser(String username) {
		
		User userFound = userService.getUser(username);
		return modelMapper.map(userFound, UserDTO.class);
	}

	@Override
	@PostMapping(value="${airfrance.api.user.create}")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO createUser(UserDTO user) {
		
		User userReceived = modelMapper.map(user, User.class);
		User userCreated = userService.createUser(userReceived);
		return modelMapper.map(userCreated, UserDTO.class);
	}

	@Override
	@DeleteMapping(value="${airfrance.api.user.delete}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(String username) {

		userService.deleteUser(username);
	}

}
