package com.bridgelabz.springrestloginapp.controllers;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bridgelabz.springrestloginapp.model.User;
import com.bridgelabz.springrestloginapp.services.UserService;
import com.bridgelabz.springrestloginapp.util.CustomErrorType;

@RestController
@RequestMapping("/user")
public class LoginController {
	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/login")
	public ResponseEntity<?> getUser(@RequestBody User user) throws SQLException, IOException, PropertyVetoException {
		System.out.println(user.getUserName());
		logger.info("Fetching User with userName {}", user.getUserName());
		User user1 = userService.findUserbyUserName(user.getUserName());

		if (user1 == null) {
			logger.error("User with userName {} not found.", user.getUserName());
			return new ResponseEntity(new CustomErrorType("User with User name " + user.getUserName() + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(user.getUserName(), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user)
			throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
		logger.info("Creating User : {}", user);
		System.out.println(user.getUserName());
		User user1 = userService.findUserbyUserName(user.getUserName());

		if (user1 != null) {
			logger.error("Unable to create. A User with name {} already exist", user.getUserName());
			return new ResponseEntity(
					new CustomErrorType("Unable to create. A User with name " + user.getUserName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		userService.saveUser(user);

		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.
		 * getId()).toUri());
		 */
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@GetMapping("/list")
	public ResponseEntity<?> listAllUsers() throws SQLException, IOException, PropertyVetoException {
		List<User> users = userService.listOfUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	/*@PutMapping()
	public ResponseEntity<?> updateUser(@PathVariable("userName") String userName, @RequestBody User user){
		logger.info("Updating User with Password {}", userName);
		 
        User currentUser = userService.findUserbyUserName(userName);
 
        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.", userName);
            return new ResponseEntity<>(new CustomErrorType("Unable to upate. User with id " + userName + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentUser.setId(user.getId());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmailId(user.getEmailId());
        currentUser.setUserName(user.getUserName());
        currentUser.setPassword(user.getPassword());
 
        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}*/
}
