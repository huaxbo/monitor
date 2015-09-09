package com.hxb.monitor.util;

public class MonitorConstant {

	
	/**
	 * 远程命令操作：命令结果回复等待时长，单位:s
	 */
	public static int max_wait_cmdResult = 30;
	
	/**
	 * 远程命令操作：命令结果临时表扫描间隔，单位：s
	 */
	public static int max_scan_cmdResult = 60;
	
	/**
	 * 远程命令操作：命令结果重复扫描次数
	 */
	public static int times_scan_cmdResult = 5;
	
	
	/**
	 * 主动上报操作：上报结果临时表扫描间隔，单位：s
	 */
	public static int max_scan_autoResult = 10;
}
