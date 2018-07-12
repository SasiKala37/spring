package com.bridgelabz.fundoonote.services;

import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.exceptions.RegisterException;
import com.bridgelabz.fundoonote.model.RegisterDTO;
import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.util.Utility;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public void registerUser(RegisterDTO registerDTO) throws RegisterException {
		if (Utility.isValidateAllFields(registerDTO)) {
			Optional<User> user1 = userRepository.findByEmailId(registerDTO.getEmailId());
			if (!user1.isPresent()) {
				User user = new User();
				user.setId(registerDTO.getId());
				user.setFirstName(registerDTO.getFirstName());
				user.setLastName(registerDTO.getLastName());
				user.setUserName(registerDTO.getUserName());
				user.setEmailId(registerDTO.getEmailId());
				user.setPassword(registerDTO.getPassword());
				user.setMobileNumber(registerDTO.getMobileNumber());
				userRepository.save(user);
			}
		}
	}

	@Override
	public void loginUser(User user) throws LoginException {
		Optional<User> user1 = userRepository.findByEmailId(user.getEmailId());
		if (user1.isPresent()) {
			if (user1.get().getPassword().equals(user.getPassword())) {
				System.out.println("Login Successfully");
			} else {
				throw new LoginException("Incorrect password exception");
			}
		}
	}

}
