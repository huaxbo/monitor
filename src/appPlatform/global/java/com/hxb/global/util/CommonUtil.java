package com.hxb.global.util;

import java.util.Random;


import com.hxb.util.DateTime;


public class CommonUtil {
	
	/**
	 * @param d
	 * @param scale
	 * @return
	 */
	public static Double roundDouble(Double d , int scale){
		String temp = "##." ;
		for(int i = 0 ; i < scale ; i++){
			temp += "0" ;
		}
		return Double.valueOf(new java.text.DecimalFormat(temp).format(d)) ;
	}
	
	/** 
	* @Title: createImgName 
	* @Description: TODO(���ɸ�������:ʱ��_3Ϊ�����) 
	* @return String    �������� 
	* @throws 
	*/ 
	public static String createAttaName(){
		int rd = new Random().nextInt(1000);		
		return DateTime.yyyyMMddhhmmss() + "_" + ((rd<10?"00":(rd<100?"0":"")) + rd);
	}	
	
	/**
	 * ��ȡclass���ƣ�����ĸСд�����಻��
	 * @param clazz
	 * @return
	 */
	public static String getClassName(Class<?> clazz){
		if(clazz == null){
			return null;
		}
		String nm = clazz.getSimpleName();
		if(nm.length() < 2){
			return nm.toLowerCase();
		}
		
		return nm.substring(0,1).toLowerCase() + nm.substring(1);
	}
}
