package com.hxb.util;

public class CreateRadom {

	/**
	 * ������λ�����
	 * @return
	 */  
	public String create(){
	    double d = Math.random();
	    String s = String.valueOf(d);
	    int index = 0;
	    String ss = "";
	    try{
	      index = s.indexOf('.') + 1;
	      ss = s.substring(index , index + 6);
	    } catch(Exception e){
	      ss = "740414";
	    }
	   return ss ;
	 }

	 /**
	  * �õ�һ��С��max�������
	  * @param max int
	  * @return int
	  */
	 public int create(int max){
	   if(max > 9){
	     max = 9 ;
	   }
	   double d = Math.random();
	   int n = 0 ;
	   int m = 0 ;
	   String s = String.valueOf(d);
	   for(int i = 4 ; i < s.length() ; i++){
	     m = Integer.parseInt(s.charAt(i)+"");
	     if(m < max){
	       n = m ;
	       break ;
	     }
	   }
	   return n ;
	}

}
