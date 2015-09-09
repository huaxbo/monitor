package com.hxb.common.sso.session;

/**
 * 注: 上行命令都是1开头
 *     下行命令都是2开头
 *     上行或下行都可能有以9开头
 * @author Administrator
 *
 */
public class SsoOrder {
	/**
	 * 子系统检查发现是有固定IP的用户，可以直接登录
	 * 命令中数据要求：数据是cookie中的"userName;password;ip"
	 * 命令处理后动作要求：SSO进行登录，登录成功则返回用户对象，如果不成功，返回空
	 */
	public static final Integer directLogin = 1000 ;
	/**
	 * 子系统检查当前请求客户端是否在登录状态
	 * 命令中数据要求：数据是cookie中的sessionId值
	 * 命令处理后动作要求：如果已经登录，则返回用户对象，如果未登录，返回空
	 */
	public static final Integer checkLogin = 1001 ;
	
	/**
	 * 刷新会话
	 * 命令中数据要求：数据是cookie中的sessionId值
	 * 命令处理后动作要求：禁止返回数据
	 */
	public static final Integer freshSession = 1002 ;
	
	/**
	 * 当前请求客户端已经处于登录成功状态
	 * 命令中数据要求：用户会话中的数据
	 * 命令处理后动作要求：禁止返回数据
	 */
	public static final Integer logined = 2001 ;
	/**
	 * 当前请求客户端未登录或会话过期
	 * 命令中数据要求：数据为空
	 * 命令处理后动作要求：禁止返回数据
	 */
	public static final Integer noLogin = 2002 ;
	/**
	 * 退出登录
	 * 命令中数据要求：数据是cookie中的sessionId值
	 * 命令处理后动作要求：禁止返回数据
	 */
	public static final Integer logout = 2003 ;
	
	
	/**
	 * 出错了
	 * 命令中数据要求：数据为出错消息
	 * 命令处理后动作要求：禁止返回数据
	 */
	public static final Integer error = 9001 ;
	
}
