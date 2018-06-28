package com.bridgelabz.autowired;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAutowired {

	public static void main(String[] args) {
	
	ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("product.xml");
	Customer customer=(Customer) applicationContext.getBean("customerBean");
	customer.show();
 }

}
