package com.airfrance.api.user.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity User for persistence
 * @author Vezolles
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User implements Serializable {

	private static final long serialVersionUID = 2934035488529223181L;

	/**
	 * Attribute id with getter and setter
	 * Auto-increment value
	 */
	@Getter
	@Setter
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	/**
	 * Attribute username with getter and setter
	 */
	@Getter
	@Setter
	@Column
	private String username;
	
	/**
	 * Attribute birthdate with getter and setter
	 * Format timestamp
	 */
	@Getter
	@Setter
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date birthdate;
	
	/**
	 * Attribute country with getter and setter
	 */
	@Getter
	@Setter
	@Column
	private String country;
	
	/**
	 * Attribute phone with getter and setter
	 */
	@Getter
	@Setter
	@Column
	private String phone;
	
	/**
	 * Attribute gender with getter and setter
	 */
	@Getter
	@Setter
	@Column
	private String gender;
	
	/**
	 * Attribute email with getter and setter
	 */
	@Getter
	@Setter
	@Column
	private String email;

}
