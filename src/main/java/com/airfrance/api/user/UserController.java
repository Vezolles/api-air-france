package com.airfrance.api.user;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.airfrance.api.user.dto.UserDTO;

@Validated
public interface UserController {
	
	@ResponseBody UserDTO getUser(@PathVariable @Valid @Size(max = 100, message = "{user.username.size}") String username);
    
	@ResponseBody UserDTO createUser(@RequestBody @Valid UserDTO user);
    
    void deleteUser(@PathVariable @Valid @Size(max = 100, message = "{user.username.size}") String username);

}
