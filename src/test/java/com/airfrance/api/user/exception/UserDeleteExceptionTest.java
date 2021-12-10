package com.airfrance.api.user.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for UserDeleteException
 * @author Vezolles
 */
@SpringBootTest
class UserDeleteExceptionTest {
	
	@Test
    void testUserDeleteException() throws Exception {
		
		UserDeleteException userDeleteException = new UserDeleteException("Error user deletion");
		
		assertNotNull(userDeleteException);
		assertEquals("Error user deletion", userDeleteException.getMessage());
    }

}
