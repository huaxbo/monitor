package com.hxb.common.secure.priv ;

/**
 * ����Ȩ��ע����
 */
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckPrivJQuery {
	//���ȼ�1(���)���������ֵ�������ж������±���ȡֵ
	String value() default "" ;
	//���ȼ�2��ӵ��ȫ��Ȩ�޲ſ� , �������ֵ�������ж����±���ȡֵ
	String[] ifAll() default{""} ;
	//���ȼ�3 , �����һ��Ȩ�޼��� , �������ֵ�������ж����±���ȡֵ
	String[] ifAny() default{""} ;
}
