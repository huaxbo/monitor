package com.automic.sso.session.server;

import java.net.URL;
import java.util.HashMap;

import org.apache.log4j.PropertyConfigurator;

//import org.apache.log4j.Logger;
import com.automic.sso.session.config.Config;
import com.automic.common.timerTask.*;
import com.automic.sso.session.session.SessionManager;


public class SeServer {
	private static String thisServerId = SeServer.class.getSimpleName() ;
	
//	private static Logger log = Logger.getLogger(SsoServer.class.getName()) ;
	
	/**
	 * 初始化方法
	 * @param thisServerId 本服务的ID
	 * @param associateServerIds ,//相关联的子服务ID集合
	 * @param configPath 的配置文件路径
	 * @throws ACException
	 */
	public void init(String configPath) throws Exception {

		SeServerContext ctx = SeServerContext.singleInstance();
		//注册子服务配置集合
		ctx.setObject(SeConstance.SSOCONFIGS , new HashMap<String , String>()) ;

		//分析配置文件
		Config con = new Config() ;
		con.createDom(configPath) ;
		con.parseOptions(configPath) ;
	}
	/**
	 * 初始化方法
	 * @param thisServerId 本服务的ID
	 * @param associateServerIds ,//相关联的子服务ID集合
	 * @param configPath 的配置文件路径
	 * @throws ACException
	 */
	@SuppressWarnings("unchecked")
	public void start() throws Exception {

		SeServerContext ctx = SeServerContext.singleInstance();

		//得到子服务配置集合
		HashMap<String, String> configs = (HashMap<String, String>) ctx
		.getObject(SeConstance.SSOCONFIGS);
		
		//////////////////////////////
		//启动检查会话过期的工作任务 , 本工作任务是定时清除过期的会话
		HashMap<String , Object> jobDataMap = new HashMap<String , Object>() ;
		String sessionTime = configs.get(SeConstance.SESSIONTIME) ;
		Long seTime = Long.parseLong(sessionTime) ;
		seTime = seTime * 60 * 1000 ;
		jobDataMap.put(SeConstance.SESSIONTIME , seTime ) ;
		
		
		//////////////////////////
		//启动任务调度工作容器,本工作任务是定时清除过期的会话
		new ACSchedularJob().addSecondlyJob(
				System.nanoTime() + "job",
				System.nanoTime() + "group",
				SessionManager.class , 
				jobDataMap ,
				SeConstance.DELAYCONSTE , 
				SeConstance.INTERVAL ,
				-1) ;
		
		
		System.out.println("单点登录服务(" + thisServerId + ")已经启动");
	}
	
	/**
	 * 测试
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		SeServer o = new SeServer() ;
		o.initLog4j() ;
		o.init("/sso.xml") ;
		o.start() ;
	}
	
	/**
	 * 初始化log日志
	 * 
	 * @return boolean
	 */
	private boolean initLog4j() {
		try {
			URL configFileURL = null;
			configFileURL = SeServer.class.getResource("/log4j.properties");
			if(configFileURL == null){
				configFileURL = SeServer.class.getResource("/config/log4j.properties");
			}
			PropertyConfigurator.configure(configFileURL);
			System.out.println("服务器运行日志系统初始化完毕");
			return true;
		} catch (Exception e) {
			System.out.println("通信服务器启动时，初始化log4j日志出错 !");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

}
