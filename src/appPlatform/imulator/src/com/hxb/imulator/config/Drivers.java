package com.hxb.imulator.config;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.hxb.imulator.protocol.ver_test.VerTestDriver;

public class Drivers {

	public static HashMap<String,String> drivers = new HashMap<String,String>(0);
	
	/**
	 * Э�飺ver_test 
	 */
	public static final String ver_test[] = {"ver_test",VerTestDriver.class.getName()};
	
	
	static{		
		Drivers cts = new Drivers();
		Field[] fds = Drivers.class.getFields();
		for(Field fd:fds){
			if(fd.getType() == String[].class){
				try {
					String[] arr = (String[])fd.get(cts);					
					drivers.put(arr[0], arr[1]);					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{}				
			}			
		}				
	}
		
	
	/**
	 * @param protocol
	 * @return
	 */
	public static String getDriver(String protocol){
		
		return drivers.get(protocol);
	}
}
