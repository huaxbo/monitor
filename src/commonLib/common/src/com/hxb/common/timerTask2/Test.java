/**
 * 
 */
package com.hxb.common.timerTask2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.hxb.common.timerTask2.inter.TaskImpl;
import com.hxb.util.DateTime;

/**
 * @author hxb
 *
 */
public class Test{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date dt = null;
		try {
			dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-05-08 21:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dt = new Date(System.currentTimeMillis());
		}finally{;}
		TimerDispatcher td = TimerDispatcher.getOne();
		if(td.canDispatcher()){
			td.startDispatcher(dt,Calendar.SECOND,10,new TaskImpl() {
				@Override
				public void work() {
					// TODO Auto-generated method stub
					System.out.println(DateTime.yyyy_MM_dd_HH_mm_ss() + "#####111#######dispatcher successfully!!");
				}
			});			
		}
		
		td = TimerDispatcher.getOne();
		if(td.canDispatcher()){
			td.startDispatcher(dt,Calendar.SECOND,5,new TaskImpl() {
				@Override
				public void work() {
					// TODO Auto-generated method stub
					System.out.println(DateTime.yyyy_MM_dd_HH_mm_ss() + "#####222#######dispatcher successfully!!");
				}
			});
		}
	}
}