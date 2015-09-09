package com.hxb.commu.remote.protocol.ver_test;

public class VerTestConstant {

	public final static byte HEAD_1 = 0x7E;
	public final static byte HEAD_2 = 0x7E;
	
	public final static int HEAD_LEN = 2;//����ͷ����
	public final static int METER_LEN = 5;//�豸��ų���
	public final static int CODE_LEN = 1;//�����볤��
	
	public final static int CODE_SITE = HEAD_LEN + METER_LEN;//��������ʼ����
	public final static int DATA_SITE = HEAD_LEN + METER_LEN + CODE_LEN;//������ʼ����
	
}
