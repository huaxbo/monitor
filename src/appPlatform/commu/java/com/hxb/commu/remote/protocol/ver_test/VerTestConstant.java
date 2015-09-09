package com.hxb.commu.remote.protocol.ver_test;

public class VerTestConstant {

	public final static byte HEAD_1 = 0x7E;
	public final static byte HEAD_2 = 0x7E;
	
	public final static int HEAD_LEN = 2;//报文头长度
	public final static int METER_LEN = 5;//设备编号长度
	public final static int CODE_LEN = 1;//功能码长度
	
	public final static int CODE_SITE = HEAD_LEN + METER_LEN;//功能码起始索引
	public final static int DATA_SITE = HEAD_LEN + METER_LEN + CODE_LEN;//数据起始索引
	
}
