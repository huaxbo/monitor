package com.hxb.commu.util;

public class CommuConstant {


	/**
	 * 远程连接维护：连续命令失败断开连接：：连续失败命令次数。
	 * -1：表示不进行此操作
	 */
	public static int continueFailCmds_times = 2;
	/**
	 * 远程连接维护：连接无数据接收间隔，超过间隔断开连接。单位：分钟
	 * -1：表示进行此操作
	 */
	public static int noneReceive_interval = 5;
	
	
	/**
	 * 本地命令回执状态：成功
	 */
	public static String receipt_succ = "receicpt_succ";
	/**
	 * 本地命令回执状态：失败
	 */
	public static String receipt_fail = "receipt_fail";
	

	/**
	 * TCP远程配置：最大处理任务数
	 */
	public static int remote_processors = 3;
	/**
	 * TCP远程参数配置：远程连接空闲时长，单位：s
	 */
	public static int remote_idle = 10;
	/**
	 * TCP远程参数配置：远程连接日志输出控制
	 */
	public static boolean remote_logs = false;
	/**
	 * TCP远程参数配置：远程服务端口
	 */
	public static int remote_port = 9014;
	/**
	 * TCP远程参数配置：远程连接维护轮训间隔，单位：m
	 */
	public static int remote_sessionMgrInteval = 1;
	
	/**
	 * 远程命令处理：单个命令最大失败次数
	 */
	public static int command_maxFails = 3;
	/**
	 * 远程命令处理：每次命令下发过程中等待间隔，单位：s
	 */
	public static int command_perInterval = 10;
	/**
	 * 远程命令处理：命令队列扫描间隔，单位：s
	 */
	public static int command_scanInterval = 10;
	
}
