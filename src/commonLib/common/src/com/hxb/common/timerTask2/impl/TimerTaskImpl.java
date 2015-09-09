/**
 * 
 */
package com.hxb.common.timerTask2.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import com.hxb.common.timerTask2.TimerDispatcher;
import com.hxb.common.timerTask2.inter.TaskInter;
/**
 * @author hxb
 *
 */
public class TimerTaskImpl extends TimerTask {

	private Date date;
	private int interval;
	private int calendarTime;
	private TaskInter task;
	private TimerDispatcher td;
	/**
	 * @param date 首次调度时间 yyyy-MM-dd HH:mm:ss
	 * @param calendarTime 调度单位，Calendar中类型,eg.MINUTE、HOUR...
	 * @param interval 调度间隔，eg.5m、5h...
	 */
	public TimerTaskImpl(TimerDispatcher td,Date date,int calendarTime,int interval,TaskInter task){
		this.td = td;
		this.date = date;
		this.calendarTime = calendarTime;
		this.interval = interval;
		this.task = task;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		/*****local task***********/
		String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		String lt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
		boolean work = false;
		if(this.task != null && dt.compareTo(lt) >= 0){
			task.work();//调度工作
			work = true;
		}
		if(work){
			lt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));			
		}
		/******start next dispatcher*********/
		Calendar calendar = Calendar.getInstance();
		while(dt.compareTo(lt) <= 0){//获取下次执行时间
			calendar.setTime(date);
			calendar.add(calendarTime, interval);
			date = calendar.getTime();
			dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		}
		if(td.canDispatcher()){
			td.startDispatcher(date,calendarTime,interval,task);	
		}
	}
}
