package com.bridgelabz.qualifier;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestQualifier {

	public static void main(String[] args) {
		ApplicationContext context= new ClassPathXmlApplicationContext("qualifier.xml");
		Customer customer=(Customer)context.getBean("customerBean");
		System.out.println(customer.getCustomerName());
	}

}
