package com.hxb.common.secure.log;

import java.util.Hashtable;

import com.hxb.common.secure.log.LogInterf;

public class TestLog implements LogInterf {

	public void log(Hashtable<String, String> logSet) {
		System.out.println("����(TestLog)���˷���(log)�����ɼ��ɵ�ϵͳʵ��") ;
	}

}
