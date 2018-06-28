package com.bridgelabz.beaninitanddestroy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInitDestroy {

	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("web.xml");
		BeanInitAndDestroy beanInitAndDestroy=(BeanInitAndDestroy) context.getBean("initBean");
		beanInitAndDestroy.getMessage();
		((AbstractApplicationContext) context).registerShutdownHook();
	}

}
;