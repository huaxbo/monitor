package com.hxb.common.sso.commu.udpServer;

import java.io.*;

public class UdpData implements Serializable {

	private static final long serialVersionUID = 3883080299246489675L;

	/**
	 * 数据ID，通过这个ID，数据接收处理端在处理完数据后返回的数据中仍要保存这个ID值
	 * 这样发送数据端可以依这个ID值，取得对应的回数.
	 * 原因:数据发送端是多用户系统
	 * 
	 */
	public String id ;
	
	/**
	 * 时间戳，用于清理超时数据
	 */
	public Long timeStamp ;
	
	/**
	 * 接收本数据的IP
	 */
	public String receiveIp ;
	/**
	 * 接收本数据的port
	 */
	public Integer receivePort ;
	/**
	 * 接收反回数据的IP
	 * 如果设置为空，则不需要回复数据
	 */
	public String returnIp ;
	/**
	 * 接收返回数据的port
	 * 如果设置为空，则不需要回复数据
	 */
	public Integer returnPort ;
	
	/**
	 * 数据类型,目前有两种类型，一种是普通对象obj(或null)，一种是字节数组bytes
	 * 由类UdpDataType定义
	 */	
	public String dataType ;//数据类型，为空即默认为普通对象类型
	
	/**
	 * 总字节数组数
	 */
	public Integer byteCount ;//总字节数
	/**
	 * 当前字节数据序号，
	 */
	public Integer byteIndex ;//当前字节数据序号，
	/**
	 * 单个字节数组长度
	 */
	public Integer byteLen ;//字节数组长度
	
	/**
	 * 当前需要处理的数据
	 */
	public Object obj ;
	/**
	 * 需要处理的字符串型的字节数据
	 */
	public String bytes ;
	
	public byte[] byteDatas ;
	
	public void copyBase(UdpData from){
		this.id = from.id ;
		this.timeStamp = from.timeStamp ;
		this.receiveIp = from.receiveIp ;
		this.receivePort = from.receivePort ;
		this.returnIp = from.returnIp ;
		this.returnPort = from.returnPort ;
		this.dataType = from.dataType ;
		this.byteCount = from.byteCount ;
		this.byteLen = from.byteLen ;
		this.byteIndex = from.byteIndex ;
	}
}
