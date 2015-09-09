package com.hxb.common.secure.log;

import java.util.Hashtable;

import com.hxb.common.secure.log.LogInterf;

public class TestLog implements LogInterf {

	public void log(Hashtable<String, String> logSet) {
		System.out.println("此类(TestLog)及此方法(log)必须由集成的系统实现") ;
	}

}
