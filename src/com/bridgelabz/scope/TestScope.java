package com.bridgelabz.scope;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestScope {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext("scopes.xml");
		HelloMessage helloMessage=(HelloMessage) context.getBean("helloMessage");
		System.out.println(helloMessage.getMessage());
		System.out.println(helloMessage.toString());
		System.out.println("___________");
		HelloMessage helloMessage1=(HelloMessage) context.getBean("helloMessage");
		System.out.println(helloMessage.getMessage());
		System.out.println(helloMessage1.toString());
		System.out.println("*********");
	}

}
