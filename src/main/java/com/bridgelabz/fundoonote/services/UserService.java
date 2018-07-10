package com.bridgelabz.fundoonote.services;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.model.RegisterDTO;
import com.bridgelabz.fundoonote.model.User;
@Service
public interface UserService {
void registerUser(User user);
User loginUser(User user);
}

