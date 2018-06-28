package com.bridgelabz.customizedannotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Documented
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})//ElementType.METHOD
@Retention(RetentionPolicy.CLASS)

public @interface Phone {
		/*String os1="phoneVersion";
		double version1=1.0;*/
	    String os() default "Symbian";
	    double version() default 1;
	
}
