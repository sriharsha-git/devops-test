package com.stacksimplify.restservices.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stacksimplify.restservices.entities.ApiUser;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private ApiUserServiceImpl apiUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApiUser apiUser = apiUserService.getApiUserByUsername(username);
		return new User(apiUser.getUname(), apiUser.getPword(), new ArrayList<>());
	}

}
