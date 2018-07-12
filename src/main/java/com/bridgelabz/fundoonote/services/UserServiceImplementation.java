package com.bridgelabz.fundoonote.services;

import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
		Optional<User> user1 = userRepository.findByEmailId(registerDTO.getEmailId());
		if (user1.isPresent()) {
		throw new RegisterException("User could not register exception");
		}
		if (Utility.isValidateAllFields(registerDTO)) {	
				User user = new User();
				user.setId(registerDTO.getId());
				user.setFirstName(registerDTO.getFirstName());
				user.setLastName(registerDTO.getLastName());
				user.setUserName(registerDTO.getUserName());
				user.setEmailId(registerDTO.getEmailId());
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				user.setPassword(encoder.encode(registerDTO.getPassword()));
				user.setMobileNumber(registerDTO.getMobileNumber());
				userRepository.save(user);			
		}
	}

	@Override
	public void loginUser(User user) throws LoginException {
		Optional<User> user1 = userRepository.findByUserName(user.getUserName());
		if (!user1.isPresent()) {
			throw new LoginException("User not found Exception");
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (encoder.matches(user.getPassword(), user1.get().getPassword())) {
			System.out.println("Login Successfully");
		} else {
			throw new LoginException("Incorrect password exception");
		}

	}

}
