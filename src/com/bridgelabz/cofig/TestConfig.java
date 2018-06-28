package com.bridgelabz.cofig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestConfig {
public static void main(String[] args) {
	ApplicationContext context=new AnnotationConfigApplicationContext(BeanConfig.class);
	BeanConfigMessage beanConfigMessage=context.getBean(BeanConfigMessage.class);
	beanConfigMessage.setMessage("Bean configuaration done");
	beanConfigMessage.getMessage();
}
}
