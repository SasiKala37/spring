package com.bridgelabz.fundoonote.exceptions;

public class RegisterException extends Exception {
  
	private static final long serialVersionUID = 1L;
private String message;

   
public RegisterException(String message) {
	//super(message);
    this.message=message;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}
   
}
