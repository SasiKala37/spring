package com.bridgelabz.springrestloginapp.controllers;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springrestloginapp.model.User;
import com.bridgelabz.springrestloginapp.services.UserService;
import com.bridgelabz.springrestloginapp.util.CustomErrorType;

@RestController
@RequestMapping("/login")
public class LoginController {
	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/user")
	public ResponseEntity<?> getUser(@RequestBody User user)
			throws SQLException, IOException, PropertyVetoException {
		//userName = "sasi2";
		System.out.println(user.getUserName());
		logger.info("Fetching User with userName {}", user.getUserName());
		User user1 = userService.findUserbyUserName(user.getUserName());
		
		if (user1 == null) {
			logger.error("User with userName {} not found.", user.getUserName());
			return new ResponseEntity(new CustomErrorType("User with User name " +user.getUserName() + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
