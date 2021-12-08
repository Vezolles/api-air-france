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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table
public class User implements Serializable {

	private static final long serialVersionUID = 2934035488529223181L;

	@Getter
	@Setter
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Getter
	@Setter
	@Column
	private String username;
	
	@Getter
	@Setter
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date birthdate;
	
	@Getter
	@Setter
	@Column
	private String country;
	
	@Getter
	@Setter
	@Column
	private String phone;
	
	@Getter
	@Setter
	@Column
	private String gender;
	
	@Getter
	@Setter
	@Column
	private String email;

}
