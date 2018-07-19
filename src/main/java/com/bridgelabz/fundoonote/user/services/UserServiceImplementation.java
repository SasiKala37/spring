package com.bridgelabz.fundoonote.user.services;

import java.util.Optional;

import javax.mail.MessagingException;
import javax.security.auth.login.LoginException;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonote.user.exceptions.UserActivationException;
import com.bridgelabz.fundoonote.user.model.LoginDTO;
import com.bridgelabz.fundoonote.user.model.MailDTO;
import com.bridgelabz.fundoonote.user.model.RegistrationDTO;
import com.bridgelabz.fundoonote.user.model.ResetPasswordDTO;
import com.bridgelabz.fundoonote.user.model.User;
import com.bridgelabz.fundoonote.user.producer.IProducer;
import com.bridgelabz.fundoonote.user.repository.UserRepository;
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
	private IProducer producer;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public void registerUser(RegistrationDTO registerDTO, String uri)
			throws RegistrationException, MessagingException, UserActivationException {
		
		Utility.isValidateAllFields(registerDTO);

		Optional<User> optionalUser = userRepository.findByEmailId(registerDTO.getEmailId());

		if (optionalUser.isPresent()) {
			throw new RegistrationException("User already registerwith this email id ");
		}
		
		registerDTO.setPassword(encoder.encode(registerDTO.getPassword()));
		
		User user=new User();
		user.setId("1");
		user = modelMapper.map(registerDTO,User.class);

		/*user.setFirstName(registerDTO.getFirstName());
		user.setLastName(registerDTO.getLastName());
		user.setUserName(registerDTO.getUserName());
		user.setEmailId(registerDTO.getEmailId());
		user.setPassword(encoder.encode(registerDTO.getPassword()));
		user.setMobileNumber(registerDTO.getMobileNumber());*/
		
		userRepository.save(user);

		Optional<User> optionalUser1 = userRepository.findByEmailId(registerDTO.getEmailId());
		if(!optionalUser1.isPresent()) {
			throw new RegistrationException("user doesnot save in the database");
		}
		sendEmailMessage(uri, optionalUser1);

	}

	@Override
	public String loginUser(LoginDTO loginDTO)
			throws LoginException, UserActivationException, MessagingException, RegistrationException {
		
		if(!Utility.validateEmailAddress(loginDTO.getEmailId())) {
			throw new LoginException("Email not valid");
		}
		
		if(!Utility.validatePassword(loginDTO.getPassword())) {
			throw new LoginException("Password not valid");
		}
		
		Optional<User> optionalUser = userRepository.findByEmailId(loginDTO.getEmailId());

		if (!optionalUser.isPresent()) {
			throw new LoginException("User not found Exception");
		}

		if (!encoder.matches(loginDTO.getPassword(), optionalUser.get().getPassword())) {
			throw new LoginException("Incorrect password exception");
		}
		
		if(!optionalUser.get().isActivate()) {
			throw new LoginException("User not in activation");
		}
		return  Utility.createToken(optionalUser.get().getId());
		
	}

	@Override
	public void forgotPassword(String emailId, String uri) throws RegistrationException, MessagingException {

		Optional<User> optionalUser = userRepository.findByEmailId(emailId);
		if (!optionalUser.isPresent()) {
			throw new RegistrationException("User doesnot exist Exception");
		}
		sendEmailMessage(uri, optionalUser);
	}

	private void sendEmailMessage(String uri, Optional<User> optionalUser) {
		
		String token = Utility.createToken(optionalUser.get().getId());
		System.out.println();
		MailDTO mailDTO = new MailDTO();

		mailDTO.setToMailAddress(optionalUser.get().getEmailId());
		mailDTO.setSubject(" Verification mail");
		mailDTO.setBody( environment.getProperty("link")+ uri + "?token=" + token);	
		producer.produceMsg(mailDTO);
	}

	@Override
	public void setActivationStatus(String token) throws UserActivationException {

		Claims claim = Utility.parseJwt(token);

		Optional<User> optionalUser = userRepository.findById(claim.getId());

		if (!optionalUser.isPresent()) {
			throw new UserActivationException("User not found Exception");
		}

		User user = optionalUser.get();
		user.setActivate(true);
		userRepository.save(user);
	}

	@Override
	public void resetPassword(ResetPasswordDTO resetPasswordDTO, String token)
			throws UserActivationException, RegistrationException {
		

		if (!Utility.validatePassword(resetPasswordDTO.getNewPassword())) {
			throw new RegistrationException("password not valid Exception");
		}

		if (!Utility.isPasswordMatch(resetPasswordDTO.getNewPassword(), resetPasswordDTO.getConfirmPassword())) {
			throw new RegistrationException("confirm password mismatch with new password Exception");
		}
		Claims claim = Utility.parseJwt(token);
		Optional<User> optionalUser = userRepository.findById(claim.getId());

		if (!optionalUser.isPresent()) {
			throw new RegistrationException("User not redistered, please register");
		}

		User user = optionalUser.get();
		user.setPassword(encoder.encode(resetPasswordDTO.getNewPassword()));
		userRepository.save(user);

	}
}
