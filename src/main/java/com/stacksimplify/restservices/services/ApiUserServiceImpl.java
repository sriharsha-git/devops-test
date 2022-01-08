package com.stacksimplify.restservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacksimplify.restservices.entities.ApiUser;
import com.stacksimplify.restservices.repositories.ApiUserRepository;

@Service
public class ApiUserServiceImpl implements ApiUserService {	
	
	@Autowired
	private ApiUserRepository apiUserRepository;
	
	@Override
	public ApiUser getApiUserByUsername(String username) {
		return apiUserRepository.findByUname(username);
	}

}
