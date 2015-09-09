/**
 * 
 */
package com.hxb.common.timerTask2;

import java.util.Date;
import java.util.Timer;

import com.hxb.common.timerTask2.impl.TimerTaskImpl;
import com.hxb.common.timerTask2.inter.TaskInter;
/**
 * @author hxb
 *
 */
public class TimerDispatcher {

	private Timer timer = null;
	
	/**
	 * 
	 */
	private TimerDispatcher(){
		
		timer = new Timer();
	}
	
	/**
	 * @return
	 */
	public static TimerDispatcher getOne(){
		
		return new TimerDispatcher();
	}
	
	/**
	 * whether timer can schdule work...
	 * @return
	 */
	public boolean canDispatcher(){
		if(timer != null){
			return true;
		}
		return false;
	}
	/**
	 * start dispatcher work...
	 */
	public void startDispatcher(Date date,int calendarTime,int interval,TaskInter taskInter){
		if(timer != null){
			timer.schedule(new TimerTaskImpl(this,date,calendarTime,interval,taskInter), date);
		}
	}
	/**
	 * end dispatcher work...
	 */
	public void endDispatcher(){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
	}
}
