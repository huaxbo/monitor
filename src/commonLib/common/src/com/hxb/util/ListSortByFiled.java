package com.hxb.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListSortByFiled<E> {
	
	    public  void Sort(List<E> list, final String method, final String sort){  
	    	 Collections.sort(list, new Comparator<Object>() {             
		            public int compare(Object a, Object b) {  
		                int ret = 0;  
		                try{     
		                	  Method m1 = ((E)a).getClass().getMethod(method, null);  
			                  Method m2 = ((E)b).getClass().getMethod(method, null);  
		                    if(sort != null && "desc".equals(sort))//µ¹Ðò  
		                        ret = m2.invoke(((E)b), null).toString().compareTo(m1.invoke(((E)a), null).toString());   
		                    else//ÕýÐò  
		                        ret = m1.invoke(((E)a), null).toString().compareTo(m2.invoke(((E)b), null).toString());  
		                }catch(NoSuchMethodException ne){
		             
		                	System.out.println(ne.getMessage());
		                }catch(IllegalAccessException ie){  
		                	
		                	 System.out.println(ie.getMessage());
		                }catch(InvocationTargetException it){  
		                
		                	 System.out.println(it.getMessage());
		                 
		                }  
		                return ret;  
		            }  
		         });  
		    }  
	
	

}
