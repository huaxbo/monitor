package com.hxb.common.secure.priv ;

/**
 * 访问权限注释类
 */
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckPrivJQuery {
	//优先级1(最高)，如果其有值，则不在判断以以下变量取值
	String value() default "" ;
	//优先级2，拥有全部权限才可 , 如果其有值，则不在判断以下变量取值
	String[] ifAll() default{""} ;
	//优先级3 , 如果有一个权限即可 , 如果其有值，则不在判断以下变量取值
	String[] ifAny() default{""} ;
}
