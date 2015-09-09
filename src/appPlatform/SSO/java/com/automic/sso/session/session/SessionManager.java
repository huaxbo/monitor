package com.automic.sso.session.session;

import com.automic.common.timerTask.*;
import com.automic.sso.session.server.* ;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SessionManager extends ACTaskJob {

	/**
	 * ����¼������Ѿ���¼�����ػỰ�е��û�����
	 * ��֮�򷵻ؿ�
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
	 * �û�������ϵͳ����ôˢ�»Ự����С����ʱ�䣬��ֹ�Ự��ʱ
	 * @param sessionId
	 */
	public void freshSession(String sessionId){
		 SessionCach.freshSession(sessionId) ;
	}
	
	
	/**
	 * �˳���¼
	 * @param sessionId
	 */
	public void logout(String sessionId){
		 SessionCach.logout(sessionId) ;
	}
	
	
	/**
	 * ����Ự����
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
	 * ��ʱ����������
	 */
	@Override
	public void execute() throws Exception {
		throw new Exception("�÷�������ʵ�֣�Ҳδʵ�֣�") ;
	}
	/**
	 * ��ʱ����������
	 */
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		JobDataMap map = ctx.getJobDetail().getJobDataMap() ;
		Long sessionTime = (Long)map.get(SeConstance.SESSIONTIME) ;
		SessionCach.cleanSession(sessionTime.longValue()) ;
	}
}
