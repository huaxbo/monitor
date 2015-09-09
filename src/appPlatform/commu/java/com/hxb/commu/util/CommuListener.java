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
	
	//得到spring容器，以备在非web环境中应用spring框架
	private static ApplicationContext springContext ;
		
	/**
	 * 关闭应用服务器上下文时，执行此方法
	 */
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	/**
	 * 应用服务器上下文初始化时，执行此方法
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext con = event.getServletContext();
		this.getSpringFrame(con);
		//初始化配置
		CommuConstant.remote_port = Integer.parseInt(Config.getConfig("remote_port"));
		
		//启动服务集群
		
		//启动TCP服务
		if(RemoteServer.getSingle().start()){
			//启动session自动处理任务。整分钟时刻触发统计任务。		
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
											log.error("定时清理超时无数据接收网络连接任务执行异常：",e);
										}finally{}
									}
								});
					}
				}			
			}catch(Exception e){
				log.error("启动定时清理超时无数据接收网络连接任务异常：",e);
			}finally{}
			
			//启动远程命令自动处理任务
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
										log.error("启动远程命令处理任务异常：",e);
									}finally{}
								}
							});
				}		
			}catch(Exception e){
				log.error("启动远程命令处理任务异常：",e);
			}finally{}
			
			
		}
		
		//启动xxx自动处理任务
		
	}
		
	 /**
     * 得到spring容器，以备在非web环境中应用spring框架
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
