package com.airfrance.api.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.airfrance.api.user.dto.UserDTO;
import com.airfrance.api.user.model.User;

/**
 * Unit tests for UserController
 * @author Vezolles
 */
@SpringBootTest
class UserControllerTest {
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private UserService userService;
	
	@InjectMocks
    private UserController userController = new UserControllerImpl();
	
	private Date date = Date.from(LocalDate.of(1990, 1, 8).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
	private User user = new User(1L, "test", date, "France", "0612345678", "man", "test@test.com");
	private UserDTO userDTO = new UserDTO("test", date, "France", "0612345678", "man", "test@test.com");
	
	@Test
    void testGetUser() throws Exception {
		
		when(userService.getUser("test")).thenReturn(user);
		when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
		
		UserDTO result = userController.getUser("test");
		
		verify(userService).getUser("test");
		assertNotNull(result);
		assertEquals("test", result.getUsername());
		assertEquals(date, result.getBirthdate());
		assertEquals("France", result.getCountry());
		assertEquals("0612345678", result.getPhone());
		assertEquals("man", result.getGender());
		assertEquals("test@test.com", result.getEmail());
    }
	
	@Test
    void testCreateUser() throws Exception {
		
		when(modelMapper.map(userDTO, User.class)).thenReturn(user);
		when(userService.createUser(user)).thenReturn(user);
		when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
		
		UserDTO result = userController.createUser(userDTO);
		
		verify(userService).createUser(user);
		assertNotNull(result);
		assertEquals("test", result.getUsername());
		assertEquals(date, result.getBirthdate());
		assertEquals("France", result.getCountry());
		assertEquals("0612345678", result.getPhone());
		assertEquals("man", result.getGender());
		assertEquals("test@test.com", result.getEmail());
    }
	
	@Test
    void testDeleteUser() throws Exception {
		
		userController.deleteUser("test");
		
		verify(userService).deleteUser("test");
    }

}
