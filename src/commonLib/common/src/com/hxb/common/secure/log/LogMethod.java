package com.hxb.common.secure.log;

import org.aspectj.lang.ProceedingJoinPoint;

public class LogMethod {
	public Object aroundMethod(ProceedingJoinPoint p) throws Throwable {
		System.out.println("����(LogMethod)���˷���(aroundMethod)�����ɼ��ɵ�ϵͳʵ��") ;

		return null ;
	}
}
