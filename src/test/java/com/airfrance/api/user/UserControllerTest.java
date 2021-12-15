package com.airfrance.api.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
	
	private Date date = Date.from(LocalDate.of(2002, 1, 8).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
	private User user = new User(1L, "test", date, "France", "0612345678", "man", "test@test.com");
	private User user2 = new User(2L, "test2", date, "France", "0612345678", "man", "test@test.com");
	private User user3 = new User(3L, "test3", date, "France", "0612345678", "man", "test@test.com");
	private LocalDate dateDTO = LocalDate.of(2002, 1, 8);
	private UserDTO userDTO = new UserDTO("test", dateDTO, "France", "0612345678", "man", "test@test.com");
	private UserDTO userDTO2 = new UserDTO("test2", dateDTO, "France", "0612345678", "man", "test@test.com");
	private UserDTO userDTO3 = new UserDTO("test3", dateDTO, "France", "0612345678", "man", "test@test.com");
	
	@Test
    void testGetAllUser() throws Exception {
		
		List<User> users = List.of(user, user2, user3);
		List<UserDTO> usersDTO = List.of(userDTO, userDTO2, userDTO3);
		
		when(userService.getUsers(1, 3)).thenReturn(users);
		when(modelMapper.map(users, new TypeToken<List<UserDTO>>(){}.getType())).thenReturn(usersDTO);
		
		List<UserDTO> result = userController.getUsers(1, 3);
		
		verify(userService).getUsers(1, 3);
		assertNotNull(result);
		assertThat(result.size(), is(3));
		assertThat(result, contains(userDTO, userDTO2, userDTO3));
    }
	
	@Test
    void testGetUser() throws Exception {
		
		when(userService.getUser("test")).thenReturn(user);
		when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);
		
		UserDTO result = userController.getUser("test");
		
		verify(userService).getUser("test");
		assertNotNull(result);
		assertEquals("test", result.getUsername());
		assertEquals(dateDTO, result.getBirthdate());
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
		assertEquals(dateDTO, result.getBirthdate());
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
