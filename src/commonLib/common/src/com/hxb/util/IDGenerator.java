package com.hxb.util;


import java.io.Serializable;
import org.hibernate.id.*;
import org.hibernate.engine.*;
import org.hibernate.HibernateException ;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IDGenerator extends IdentityGenerator {

	private static String curr ;
	private static int add = 0 ;
	private static String dtStr = "yyMMddHHmmss" ;
	static {
		curr = new SimpleDateFormat(dtStr).format(new Date(System
				.currentTimeMillis()));
		new ResetTimerThread();
	}
   
	/**
	 * 
	 */
    public Serializable generate(SessionImplementor parm1,
    		Object parm2) throws HibernateException {
    	return curr + getAdd();	
    }
	/**
	 * 
	 */
    private synchronized String getAdd(){
    	add ++ ;
    	if(add < 10){
    		return "000" + add  ;
    	}else{
    		if(add < 100){
    			return "00" + add ;
    		}else{
    			if(add < 1000){
    				return "0" + add ;
    			}else{
    				if(add < 9999){
    					return "" + add ;
    				}else{
	    				String s = "9999" ;
	    				try{
	    					Thread.sleep(1) ;
	    				}catch(Exception e){
	    					;
	    				}finally{
		    				add = 0 ;
	    				}
	    				return s ;
    				}
    			}
    		}
    	}
    }
	private static void update() {
		curr = new SimpleDateFormat(dtStr).format(new Date(System
				.currentTimeMillis()));
		//System.out.println(curr) ;
	}

	/**
	 * 
	 */
	private static class ResetTimerThread extends Thread {
		public ResetTimerThread() {
			super();
			this.setDaemon(true);
			start();
		}

		public void run() {
			while (true) {
				update();
				try {
					Thread.sleep(1000);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String args[]){
		IDGenerator o = new IDGenerator() ;
		for(int i = 0 ; i < 1000 ; i++){
			System.out.println((String)(o.generate(null , null))) ;
			try{
			  Thread.sleep(200) ;
			}catch(Exception e){
				e.printStackTrace() ;
			}
		}
	}
	
}
