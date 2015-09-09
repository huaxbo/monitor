package com.hxb.commu.remote.tcp.manager.session.task;


import org.apache.log4j.Logger;

import com.hxb.commu.remote.tcp.manager.session.SessionManager;

public class SessionManagerTask{
	
	
	Logger log = Logger.getLogger(SessionManagerTask.class);
	private static SessionManagerTask single;
	
	/**
	 * private construct
	 */
	private SessionManagerTask(){}
	
	/**
	 * get single instance
	 * @return
	 */
	public static SessionManagerTask getSingle(){
		if(single == null){
			single = new SessionManagerTask();
		}
		
		return single;
	}
	
	/**
	 * @throws Exception
	 */
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		SessionManager smgr = SessionManager.getSingleInstance();
		smgr.cleanTimeout();		
	}	
}
