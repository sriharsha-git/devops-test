package com.stacksimplify.restservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNameNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "User-Controller", description = "create, update, delete services")
@RestController
@Validated
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retrieve all users")
	public CollectionModel<User> getAllUsers() throws UserNotFoundException {
		List<User> allUsers = userService.getAllUsers();
		for (User user : allUsers) {
			Long userid = user.getId();
			Link selfLink = linkTo(UserController.class).slash(userid).withSelfRel();
			user.add(selfLink);

			if (user.getOrders().size() > 0) {
				Link ordersLink = linkTo(methodOn(OrderController.class).getAllOrders(userid)).withRel("all-orders");
				user.add(ordersLink);
			}
		}
		Link link = linkTo(UserController.class).withSelfRel();
		CollectionModel<User> result = CollectionModel.of(allUsers, link);
		return result;
	}

	@PostMapping
	@ApiOperation(value = "Create user")
	public ResponseEntity<Void> createUser(@Valid @RequestBody User user, UriComponentsBuilder builder) {
		try {
			User savedUser = userService.createUser(user);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(builder.path("/users/{id}").buildAndExpand(savedUser.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

		} catch (UserExistsException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}

	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retrieve user by id")
	public EntityModel<User> getUserById(@PathVariable("id") @Min(1) Long id) {
		try {
			Optional<User> userOptional = userService.getUserById(id);
			User user = null;
			if (userOptional.isPresent()) {
				user = userOptional.get();
			}
			EntityModel<User> resource = EntityModel.of(user);
			WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUserById(id));
			resource.add(linkTo.withSelfRel());
			return resource;
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update user by id")
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
		try {
			return userService.updateUserById(id, user);
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}

	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete user by id")
	public void updateUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
	}

	@GetMapping(value = "/byusername/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retrieve user by username")
	public User getUserByUsername(@PathVariable("username") String username) throws UserNameNotFoundException {
		User user = userService.getUserByUsername(username);
		if (user == null) {
			throw new UserNameNotFoundException("User with username '" + username + "' not found");
		} else {
			return user;
		}
	}

}
