package com.airfrance.api.user.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for UserCreateException
 * @author Vezolles
 */
@SpringBootTest
class UserCreateExceptionTest {
	
	@Test
    void testUserCreateException() throws Exception {
		
		UserCreateException userCreateException = new UserCreateException("Error user creation");
		
		assertNotNull(userCreateException);
		assertEquals("Error user creation", userCreateException.getMessage());
    }

}
