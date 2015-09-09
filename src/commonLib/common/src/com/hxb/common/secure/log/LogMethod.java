package com.hxb.common.secure.log;

import org.aspectj.lang.ProceedingJoinPoint;

public class LogMethod {
	public Object aroundMethod(ProceedingJoinPoint p) throws Throwable {
		System.out.println("此类(LogMethod)及此方法(aroundMethod)必须由集成的系统实现") ;

		return null ;
	}
}
