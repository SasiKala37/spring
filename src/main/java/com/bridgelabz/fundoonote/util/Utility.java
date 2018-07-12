package com.bridgelabz.fundoonote.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bridgelabz.fundoonote.exceptions.RegisterException;
import com.bridgelabz.fundoonote.model.RegisterDTO;

public class Utility {

	public static boolean isValidateAllFields(RegisterDTO registerDTO) throws RegisterException {
		System.out.println("util");
		if (!validateEmailAddress(registerDTO.getEmailId())) {
			throw new RegisterException("emailid not valid  Exception");
		} else if (!isValidUserName(registerDTO.getUserName())) {
			throw new RegisterException("UserName Not valid  Exception");
		} else if (!validatePassword(registerDTO.getPassword())) {
			throw new RegisterException("password not valid Exception");
		} else if (!isValidMobileNumber(registerDTO.getMobileNumber())) {
			throw new RegisterException("mobilenumber not valid  Exception");
		} else if (!isPasswordMatch(registerDTO.getPassword(), registerDTO.getConfirmPassword())) {
			throw new RegisterException("password mismatch exception");
		} else {
			return true;
		}
	}

	public static boolean validateEmailAddress(String emailId) {
		Pattern emailNamePtrn = Pattern
				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mtch = emailNamePtrn.matcher(emailId);
		if (mtch.matches()) {
			return true;
		}
		return false;
	}

	public static boolean validatePassword(String password) {
		Pattern pattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();

	}

	public static boolean isValidUserName(String userName) {
		Pattern userNamePattern = Pattern.compile("^[a-z0-9_-]{6,14}$");
		Matcher matcher = userNamePattern.matcher(userName);
		return matcher.matches();

	}

	public static boolean isValidMobileNumber(String mobileNumber) {
		Pattern mobileNumberPattern = Pattern.compile("\\d{10}");
		Matcher matcher = mobileNumberPattern.matcher(mobileNumber);
		return matcher.matches();
	}

	public static boolean isPasswordMatch(String password, String confirmPassword) {
		return password.equals(confirmPassword);
	}

}