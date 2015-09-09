package com.hxb.imulator.config;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Centers {

	public static HashMap<String,String[]> centers = new HashMap<String,String[]>(0);
	/**
	 * 中心1:[id,ip,port]
	 */
	public static String[] center_1 = {"center_1","localhost","9014"};
	
	
	static{		
		Centers cts = new Centers();
		Field[] fds = Centers.class.getFields();
		for(Field fd:fds){
			if(fd.getType() == String[].class){
				try {
					String[] arr = (String[])fd.get(cts);					
					centers.put(arr[0], new String[]{arr[1],arr[2]});					
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
	 * 获取中心地址
	 * @param centerId
	 * @return
	 */
	public static String[] getCenter(String centerId){
		
		return centers.get(centerId);
		
	}
}
