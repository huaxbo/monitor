package com.automic.sso.session.session;

import com.automic.common.timerTask.*;
import com.automic.sso.session.server.* ;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SessionManager extends ACTaskJob {

	/**
	 * 检查登录，如果已经登录，返回会话中的用户数据
	 * 反之则返回空
	 * @param sessionId
	 * @return
	 */
	public Object checkLogin(String sessionId){
		Session se = SessionCach.getSession(sessionId) ;
		if(se != null){
			this.freshSession(sessionId) ;
			return se.data ;
		}
		return null ;
	}
	
	/**
	 * 用户访问了系统，那么刷新会话，减小发呆时间，防止会话过时
	 * @param sessionId
	 */
	public void freshSession(String sessionId){
		 SessionCach.freshSession(sessionId) ;
	}
	
	
	/**
	 * 退出登录
	 * @param sessionId
	 */
	public void logout(String sessionId){
		 SessionCach.logout(sessionId) ;
	}
	
	
	/**
	 * 存入会话数据
	 * @param sessionId
	 * @param se
	 */
	public void logined(Object o , String sessionId){
		Session se = new Session() ;
		se.stamp = System.currentTimeMillis() ;
		se.data = o ;
		SessionCach.putSession(sessionId, se) ;
	}

	/**
	 * 定时任务工作方法
	 */
	@Override
	public void execute() throws Exception {
		throw new Exception("该方法不用实现，也未实现！") ;
	}
	/**
	 * 定时任务工作方法
	 */
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		JobDataMap map = ctx.getJobDetail().getJobDataMap() ;
		Long sessionTime = (Long)map.get(SeConstance.SESSIONTIME) ;
		SessionCach.cleanSession(sessionTime.longValue()) ;
	}
}
