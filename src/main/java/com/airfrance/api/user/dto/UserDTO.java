package com.airfrance.api.user.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CLass DTO User for transfer
 * @author Vezolles
 */
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	/**
	 * Attribute username with getter and setter
	 * Username must be not blank and max size = 100
	 */
	@Getter
	@Setter
	@NotBlank(message = "{user.username.mandatory}")
	@Size(max = 100, message = "{user.username.size}")
	private String username;
	
	/**
	 * Attribute birthdate with getter and setter
	 * Birthdate must be not null and format = yyyy-MM-dd
	 */
	@Getter
	@Setter
	@NotNull(message = "{user.birthday.mandatory}")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private LocalDate birthdate;
	
	/**
	 * Attribute country with getter and setter
	 * Country must be not blank and max size = 100
	 */
	@Getter
	@Setter
	@NotBlank(message = "{user.country.mandatory}")
	@Size(max = 100, message = "{user.country.size}")
	private String country;
	
	/**
	 * Attribute phone with getter and setter
	 * Phone must be max size = 15
	 */
	@Getter
	@Setter
	@Size(max = 15, message = "{user.phone.size}")
	private String phone;
	
	/**
	 * Attribute gender with getter and setter
	 * Gender must be max size = 100
	 */
	@Getter
	@Setter
	@Size(max = 100, message = "{user.gender.size}")
	private String gender;
	
	/**
	 * Attribute email with getter and setter
	 * Email must be valid format and max size = 100
	 */
	@Getter
	@Setter
	@Email(message = "{user.email.notvalid}")
	@Size(max = 100, message = "{user.email.size}")
	private String email;

}
