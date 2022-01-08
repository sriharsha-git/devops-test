package com.stacksimplify.restservices.services;

import com.stacksimplify.restservices.entities.ApiUser;

public interface ApiUserService {

	ApiUser getApiUserByUsername(String username);
}
