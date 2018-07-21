package com.bridgelabz.fundoonote.note.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.bridgelabz.fundoonote.note.interceptor.NoteInterceptor;

@Configuration
public class NoteWebConfig implements WebMvcConfigurer{
	@Override
    public void addInterceptors(InterceptorRegistry registry) {   
        registry.addInterceptor(new NoteInterceptor()).addPathPatterns("/note/**");
        
    }
}
