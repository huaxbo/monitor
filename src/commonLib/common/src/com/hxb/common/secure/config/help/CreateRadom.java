package com.hxb.common.secure.config.help;

public class CreateRadom {
	
	public static int create(int min , int max){
		if(max < min){
			return min ;
		}
		if(max - min < min/2){
			return min ;
		}
		String mins = String.valueOf(min) ;
		int len = mins.length() ;
		char minfirst = mins.charAt(0) ;
	    double d = Math.random();
	    d = d * 10000000 ;
	    String s = String.valueOf(d);
	    s = minfirst + s ;
	    s = s.substring(0 ,len) ;
	    int n = Integer.parseInt(s) ;
	    if(n < min || n > max){
	    	n = create(min , max) ;
	    }
	    return n ;
	}
	
}
