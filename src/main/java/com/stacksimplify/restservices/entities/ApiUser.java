package com.stacksimplify.restservices.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "api_users")
@Getter
@Setter
@NoArgsConstructor
public class ApiUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(name = "API_USER_NAME", length = 50, unique = true, nullable = false)
	@NotEmpty(message = "Username cannot be empty . Please provide Username")
	@NotNull(message = "Username cannot be null")
	private String uname;

	@Column(name = "API_USER_PASSWORD", length = 50, unique = true, nullable = false)
	@NotEmpty(message = "Password cannot be empty . Please provide Password")
	@NotNull(message = "Password cannot be null")
	private String pword;

	public ApiUser(String uname, String pword) {
		this.uname = uname;
		this.pword = pword;
	}

}
