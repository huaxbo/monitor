package com.hxb.commu.util;

import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hxb.common.timerTask2.TimerDispatcher;
import com.hxb.common.timerTask2.inter.TaskImpl;
import com.hxb.commu.remote.tcp.manager.commands.task.CommandManagerTask;
import com.hxb.commu.remote.tcp.manager.session.task.SessionManagerTask;
import com.hxb.commu.remote.tcp.server.RemoteServer;
import com.hxb.util.Config;
import com.hxb.util.DateTime;

public class CommuListener implements ServletContextListener{

	private Logger log = Logger.getLogger(CommuListener.class);
	
	//�õ�spring�������Ա��ڷ�web������Ӧ��spring���
	private static ApplicationContext springContext ;
		
	/**
	 * �ر�Ӧ�÷�����������ʱ��ִ�д˷���
	 */
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	/**
	 * Ӧ�÷����������ĳ�ʼ��ʱ��ִ�д˷���
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext con = event.getServletContext();
		this.getSpringFrame(con);
		//��ʼ������
		CommuConstant.remote_port = Integer.parseInt(Config.getConfig("remote_port"));
		
		//��������Ⱥ
		
		//����TCP����
		if(RemoteServer.getSingle().start()){
			//����session�Զ���������������ʱ�̴���ͳ������		
			try{
				if(CommuConstant.noneReceive_interval>0){
					TimerDispatcher td = TimerDispatcher.getOne();
					if(td.canDispatcher()){
						td.startDispatcher(DateTime.dateFrom_yyyy_MM_dd_HH(DateTime.yyyy_MM_dd_HH()),
								Calendar.MINUTE, CommuConstant.remote_sessionMgrInteval, new TaskImpl() {
									@Override
									public void work() {
										// TODO Auto-generated method stub
										try {
											SessionManagerTask.getSingle().execute();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											log.error("��ʱ����ʱ�����ݽ���������������ִ���쳣��",e);
										}finally{}
									}
								});
					}
				}			
			}catch(Exception e){
				log.error("������ʱ����ʱ�����ݽ����������������쳣��",e);
			}finally{}
			
			//����Զ�������Զ���������
			try{
				TimerDispatcher td = TimerDispatcher.getOne();
				if(td.canDispatcher()){
					td.startDispatcher(DateTime.dateFrom_yyyy_MM_dd_HH(DateTime.yyyy_MM_dd_HH()),
							Calendar.SECOND, CommuConstant.command_scanInterval, new TaskImpl() {
								@Override
								public void work() {
									// TODO Auto-generated method stub
									try {
										CommandManagerTask.getSingle().execute();											
									} catch (Exception e) {
										// TODO Auto-generated catch block
										log.error("����Զ������������쳣��",e);
									}finally{}
								}
							});
				}		
			}catch(Exception e){
				log.error("����Զ������������쳣��",e);
			}finally{}
			
			
		}
		
		//����xxx�Զ���������
		
	}
		
	 /**
     * �õ�spring�������Ա��ڷ�web������Ӧ��spring���
     *
     */
    private void getSpringFrame(ServletContext sc){
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		springContext = ctx ;
    }

	public static ApplicationContext getSpringContext() {
		return springContext;
	}	
	
}
