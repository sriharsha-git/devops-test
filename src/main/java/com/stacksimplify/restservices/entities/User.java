package com.stacksimplify.restservices.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel("This model is for User")
public class User extends RepresentationModel<User> {

	@JsonIgnore
	private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

	@Id
	@ApiModelProperty("This field is required and is auto generated")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Username cannot be empty . Please provide Username")
	@NotNull(message = "Username cannot be null")
	@Column(name = "USER_NAME", length = 50, unique = true, nullable = false)
	private String username;

	@Size(min = 2, message = "Firstname should be of minimum 2 characters")
	@Column(name = "FIRST_NAME", length = 50, nullable = false)
	@NotNull(message = "firstname cannot be null")
	@NotEmpty(message = "firstname cannot be empty . Please provide firstname")
	private String firstname;

	@Size(min = 2, message = "Lastname should be of minimum 2 characters")
	@Column(name = "LAST_NAME", length = 50, nullable = false)
	@NotNull(message = "lastname cannot be null")
	private String lastname;

	@Email(regexp = ".+@.+\\..+" , message = "In valid email address")
	@NotNull(message = "email cannot be null")
	@Column(name = "EMAIL_ADDRESS", length = 50, nullable = false)
	@NotEmpty(message = "email cannot be empty . Please provide email")
	private String email;

	@Column(name = "ROLE", length = 50, nullable = false)
	@NotNull(message = "role cannot be null")
	private String role;

	@Column(name = "SSN", length = 50, unique = true, nullable = false)
	@NotNull(message = "ssn cannot be null")
	@NotEmpty(message = "ssn cannot be empty . Please provide ssn")
	private String ssn;

	@OneToMany(mappedBy = "user")
	private List<Orders> orders;

	public User(String username, String firstname, String lastname, String email, String role, String ssn) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.role = role;
		this.ssn = ssn;
	}
}
