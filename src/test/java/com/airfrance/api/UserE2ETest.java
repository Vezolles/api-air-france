package com.airfrance.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.airfrance.api.user.UserRepository;
import com.airfrance.api.user.dto.UserDTO;
import com.airfrance.api.user.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserE2ETest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	private Date date = Date.from(LocalDate.of(1990, 1, 8).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
	
	@BeforeEach
	void setUp() {
		User user = new User();
		user.setUsername("test");
		user.setBirthdate(date);
		user.setCountry("France");
		user.setPhone("0612345678");
		user.setGender("man");
		user.setEmail("test@test.com");
		userRepository.save(user);
	}
	
	@AfterEach
	void tearDown() {
		Optional<User> user = userRepository.findByUsername("test");
		if (user.isPresent()) {
			userRepository.delete(user.get());
		}
	}

    @Test
    void testGetUser() throws Exception {

    	MvcResult result = mockMvc.perform(get("/user/test"))
        	.andExpect(status().isOk())
        	.andReturn();
    	
    	UserDTO userDTOReceived = objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);
    	
    	assertNotNull(userDTOReceived);
    	assertEquals("test", userDTOReceived.getUsername());
    	assertEquals(date, userDTOReceived.getBirthdate());
    	assertEquals("France", userDTOReceived.getCountry());
    	assertEquals("0612345678", userDTOReceived.getPhone());
    	assertEquals("man", userDTOReceived.getGender());
    	assertEquals("test@test.com", userDTOReceived.getEmail());
    }
    
    @Test
    void testGetUserBlank() throws Exception {

    	mockMvc.perform(get("/user/"))
        	.andExpect(status().isMethodNotAllowed());
    }
    
    @Test
    void testGetUserNotExist() throws Exception {

    	mockMvc.perform(get("/user/testnotexist"))
        	.andExpect(status().isInternalServerError());
    }
    
    @Test
    void testCreateUser() throws Exception {
    	
    	UserDTO user = new UserDTO("testcreation", date, "France", "0612345678", "man", "test@test.com");

    	MvcResult result = mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isCreated())
    		.andReturn();
    	
    	UserDTO userDTOReceived = objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);
    	
    	assertNotNull(userDTOReceived);
    	assertEquals("testcreation", userDTOReceived.getUsername());
    	assertEquals(date, userDTOReceived.getBirthdate());
    	assertEquals("France", userDTOReceived.getCountry());
    	assertEquals("0612345678", userDTOReceived.getPhone());
    	assertEquals("man", userDTOReceived.getGender());
    	assertEquals("test@test.com", userDTOReceived.getEmail());
    }
    
    @Test
    void testCreateUserBlank() throws Exception {
    	
    	mockMvc.perform(post("/user")
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateUserExist() throws Exception {
    	
    	UserDTO user = new UserDTO("test", date, "France", "0612345678", "man", "test@test.com");

    	mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isInternalServerError());
    }
    
    @Test
    void testCreateUserNotAdult() throws Exception {
    	
    	Date dateNotAdult = Date.from(LocalDate.of(2020, 1, 8).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    	UserDTO user = new UserDTO("test", dateNotAdult, "France", "0612345678", "man", "test@test.com");

    	mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isInternalServerError());
    }
    
    @Test
    void testCreateUserNotFrench() throws Exception {
    	
    	UserDTO user = new UserDTO("test", date, "Spain", "0612345678", "man", "test@test.com");

    	mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isInternalServerError());
    }
    
    @Test
    void testCreateUserUsernameMissing() throws Exception {
    	
    	UserDTO user = new UserDTO(null, date, "France", "0612345678", "man", "test@test.com");

    	mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateUserUsernameTooLong() throws Exception {
    	
    	UserDTO user = new UserDTO("testtoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooolong", date, "France", "0612345678", "man", "test@test.com");

    	mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateUserBirthdateMissing() throws Exception {
    	
    	UserDTO user = new UserDTO("testcreation", null, "France", "0612345678", "man", "test@test.com");

    	mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateUserBirthdateWrongFormat() throws Exception {
    	
    	String json = "{\"username\":\"testcreation\",\"birthdate\":\"1990-01\",\"country\":\"France\",\"phone\":\"0612345678\",\"gender\":\"man\",\"email\":\"test@test.com\"}";

    	mockMvc.perform(post("/user")
	    		.content(json)
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateUserCountryMissing() throws Exception {
    	
    	UserDTO user = new UserDTO("testcreation", date, null, "0612345678", "man", "test@test.com");

    	mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateUserCountryTooLong() throws Exception {
    	
    	UserDTO user = new UserDTO("testcreation", date, "Francetoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooolong", "0612345678", "man", "test@test.com");

    	mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateUserPhoneTooLong() throws Exception {
    	
    	UserDTO user = new UserDTO("testcreation", date, "France", "0612345678toooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooolong", "man", "test@test.com");

    	mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateUserGenderTooLong() throws Exception {
    	
    	UserDTO user = new UserDTO("testcreation", date, "France", "0612345678", "gendertoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooolong", "test@test.com");

    	mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateUserMailTooLong() throws Exception {
    	
    	UserDTO user = new UserDTO("testcreation", date, "France", "0612345678", "man", "testtoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooolong@test.com");

    	mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateUserMailWrongFormat() throws Exception {
    	
    	UserDTO user = new UserDTO("testcreation", date, "France", "0612345678", "man", "test.com");

    	mockMvc.perform(post("/user")
	    		.content(objectMapper.writeValueAsString(user))
	    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isBadRequest());
    }
    
    @Test
    void testDeleteUser() throws Exception {

    	mockMvc.perform(delete("/user/test"))
        	.andExpect(status().isNoContent());
    }
    
    @Test
    void testDeleteUserBlank() throws Exception {

    	mockMvc.perform(delete("/user/"))
        	.andExpect(status().isMethodNotAllowed());
    }
    
    @Test
    void testDeleteUserNotExist() throws Exception {

    	mockMvc.perform(delete("/user/testnotexist"))
        	.andExpect(status().isInternalServerError());
    }

}
