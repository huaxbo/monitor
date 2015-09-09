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
	 * ��ʼ������
	 * @param thisServerId �������ID
	 * @param associateServerIds ,//��������ӷ���ID����
	 * @param configPath �������ļ�·��
	 * @throws ACException
	 */
	public void init(String configPath) throws Exception {

		SeServerContext ctx = SeServerContext.singleInstance();
		//ע���ӷ������ü���
		ctx.setObject(SeConstance.SSOCONFIGS , new HashMap<String , String>()) ;

		//���������ļ�
		Config con = new Config() ;
		con.createDom(configPath) ;
		con.parseOptions(configPath) ;
	}
	/**
	 * ��ʼ������
	 * @param thisServerId �������ID
	 * @param associateServerIds ,//��������ӷ���ID����
	 * @param configPath �������ļ�·��
	 * @throws ACException
	 */
	@SuppressWarnings("unchecked")
	public void start() throws Exception {

		SeServerContext ctx = SeServerContext.singleInstance();

		//�õ��ӷ������ü���
		HashMap<String, String> configs = (HashMap<String, String>) ctx
		.getObject(SeConstance.SSOCONFIGS);
		
		//////////////////////////////
		//�������Ự���ڵĹ������� , �����������Ƕ�ʱ������ڵĻỰ
		HashMap<String , Object> jobDataMap = new HashMap<String , Object>() ;
		String sessionTime = configs.get(SeConstance.SESSIONTIME) ;
		Long seTime = Long.parseLong(sessionTime) ;
		seTime = seTime * 60 * 1000 ;
		jobDataMap.put(SeConstance.SESSIONTIME , seTime ) ;
		
		
		//////////////////////////
		//����������ȹ�������,�����������Ƕ�ʱ������ڵĻỰ
		new ACSchedularJob().addSecondlyJob(
				System.nanoTime() + "job",
				System.nanoTime() + "group",
				SessionManager.class , 
				jobDataMap ,
				SeConstance.DELAYCONSTE , 
				SeConstance.INTERVAL ,
				-1) ;
		
		
		System.out.println("�����¼����(" + thisServerId + ")�Ѿ�����");
	}
	
	/**
	 * ����
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
	 * ��ʼ��log��־
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
			System.out.println("������������־ϵͳ��ʼ�����");
			return true;
		} catch (Exception e) {
			System.out.println("ͨ�ŷ���������ʱ����ʼ��log4j��־���� !");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

}
