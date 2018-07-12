package com.bridgelabz.fundoonote.services;

import javax.security.auth.login.LoginException;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.exceptions.RegisterException;
import com.bridgelabz.fundoonote.model.RegisterDTO;
import com.bridgelabz.fundoonote.model.User;

@Service
public interface UserService {
	/**
	 * Register the user Details in fundoonote application
	 * 
	 * @param registerDTO
	 *            {@link RegisterDTO} registerDTO is reference variable it has
	 *            registered user data
	 * @throws RegisterException
	 */
	void registerUser(RegisterDTO registerDTO) throws RegisterException;

	/**
	 * login the user in application if the user is present
	 * 
	 * @param user
	 *            user is reference variable
	 * @throws LoginException
	 */
	void loginUser(User user) throws LoginException;
}
