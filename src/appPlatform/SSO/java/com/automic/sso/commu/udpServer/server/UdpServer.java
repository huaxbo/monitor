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
	 * 初始化方法
	 * @param thisServerId 本服务的ID
	 * @param associateServerIds ,//相关联的子服务ID集合
	 * @param configPath 的配置文件路径
	 * @throws ACException
	 */
	public void init(String configPath) throws Exception {

		UdpServerContext ctx = UdpServerContext.singleInstance();
		//注册子服务配置集合
		ctx.setObject(UdpServerConstance.UDPCONFIGS , new HashMap<String , String>()) ;

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

		UdpServerContext ctx = UdpServerContext.singleInstance();

		//得到子服务配置集合
		HashMap<String, String> configs = (HashMap<String, String>) ctx
		.getObject(UdpServerConstance.UDPCONFIGS);
		//得到线程池的配置
		int maxThreadNum = Integer.parseInt((String)configs.get(UdpServerConstance.UDPMAXTHREADNUM)) ;
		int minThreadNum = Integer.parseInt((String)configs.get(UdpServerConstance.UDPMINTHREADNUM)) ;
		long freeTimeout = Long.parseLong((String)configs.get(UdpServerConstance.UDPFREETIMEOUT)) ;
		long busyTimeout = Long.parseLong((String)configs.get(UdpServerConstance.UDPBUSYTIMEOUT)) ;
		//启动并注册线程池
		ctx.setObject(UdpServerConstance.UDPTHREADPOOL,
				new ACThreadPoolSupport().newThreadPool(thisServerId,
						maxThreadNum, minThreadNum, freeTimeout, busyTimeout));

		//启动并注册发送数据UDP socket
		int sendPort = Integer.parseInt((String)configs.get(UdpServerConstance.UDPSENDPORT)) ;
		int sendTimeOut = Integer.parseInt((String)configs.get(UdpServerConstance.UDPSENDTIMEOUT)) ;
		UdpSendManager.initOnlyOnce(sendPort, sendTimeOut) ;
		UdpSendManager sender = UdpSendManager.singleInstance() ;
		sender.start() ;
		ctx.setObject(UdpServerConstance.UDPSENDSOCKETMANAGER,
				sender);

		
		//启动并注册接收数据UDP socket
		int receivePort = Integer.parseInt((String)configs.get(UdpServerConstance.UDPRECEIVEPORT)) ;
		int receiveTimeOut = Integer.parseInt((String)configs.get(UdpServerConstance.UDPRECEIVETIMEOUT)) ;
		int receiveDataLen = Integer.parseInt((String)configs.get(UdpServerConstance.UDPRECEIVEDATALEN)) ;
		UdpReceiveManager.initOnlyOnce(receivePort, receiveTimeOut , receiveDataLen) ;
		UdpReceiveManager receiver = UdpReceiveManager.singleInstance() ;
		receiver.start() ;
		ctx.setObject(UdpServerConstance.UDPRECEIVESOCKETMANAGER,
				receiver);


		System.out.println("单点登录服务:发送数据(UDP)端口号:" + sendPort) ;
		System.out.println("单点登录服务:接收数据(UDP)端口号:" + receivePort) ;
		
		
		System.out.println("单点登录网络通信服务(" + thisServerId + ")已经启动");
	}
	
	/**
	 * 测试
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
	 * 初始化log日志
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
