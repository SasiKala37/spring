package com.bridgelabz.fundoonote.services;

import javax.security.auth.login.LoginException;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.exceptions.RegisterException;
import com.bridgelabz.fundoonote.model.RegisterDTO;
import com.bridgelabz.fundoonote.model.User;
@Service
public interface UserService {
void registerUser(RegisterDTO registerDTO) throws RegisterException;
void loginUser(User user) throws LoginException;
}

