package com.bridgelabz.fundoonote.user.services;

import java.util.Optional;

import javax.mail.MessagingException;
import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonote.user.exceptions.UserActivationException;
import com.bridgelabz.fundoonote.user.model.LoginDTO;
import com.bridgelabz.fundoonote.user.model.MailDTO;
import com.bridgelabz.fundoonote.user.model.RegistrationDTO;
import com.bridgelabz.fundoonote.user.model.User;
import com.bridgelabz.fundoonote.user.repository.UserRepository;
import com.bridgelabz.fundoonote.user.security.SecurityConfig;
import com.bridgelabz.fundoonote.user.security.UserEmailSecurity;
import com.bridgelabz.fundoonote.user.util.Utility;

import io.jsonwebtoken.Claims;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserEmailSecurity userEmailSecurity;

	@Override
	public void registerUser(RegistrationDTO registerDTO, String uri)
			throws RegistrationException, MessagingException, UserActivationException {

		Optional<User> optionalUser = userRepository.findByEmailId(registerDTO.getEmailId());

		if (optionalUser.isPresent()) {
			throw new RegistrationException("User already registerwith this email id exception");
		}

		Utility.isValidateAllFields(registerDTO);

		User user = new User();

		user.setFirstName(registerDTO.getFirstName());
		user.setLastName(registerDTO.getLastName());
		user.setUserName(registerDTO.getUserName());
		user.setEmailId(registerDTO.getEmailId());
		user.setPassword(encoder.encode(registerDTO.getPassword()));
		user.setMobileNumber(registerDTO.getMobileNumber());

		userRepository.save(user);
		Optional<User> optionalUser1 = userRepository.findByEmailId(registerDTO.getEmailId());
		SecurityConfig securityConfig = new SecurityConfig();
		String token = securityConfig.createToken(optionalUser1.get().getId());

		MailDTO mailDTO = new MailDTO();
		mailDTO.setId(user.getId());
		mailDTO.setToMailAddress(registerDTO.getEmailId());
		mailDTO.setSubject("Hi " + registerDTO.getFirstName());
		mailDTO.setBody("Activate your accout click on this link: http://localhost:8080" + uri + "/" + token);
		mailDTO.setMailSign("\nThank you \n SasiKala G \n Bridge Labz \n Mumbai");

		userEmailSecurity.sendEmail(mailDTO);
		// setActivationStatus(mailDTO.getId(), token);

	}

	@Override
	public void loginUser(LoginDTO loginDTO, String uri)
			throws LoginException, UserActivationException, MessagingException {

		Optional<User> optionalUser = userRepository.findByEmailId(loginDTO.getEmailId());

		if (!optionalUser.isPresent()) {
			throw new LoginException("User not found Exception");
		}

		if (!encoder.matches(loginDTO.getPassword(), optionalUser.get().getPassword())) {
			throw new LoginException("Incorrect password exception");
		}
		SecurityConfig securityConfig = new SecurityConfig();
		String token = securityConfig.createToken(optionalUser.get().getId());

		MailDTO mailDTO = new MailDTO();

		mailDTO.setId(optionalUser.get().getId());
		mailDTO.setToMailAddress(loginDTO.getEmailId());
		mailDTO.setSubject("Hi " + optionalUser.get().getFirstName());
		mailDTO.setBody("Activate your accout click on this link: http://localhost:8080" + uri + "?token=" + token);
		mailDTO.setMailSign("\nThank you \n SasiKala G \n Bridge Labz \n Mumbai");

		userEmailSecurity.sendEmail(mailDTO);

	}

	@Override
	public void setActivationStatus(String token) throws UserActivationException {
		SecurityConfig securityConfig = new SecurityConfig();
		//String jwtToken = securityConfig.createToken();
		Claims claim = securityConfig.parseJwt(token);
System.out.println(claim.getId());
		Optional<User> optionalUser = userRepository.findById(claim.getId());

		if (!optionalUser.isPresent()) {
			throw new UserActivationException("User not found Exception");
		}

		User user = optionalUser.get();
		user.setActivate(true);
		userRepository.save(user);
	}

}
