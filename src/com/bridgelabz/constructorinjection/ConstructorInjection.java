package com.bridgelabz.constructorinjection;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ConstructorInjection {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Resource resource=new ClassPathResource("application.xml");
		BeanFactory factory=new XmlBeanFactory(resource);
		Employee employee=(Employee) factory.getBean("employeeBean");
		employee.show();
		System.out.println("hash code "+employee.toString()+"\n");
		
		Employee employee2=(Employee) factory.getBean("employeeBean");
		employee2.show();
		System.out.println("hashcode "+employee2.toString());
		employee2.run();
	}

}
