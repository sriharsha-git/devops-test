package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;

public interface UserService {

	public List<User> getAllUsers();

	public User createUser(User user) throws UserExistsException;

	public Optional<User> getUserById(Long id) throws UserNotFoundException;

	public User updateUserById(Long id, User user) throws UserNotFoundException;

	public void deleteUserById(Long id);

	public User getUserByUsername(String username);

}
