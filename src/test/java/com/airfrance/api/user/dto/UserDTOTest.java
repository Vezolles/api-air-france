package com.airfrance.api.user.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for UserDTO
 * @author Vezolles
 */
@SpringBootTest
class UserDTOTest {
	
	@Test
    void testUserDTO() throws Exception {
		
		Date date = Date.from(LocalDate.of(1990, 1, 8).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

		UserDTO user1 = new UserDTO();
		user1.setUsername("test");
		user1.setBirthdate(date);
		user1.setCountry("France");
		user1.setPhone("0612345678");
		user1.setGender("man");
		user1.setEmail("test@test.com");
		
		assertEquals("test", user1.getUsername());
		assertEquals(date, user1.getBirthdate());
		assertEquals("France", user1.getCountry());
		assertEquals("0612345678", user1.getPhone());
		assertEquals("man", user1.getGender());
		assertEquals("test@test.com", user1.getEmail());
		
		UserDTO user2 = new UserDTO("test", date, "France", "0612345678", "man", "test@test.com");
		
		assertEquals("test", user2.getUsername());
		assertEquals(date, user2.getBirthdate());
		assertEquals("France", user2.getCountry());
		assertEquals("0612345678", user2.getPhone());
		assertEquals("man", user2.getGender());
		assertEquals("test@test.com", user2.getEmail());
    }

}
