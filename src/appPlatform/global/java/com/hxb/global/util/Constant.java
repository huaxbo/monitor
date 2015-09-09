package com.hxb.global.util;

import com.hxb.util.*;
public class Constant {
	public static final String message = "message" ;//消息键	
		
	public static final String SUCCESS = "success" ;
	public static final String FAILURE = "failure" ;
	
	
	//权限类型
	public static String powerType_0000 = "0000" ;
	public static String powerType_0020 = "0020" ;
	public static String powerType_0040 = "0040" ;
	public static String powerType_0060 = "0060" ;
	public static String powerType_0080 = "0080" ;
	public static String powerType_0100 = "0100" ;
	
////////////////////////////////////////////
//是否应用oracle数据库
private static Boolean ISUSEORACLE = null ;
public static boolean isUseOracle(){
if(ISUSEORACLE == null){
String s = Config.getConfig("sys.oracleDB") ;
if(s == null || s.trim().equals("1")){
ISUSEORACLE = true ;
}else{
ISUSEORACLE = false ;
}
}
return ISUSEORACLE ;
}
}
