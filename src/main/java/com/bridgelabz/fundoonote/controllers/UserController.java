package com.bridgelabz.fundoonote.controllers;


import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonote.configuaration.SecurityConfig;
import com.bridgelabz.fundoonote.exceptions.RegisterException;
import com.bridgelabz.fundoonote.model.RegisterDTO;
import com.bridgelabz.fundoonote.model.ResponseDTO;
import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private SecurityConfig securityConfig;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) throws RegisterException {
		logger.info("Creating User : {}", registerDTO);

		userService.registerUser(registerDTO);
		ResponseDTO response=new ResponseDTO();
		response.setMessage("Registered successfully");
		response.setStatus(2);
		return new ResponseEntity<>("Registered successfully", HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user) throws LoginException {
		logger.info("login  User : {}", user);

		String token = securityConfig.createToken(user);
		securityConfig.parseJwt(token);
		System.out.println("Token: " + token);
		
		userService.loginUser(user);
		ResponseDTO response=new ResponseDTO();
		response.setMessage("Login successfully");
		response.setStatus(3);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}