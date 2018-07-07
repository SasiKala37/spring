package com.bridgelabz.springrestloginapp.util;

public class Utility {
	public static boolean validate(String password) {
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
	      //System.out.println(password.matches(pattern));
		return password.matches(pattern);
		
	}
}
