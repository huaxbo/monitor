package com.hxb.util;


public class ChangeCode {
	  public ChangeCode() {
	  }

	  public static String changeISO2GBK(String str){
		  if(str == null){
			  return null ;
		  }
	    String s = "" ;
	    try{
	      s = new String(str.getBytes("ISO8859-1") , "GBK") ;
	      s = s.replaceAll("'" , "\'") ;
	    }catch(Exception e){
	      return str ;
	    }
	    return s ;
	  }
	  public static String changeGBK2ISO(String str){
		  if(str == null){
			  return null ;
		  }
		    String s = "" ;
		    try{
		      s = new String(str.getBytes("GBK"), "ISO8859-1") ;
		      s = s.replaceAll("'" , "\'") ;
		    }catch(Exception e){
		      return str ;
		    }
		    return s ;
		  }
	  public static String changeISO2GB(String str){
		  if(str == null){
			  return null ;
		  }
		    String s = "" ;
		    try{
		      s = new String(str.getBytes("ISO8859-1") , "gb2312") ;
		      s = s.replaceAll("'" , "\'") ;
		    }catch(Exception e){
		      return str ;
		    }
		    return s ;
		  }
	  public static String changeISO2UTF8(String str){
		  if(str == null){
			  return null ;
		  }
		    String s = "" ;
		    try{
		      s = new String(str.getBytes("ISO8859-1") , "UTF-8") ;
		      s = s.replaceAll("'" , "\'") ;
		    }catch(Exception e){
		      return str ;
		    }
		    return s ;
		  }
	  public static String changeGB2ISO(String str){
		  if(str == null){
			  return null ;
		  }
		    String s = "" ;
		    try{
		      s = new String(str.getBytes("gb2312"), "ISO8859-1") ;
		      s = s.replaceAll("'" , "\'") ;
		    }catch(Exception e){
		      return str ;
		    }
		    return s ;
		  }
	  
	  public static String changeURLCode(String str){
		  if(str == null){
			  return null ;
		  }
		    String s = "" ;
		    try{
		      s = java.net.URLEncoder.encode(str,"GBK") ;
		    }catch(Exception e){
		      return str ;
		    }
		    return s ;
		  }
	  public static String anChangeURLCode(String str){
		  if(str == null){
			  return null ;
		  }
		    String s = "" ;
		    try{
		      s = java.net.URLDecoder.decode(str,"GBK") ;
		    }catch(Exception e){
		      return str ;
		    }
		    return s ;
		  }

     public static void main(String g[]){
    	 String s = "¹²ÓÐ" ;
    	 s = ChangeCode.changeGBK2ISO(s) ;
    	 System.out.println(s) ;
     }


	}
