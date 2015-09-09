package com.automic.sso.util;

import com.automic.util.Config ;

public class Constant {
	public static String message = "message" ;
	public static String YES = "1" ;
	public static String NO = "0" ;
	
	
	public static String level_1_str = "1" ;
	public static String level_2_str = "2" ;
	public static String level_3_str = "3" ;
	public static String level_4_str = "4" ;
	
	public static String debugPower="-1" ;
	//是否应用oracle数据库
	private static Boolean ISUSEORACLE = null ;
	public static boolean isUseOracle(){
		if(ISUSEORACLE == null){
			String s = Config.getConfig("wr.oracleDB") ;
			if(s == null || s.trim().equals(YES)){
				ISUSEORACLE = true ;
			}else{
				ISUSEORACLE = false ;
			}
		}
		return ISUSEORACLE ;
	}
}
