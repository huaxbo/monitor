package com.hxb.commu.local.common;

public class LocalCode {

	private static int idx = 0;
	
	/**
	 * 本地命令：重新加载测控设备列表
	 */
	public static final String local_reloadMeters = "local_" + idx++;
	
	/**
	 * 本地命令：查询通讯服务时钟
	 */
	public static final String local_serverClock = "local_" + idx++;
	
	/**
	 * 查看设备在线列表
	 */
	public static final String local_onLines = "local_" + idx++;
	
	
	
	
}
