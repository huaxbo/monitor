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
		//init tasks
		//��ʼ������豸Э��
		initMeterProtocols();
		//��ʼ��ͨѶ���񱾵�ͨѶ����
		new InitLocalServer().start();
		//����Զ����������������
		CmdRltTask.getSingleOne().start();
		
		
	}
	
	
	/**
	 * ��ʼ������豸Э��
	 */
	private void initMeterProtocols(){
		log.info("===��������ʼ��ͨѶЭ���б�");
		CommandConstant.initProtocols();
		log.info("===��������ʼ��ͨѶЭ���б�");
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

	
	/**
	 * ��ʼ��local server
	 */
	class InitLocalServer extends Thread{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			log.info("===��������ʼ������ͨѶ��");
			ServerBusi csBusi = (ServerBusi)springContext.getBean("serverBusi");
			List<Object> lt = csBusi.list(new Hashtable<String,Object>(0));
			for(Object o:lt){
				ServerVO vo = (ServerVO)o;
				LocalServerFac.build(vo.getId(), vo.getIp(), vo.getPort(), vo.getContext());
			}
			log.info("===��������ʼ������ͨѶ��");
		}
		
	}
}
