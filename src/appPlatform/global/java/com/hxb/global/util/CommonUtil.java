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
	* @Description: TODO(生成附件名称:时间_3为随机数) 
	* @return String    返回类型 
	* @throws 
	*/ 
	public static String createAttaName(){
		int rd = new Random().nextInt(1000);		
		return DateTime.yyyyMMddhhmmss() + "_" + ((rd<10?"00":(rd<100?"0":"")) + rd);
	}	
	
	/**
	 * 获取class名称：首字母小写，其余不变
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
