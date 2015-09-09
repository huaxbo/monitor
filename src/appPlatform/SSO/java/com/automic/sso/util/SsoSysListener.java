package com.automic.sso.util;

import java.net.URL;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;

import org.apache.log4j.PropertyConfigurator;

import com.automic.util.Config;

import com.automic.sso.session.server.*;
import com.automic.sso.commu.udpServer.server.*;


public class SsoSysListener implements ServletContextListener {
	
	private static ApplicationContext springContext ;

	private static FlushOracleThread flushThread ;
	public static String SYSCONFIGLOCATION = "sysConfigLocation" ;
	/**
	 * 关闭应用服务器上下文时，执行此方法
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		//System.out.println("应用服务器关闭时！");
	}
	/**
	 * 应用服务器上下文初始化时，执行此方法
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext con = event.getServletContext();
		String configName = con.getInitParameter(SYSCONFIGLOCATION);
		//this.initLog4j();
		this.initConfig(configName);
		this.getSpringFrame(con);
		try{
			this.startInnerServer() ;
		}catch(Exception e){
			System.out.println("启动内部UDP通信、会话管理子服务失败！");
			e.printStackTrace() ;
		}
		String uo = Config.getConfig("w.oracleDB") ;
		if(uo != null && uo.trim().equals("1")){
			this.flushOracle();
		}
	}

	
	/**
	 * 初始化log日志
	 * 
	 * @return boolean
	 */
	private boolean initLog4j() {
		try {
			URL configFileURL = null;
			configFileURL = SsoSysListener.class.getResource("/log4j.properties");
			if(configFileURL == null){
				configFileURL = SsoSysListener.class.getResource("/config/log4j.properties");
			}
			PropertyConfigurator.configure(configFileURL);
			//System.out.println("系统运行日志系统初始化完毕");
			return true;
		} catch (Exception e) {
			System.out.println("系统启动时，初始化log4j日志出错 !");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 解析系统配置
	 * @return
	 */
	private boolean initConfig(String configName){
		try {
			URL configFileURL = null;
//			configFileURL = SysListener.class.getResource("/config/config.xml");
			configFileURL = SsoSysListener.class.getResource("/config/" + configName);
			if(configFileURL == null){
				configFileURL = SsoSysListener.class.getResource("/" + configName);
			}
			Config.getInstance().init(configFileURL) ;
			return true;
		} catch (Exception e) {
			System.out.println("系统启动时，初始化配置出错 !");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 启动与通信服务器交互的子服务
	 *
	 */
    private void startInnerServer()throws Exception {
		SeServer ss = new SeServer() ;
		ss.init("/SSO-UDP.xml") ;
		ss.start() ;
		
		UdpServer us = new UdpServer() ;
		us.init("/SSO-UDP.xml") ;
		us.start() ;
		
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
	 * 刷数据库连接，防止oracle偷偷关闭连接
	 */
	private void flushOracle(){
		if(Constant.isUseOracle()){
			flushThread = new FlushOracleThread() ;
			flushThread.start() ;
		}
	}

	private class FlushOracleThread extends Thread{
		private long intv ;
		private boolean first ;
		private com.automic.util.flushOracleDB.FlushBusi busi ;
		public FlushOracleThread(){
			intv = 60000 ;
			first = true ;
			this.setDaemon(true) ;
		}
		@SuppressWarnings("finally")
		public void run(){
			while(true){
				try{
					if(first){
						sleep(intv * 2) ;
						first = false ;
						busi = (com.automic.util.flushOracleDB.FlushBusi)
						springContext.getBean("flushOrcalDbBusi") ;
					}else{
						sleep(intv) ;
					}
				}catch(Exception e){
					;
				}finally{
					try{
						if(busi != null){
							busi.flush() ;
						}
					}catch(Exception e){
						;
					}finally{
						continue ;
					}
				}
			}
		}
	}
}


