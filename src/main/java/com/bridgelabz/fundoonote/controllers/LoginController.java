package com.bridgelabz.fundoonote.controllers;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.services.UserService;

@RestController
@RequestMapping("/user")
public class LoginController {
	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user)
			throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
		logger.info("Creating User : {}", user);
		userService.registerUser(user);
		return new ResponseEntity<>("Registered successfully", HttpStatus.CREATED);
	}
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user){
		logger.info("login  User : {}", user);
		User user1=userService.loginUser(user);
		if (user1 == null) {
			logger.error("User with userName {} not found.", user.getUserName());
			return new ResponseEntity<>("User with User name " + user.getUserName() + " not found",
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Welcome "+user.getUserName(), HttpStatus.OK);		
	}

}