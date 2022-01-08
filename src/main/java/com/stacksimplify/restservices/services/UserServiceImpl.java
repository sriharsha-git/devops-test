package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User createUser(User user) throws UserExistsException {
		User existingUser = userRepository.findByUsername(user.getUsername());

		if (existingUser != null) {
			throw new UserExistsException("User with user name " + user.getUsername() + " already exists");
		}

		return userRepository.save(user);
	}

	@Override
	public Optional<User> getUserById(Long id) throws UserNotFoundException {
		if (userRepository.findById(id).isPresent()) {
			Optional<User> user = userRepository.findById(id);
			return user;
		} else {
			throw new UserNotFoundException("User with Id " + id + " does not exist");
		}
	}

	@Override
	public User updateUserById(Long id, User user) throws UserNotFoundException {
		if (userRepository.findById(id).isPresent()) {
			user.setId(id);
			return userRepository.save(user);
		} else {
			throw new UserNotFoundException("Not updated, user with Id " + id + " does not exist");
		}
	}

	@Override
	public void deleteUserById(Long id) {
		if (userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with Id " + id + " does not exist");
		}
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
