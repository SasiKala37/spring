package com.bridgelabz.scope;

public class HelloMessage {
	private String message;
public HelloMessage() {
	System.out.println("Constructor calling");
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	System.out.println("Setter calling");
	this.message = message;
}

	
}
