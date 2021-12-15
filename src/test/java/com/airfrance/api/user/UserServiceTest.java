package com.airfrance.api.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.airfrance.api.user.exception.UserCreateException;
import com.airfrance.api.user.exception.UserDeleteException;
import com.airfrance.api.user.exception.UserNotFoundException;
import com.airfrance.api.user.model.User;

/**
 * Unit tests for UserService
 * @author Vezolles
 */
@SpringBootTest
class UserServiceTest {
	
	@Mock
	private Clock clock;
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
    private UserService userService = new UserServiceImpl();
	
	private Date date = Date.from(LocalDate.of(2002, 1, 8).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
	private User user = new User(1L, "test", date, "France", "0612345678", "man", "test@test.com");
	private User user2 = new User(2L, "test2", date, "France", "0612345678", "man", "test@test.com");
	private User user3 = new User(3L, "test3", date, "France", "0612345678", "man", "test@test.com");
		
	@BeforeEach
	void setUp() {
		
		Clock fixedClock = Clock.fixed(LocalDate.of(2020, 1, 8).atStartOfDay(ZoneOffset.UTC).toInstant(), ZoneOffset.UTC);
	    doReturn(fixedClock.instant()).when(clock).instant();
	    doReturn(fixedClock.getZone()).when(clock).getZone();
		when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
	}
	
	@Test
    void testGetAllUser() throws Exception {
		
		Page<User> users = new PageImpl<>(List.of(user, user2, user3));
		when(userRepository.findAll(PageRequest.of(1, 3))).thenReturn(users);
		
		List<User> result = userService.getUsers(1, 3);
		
		verify(userRepository).findAll(PageRequest.of(1, 3));
		assertNotNull(result);
		assertThat(result.size(), is(3));
		assertThat(result, contains(user, user2, user3));
    }
	
	@Test
    void testGetAllUserNoPage() throws Exception {
		
		Page<User> users = new PageImpl<>(List.of(user, user2, user3));
		when(userRepository.findAll(PageRequest.of(0, 3))).thenReturn(users);
		
		List<User> result = userService.getUsers(null, 3);
		
		verify(userRepository).findAll(PageRequest.of(0, 3));
		assertNotNull(result);
		assertThat(result.size(), is(3));
		assertThat(result, contains(user, user2, user3));
    }
	
	@Test
    void testGetAllUserNoPageNoSize() throws Exception {
		
		when(userRepository.findAll()).thenReturn(List.of(user, user2, user3));
		
		List<User> result = userService.getUsers(null, null);
		
		verify(userRepository).findAll();
		assertNotNull(result);
		assertThat(result.size(), is(3));
		assertThat(result, contains(user, user2, user3));
    }
	
	@Test
    void testGetUser() throws Exception {
		
		User result = userService.getUser("test");
		
		assertNotNull(result);
		assertEquals(1L, result.getId().longValue());
		assertEquals("test", result.getUsername());
		assertEquals(date, result.getBirthdate());
		assertEquals("France", result.getCountry());
		assertEquals("0612345678", result.getPhone());
		assertEquals("man", result.getGender());
		assertEquals("test@test.com", result.getEmail());
    }
	
	@Test
    void testGetUserNotFound() throws Exception {
		
		try {
			userService.getUser("testnotfound");
			fail();
		
		} catch (UserNotFoundException e) {
			assertEquals("User not found", e.getMessage());
		}
    }
	
	@Test
    void testCreateUser() throws Exception {
		
		User userCreation = new User(2L, "testcreation", date, "France", "0612345678", "man", "test@test.com");
		
		when(userRepository.save(userCreation)).thenReturn(userCreation);
		
		User result = userService.createUser(userCreation);
		
		verify(userRepository).save(userCreation);
		assertNotNull(result);
		assertEquals(2L, result.getId().longValue());
		assertEquals("testcreation", result.getUsername());
		assertEquals(date, result.getBirthdate());
		assertEquals("France", result.getCountry());
		assertEquals("0612345678", result.getPhone());
		assertEquals("man", result.getGender());
		assertEquals("test@test.com", result.getEmail());
    }
	
	@Test
    void testCreateUserExist() throws Exception {
		
		try {
			userService.createUser(user);
			fail();
			
		} catch (UserCreateException e) {
			assertEquals("User cannot be created, user already exists", e.getMessage());
		}
    }
	
	@Test
    void testCreateUserIllegalArgument() throws Exception {
		
		User userIllegalArgument = new User(3L, "testillegalargument", date, "France", "0612345678", "man", "test@test.com");
		
		doThrow(new IllegalArgumentException()).when(userRepository).save(userIllegalArgument);
		
		try {
			userService.createUser(userIllegalArgument);
			fail();
		
		} catch (UserCreateException e) {
			assertEquals("User cannot be created", e.getMessage());
		}
    }
	
	@Test
    void testCreateUserNotAdult() throws Exception {
		
		Date dateNotAdult = Date.from(LocalDate.of(2002, 1, 9).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
		User userNotAdult = new User(4L, "testnotadult", dateNotAdult, "France", "0612345678", "man", "test@test.com");
		
		try {
			userService.createUser(userNotAdult);
			fail();
			
		} catch (UserCreateException e) {
			assertEquals("User cannot be created, user must be adult French", e.getMessage());
		}
    }
	
	@Test
    void testCreateUserNotFrench() throws Exception {
		
		Date dateNotFrench = Date.from(LocalDate.of(2002, 1, 8).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
		User userNotFrench = new User(5L, "testnotadult", dateNotFrench, "Spain", "0612345678", "man", "test@test.com");
		
		try {
			userService.createUser(userNotFrench);
			fail();
			
		} catch (UserCreateException e) {
			assertEquals("User cannot be created, user must be adult French", e.getMessage());
		}
    }
	
	@Test
    void testDeleteUser() throws Exception {
		
		userService.deleteUser("test");
		verify(userRepository).delete(user);
    }
	
	@Test
    void testDeleteUserNotFound() throws Exception {
		
		try {
			userService.deleteUser("testnotfound");
			fail();
		
		} catch (UserDeleteException e) {
			assertEquals("User cannot be deleted, user does not exist", e.getMessage());
		}
    }
	
	@Test
    void testDeleteUserIllegalArgument() throws Exception {
		
		User userIllegalArgument = new User(1L, "testillegalargument", date, "France", "0612345678", "man", "test@test.com");
		
		when(userRepository.findByUsername("testillegalargument")).thenReturn(Optional.of(userIllegalArgument));
		doThrow(new IllegalArgumentException()).when(userRepository).delete(userIllegalArgument);
		
		try {
			userService.deleteUser("testillegalargument");
			fail();
		
		} catch (UserDeleteException e) {
			assertEquals("User cannot be deleted", e.getMessage());
		}
    }

}
