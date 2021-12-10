package com.airfrance.api.user.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for UserNotFoundException
 * @author Vezolles
 */
@SpringBootTest
class UserNotFoundExceptionTest {
	
	@Test
    void testUserNotFoundException() throws Exception {
		
		UserNotFoundException userNotFoundException = new UserNotFoundException("Error user not found");
		
		assertNotNull(userNotFoundException);
		assertEquals("Error user not found", userNotFoundException.getMessage());
    }

}
