package com.bridgelabz.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
	@Bean(initMethod="",destroyMethod="")
	public BeanConfigMessage beanConfiguration() {
		return new BeanConfigMessage();
	}
}
