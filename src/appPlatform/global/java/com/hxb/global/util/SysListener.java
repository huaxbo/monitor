package com.hxb.global.util;

import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hxb.util.Config;

public class SysListener implements ServletContextListener {

	//�õ�spring�������Ա��ڷ�web������Ӧ��spring���
	private static ApplicationContext springContext ;

	private static FlushOracleThread flushThread ;
	
	public static String SYSCONFIGLOCATION = "sysConfigLocation" ;
	
	/**
	 * �ر�Ӧ�÷�����������ʱ��ִ�д˷���
	 */
	public void contextDestroyed(ServletContextEvent event) {
		//System.out.println("Ӧ�÷������ر�ʱ��");
	}

	/**
	 * Ӧ�÷����������ĳ�ʼ��ʱ��ִ�д˷���
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext con = event.getServletContext();
		this.initConfig(con);
		this.getSpringFrame(con);
		this.flushOracle();
	}
		
	
	/**
	 * ����ϵͳ����
	 * @return
	 */
	private boolean initConfig(ServletContext con){
		try {
			String configName = con.getInitParameter(SYSCONFIGLOCATION);
			if(configName == null || configName.trim().equals("")){
				throw new Exception("��web.xml�ļ��У�δ���������ļ�����!") ;
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
			System.out.println("ϵͳ����ʱ����ʼ�����ó��� !");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	 /**
     * �õ�spring�������Ա��ڷ�web������Ӧ��spring���
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
	 * ˢ���ݿ����ӣ���ֹoracle͵͵�ر�����
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
