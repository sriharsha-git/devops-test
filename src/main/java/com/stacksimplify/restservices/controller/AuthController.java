package com.stacksimplify.restservices.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entities.AuthenticationRequest;
import com.stacksimplify.restservices.entities.AuthenticationResponse;
import com.stacksimplify.restservices.exceptions.InvalidCredentialsException;
import com.stacksimplify.restservices.services.CustomUserDetailsService;
import com.stacksimplify.restservices.util.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags = "Auth-Controller", description = "authentication service")
@RestController
@RequestMapping("/users")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/authenticate")
	@ApiOperation(value = "Get Token")
	public ResponseEntity<AuthenticationResponse> authenticateUser(@Valid @RequestBody AuthenticationRequest authenticationRequest)
			throws InvalidCredentialsException {

		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
					authenticationRequest.getPassCode()));
		} catch (BadCredentialsException ex) {
			throw new InvalidCredentialsException("Incorrect username or passowrd");
		}
		
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUserName());

		return ResponseEntity.ok(new AuthenticationResponse(jwtUtil.generateToken(userDetails)));

	}

}
