package com.hxb.common.secure.log ;

/**
 * 日志注释类
 */
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Logor {
	//日志内容
	String value() default "" ;
}
