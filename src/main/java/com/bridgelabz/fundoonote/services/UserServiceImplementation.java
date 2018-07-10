package com.bridgelabz.fundoonote.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.repository.UserRepository;


@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	
	UserRepository userRepository;
	//private User user=new User();
	@Override
	public void registerUser(User user) { 		
		Optional<User> user1=userRepository.findByUserName(user.getUserName());
		if (!user1.isPresent()) {
			userRepository. save(user);	
			}
		}
	@Override
	public User loginUser(User user ) {
		Optional<User> user1=userRepository.findByUserName(user.getUserName());
		if(user1.isPresent()) {
			if(user1.get().getPassword().equals(user.getPassword())) {
				System.out.println("Login Successfully");
				return user;
			}
			else {
				System.out.println("user not existed register again");
				return null;
			}
		}
		return null;
	}

}
