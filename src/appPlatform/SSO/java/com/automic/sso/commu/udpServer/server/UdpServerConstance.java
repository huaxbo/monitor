package com.automic.sso.commu.udpServer.server;

public class UdpServerConstance {
	
	
	/**
	 *指向心配置集合
	 */
	public static final String UDPTHREADPOOL = "UDP000001" ;
	
	/**
	 *指向发送socket管理器
	 */
	public static final String UDPSENDSOCKETMANAGER = "UDP000002" ;
	
	/**
	 *指向接收socket管理器
	 */
	public static final String UDPRECEIVESOCKETMANAGER = "UDP000003" ;
	
	
	/**
	 *指向心配置集合
	 */
	public static final String UDPCONFIGS = "UDPConfigs" ;


	/**
	 * 发送数据UDP SOCKET端口号
	 */
	public static final String UDPSENDPORT = "sendPort" ;
	
	/**
	 *  接收数据UDP SOCKET端口号
	 */
	public static final String UDPRECEIVEPORT = "receivePort" ;
	/**
	 *  发送数据超时时长
	 */
	public static final String UDPSENDTIMEOUT = "sendTimeOut" ;
	/**
	 *  接收数据超时时长
	 */
	public static final String UDPRECEIVETIMEOUT = "receiveTimeOut" ;
	/**
	 *  接收数据字节长
	 */
	public static final String UDPRECEIVEDATALEN = "receiveDataLen" ;

	/**
	 * 指向心线程池最大线程数
	 */
	public static final String UDPMAXTHREADNUM = "maxThreadNum" ;
	/**
	 * 指向心线程池最小线程数
	 */
	public static final String UDPMINTHREADNUM = "minThreadNum" ;
	/**
	 * 指向心线程池线程最长空闲时长
	 */
	public static final String UDPFREETIMEOUT = "freeTimeout" ;
	/**
	 * 指向心线程池线程最长繁忙时长，超过时长，即认为线程崩溃。
	 */
	public static final String UDPBUSYTIMEOUT = "busyTimeout" ;
	
	

}
