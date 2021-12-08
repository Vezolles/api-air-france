package com.airfrance.api.user.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	@Getter
	@Setter
	@NotBlank(message = "{user.username.mandatory}")
	@Size(max = 100, message = "{user.username.size}")
	private String username;
	
	@Getter
	@Setter
	@NotNull(message = "{user.birthday.mandatory}")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date birthdate;
	
	@Getter
	@Setter
	@NotBlank(message = "{user.country.mandatory}")
	@Size(max = 100, message = "{user.country.size}")
	private String country;
	
	@Getter
	@Setter
	@Size(max = 15, message = "{user.phone.size}")
	private String phone;
	
	@Getter
	@Setter
	@Size(max = 100, message = "{user.gender.size}")
	private String gender;
	
	@Getter
	@Setter
	@Email(message = "{user.email.notvalid}")
	@Size(max = 100, message = "{user.email.size}")
	private String email;

}
