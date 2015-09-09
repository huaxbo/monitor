package com.hxb.monitor.util;


import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hxb.monitor.commu.autoTask.cmdRlt.CmdRltTask;
import com.hxb.monitor.commu.localServer.LocalServerFac;
import com.hxb.monitor.commu.protocol.CommandConstant;
import com.hxb.monitor.web.manager.server.ServerBusi;
import com.hxb.monitor.web.manager.server.ServerVO;

public class MonitorListener implements ServletContextListener{

	private Logger log = Logger.getLogger(MonitorListener.class);
	
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
		//init tasks
		//初始化测控设备协议
		initMeterProtocols();
		//初始化通讯服务本地通讯连接
		new InitLocalServer().start();
		//启动远程命令结果处理任务
		CmdRltTask.getSingleOne().start();
		
		
	}
	
	
	/**
	 * 初始化测控设备协议
	 */
	private void initMeterProtocols(){
		log.info("===启动：初始化通讯协议列表！");
		CommandConstant.initProtocols();
		log.info("===结束：初始化通讯协议列表！");
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

	
	/**
	 * 初始化local server
	 */
	class InitLocalServer extends Thread{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			log.info("===启动：初始化本地通讯！");
			ServerBusi csBusi = (ServerBusi)springContext.getBean("serverBusi");
			List<Object> lt = csBusi.list(new Hashtable<String,Object>(0));
			for(Object o:lt){
				ServerVO vo = (ServerVO)o;
				LocalServerFac.build(vo.getId(), vo.getIp(), vo.getPort(), vo.getContext());
			}
			log.info("===结束：初始化本地通讯！");
		}
		
	}
}
