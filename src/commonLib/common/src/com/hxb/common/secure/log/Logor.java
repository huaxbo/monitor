package com.hxb.common.secure.log ;

/**
 * ��־ע����
 */
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logor {
	//��־����
	String value() default "" ;
}
