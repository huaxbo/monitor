package com.automic.sso.commu.udpServer.server;

import java.util.*;
import org.apache.log4j.*;
import com.automic.sso.commu.udpServer.config.Config;
import com.automic.common.threadPool.*;
import com.automic.sso.commu.udpServer.socket.* ;
import java.net.URL;

public class UdpServer {
	
	private static String thisServerId = UdpServer.class.getSimpleName() ;
	
//	private static Logger log = Logger.getLogger(UdpServer.class.getName()) ;
	
	/**
	 * ��ʼ������
	 * @param thisServerId �������ID
	 * @param associateServerIds ,//��������ӷ���ID����
	 * @param configPath �������ļ�·��
	 * @throws ACException
	 */
	public void init(String configPath) throws Exception {

		UdpServerContext ctx = UdpServerContext.singleInstance();
		//ע���ӷ������ü���
		ctx.setObject(UdpServerConstance.UDPCONFIGS , new HashMap<String , String>()) ;

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

		UdpServerContext ctx = UdpServerContext.singleInstance();

		//�õ��ӷ������ü���
		HashMap<String, String> configs = (HashMap<String, String>) ctx
		.getObject(UdpServerConstance.UDPCONFIGS);
		//�õ��̳߳ص�����
		int maxThreadNum = Integer.parseInt((String)configs.get(UdpServerConstance.UDPMAXTHREADNUM)) ;
		int minThreadNum = Integer.parseInt((String)configs.get(UdpServerConstance.UDPMINTHREADNUM)) ;
		long freeTimeout = Long.parseLong((String)configs.get(UdpServerConstance.UDPFREETIMEOUT)) ;
		long busyTimeout = Long.parseLong((String)configs.get(UdpServerConstance.UDPBUSYTIMEOUT)) ;
		//������ע���̳߳�
		ctx.setObject(UdpServerConstance.UDPTHREADPOOL,
				new ACThreadPoolSupport().newThreadPool(thisServerId,
						maxThreadNum, minThreadNum, freeTimeout, busyTimeout));

		//������ע�ᷢ������UDP socket
		int sendPort = Integer.parseInt((String)configs.get(UdpServerConstance.UDPSENDPORT)) ;
		int sendTimeOut = Integer.parseInt((String)configs.get(UdpServerConstance.UDPSENDTIMEOUT)) ;
		UdpSendManager.initOnlyOnce(sendPort, sendTimeOut) ;
		UdpSendManager sender = UdpSendManager.singleInstance() ;
		sender.start() ;
		ctx.setObject(UdpServerConstance.UDPSENDSOCKETMANAGER,
				sender);

		
		//������ע���������UDP socket
		int receivePort = Integer.parseInt((String)configs.get(UdpServerConstance.UDPRECEIVEPORT)) ;
		int receiveTimeOut = Integer.parseInt((String)configs.get(UdpServerConstance.UDPRECEIVETIMEOUT)) ;
		int receiveDataLen = Integer.parseInt((String)configs.get(UdpServerConstance.UDPRECEIVEDATALEN)) ;
		UdpReceiveManager.initOnlyOnce(receivePort, receiveTimeOut , receiveDataLen) ;
		UdpReceiveManager receiver = UdpReceiveManager.singleInstance() ;
		receiver.start() ;
		ctx.setObject(UdpServerConstance.UDPRECEIVESOCKETMANAGER,
				receiver);


		System.out.println("�����¼����:��������(UDP)�˿ں�:" + sendPort) ;
		System.out.println("�����¼����:��������(UDP)�˿ں�:" + receivePort) ;
		
		
		System.out.println("�����¼����ͨ�ŷ���(" + thisServerId + ")�Ѿ�����");
	}
	
	/**
	 * ����
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		UdpServer o = new UdpServer() ;
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
			configFileURL = UdpServer.class.getResource("/log4j.properties");
			if(configFileURL == null){
				configFileURL = UdpServer.class.getResource("/config/log4j.properties");
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
