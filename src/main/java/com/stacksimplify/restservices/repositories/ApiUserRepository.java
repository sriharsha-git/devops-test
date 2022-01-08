package com.stacksimplify.restservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stacksimplify.restservices.entities.ApiUser;

@Repository
public interface ApiUserRepository extends JpaRepository<ApiUser,Long>{

	ApiUser findByUname(String uname);
}
