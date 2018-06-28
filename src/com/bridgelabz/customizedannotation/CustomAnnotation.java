package com.bridgelabz.customizedannotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CustomAnnotation {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
       //Target element type TYPE
		Redmi obj=new Redmi();  
        Phone c=obj.getClass().getAnnotation(Phone.class); 
        System.out.println(c.os()+" "+c.version());
      
        //Target element type Method
        Class<? extends Redmi> c1=obj.getClass();
        Method[] method=c1.getMethods();
        Annotation annotation=c1.getAnnotation(Phone.class);
        System.out.println(c.os());
}
}
