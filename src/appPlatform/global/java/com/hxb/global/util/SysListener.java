package com.hxb.global.util;

import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hxb.util.Config;

public class SysListener implements ServletContextListener {

	//得到spring容器，以备在非web环境中应用spring框架
	private static ApplicationContext springContext ;

	private static FlushOracleThread flushThread ;
	
	public static String SYSCONFIGLOCATION = "sysConfigLocation" ;
	
	/**
	 * 关闭应用服务器上下文时，执行此方法
	 */
	public void contextDestroyed(ServletContextEvent event) {
		//System.out.println("应用服务器关闭时！");
	}

	/**
	 * 应用服务器上下文初始化时，执行此方法
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext con = event.getServletContext();
		this.initConfig(con);
		this.getSpringFrame(con);
		this.flushOracle();
	}
		
	
	/**
	 * 解析系统配置
	 * @return
	 */
	private boolean initConfig(ServletContext con){
		try {
			String configName = con.getInitParameter(SYSCONFIGLOCATION);
			if(configName == null || configName.trim().equals("")){
				throw new Exception("在web.xml文件中，未配置配置文件名称!") ;
			}
 			String confs[] = configName.split(",");
 			String conf = null ;
 			for(int i = 0 ; i < confs.length ; i++){
 				conf = confs[i].trim() ;
 				if(conf != null && !conf.equals("")){
 					URL configFileURL = null;
 					configFileURL = SysListener.class.getResource("/config/" + conf);
 					if(configFileURL == null){
 						configFileURL = SysListener.class.getResource("/" + conf);
 					}
 					Config.getInstance().init(configFileURL) ;
 				}
 			}
			return true;
		} catch (Exception e) {
			System.out.println("系统启动时，初始化配置出错 !");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	 /**
     * 得到spring容器，以备在非web环境中应用spring框架
     *
     */
    private void getSpringFrame(ServletContext sc){
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		springContext = ctx ;
		HttpUtil.springContext = ctx;
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
		private com.hxb.util.flushOracleDB.FlushBusi busi ;
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
						busi = (com.hxb.util.flushOracleDB.FlushBusi)
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
