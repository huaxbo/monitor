package com.hxb.common.sso.commu.udpServer;

import java.io.*;

public class UdpData implements Serializable {

	private static final long serialVersionUID = 3883080299246489675L;

	/**
	 * ����ID��ͨ�����ID�����ݽ��մ�����ڴ��������ݺ󷵻ص���������Ҫ�������IDֵ
	 * �����������ݶ˿��������IDֵ��ȡ�ö�Ӧ�Ļ���.
	 * ԭ��:���ݷ��Ͷ��Ƕ��û�ϵͳ
	 * 
	 */
	public String id ;
	
	/**
	 * ʱ�������������ʱ����
	 */
	public Long timeStamp ;
	
	/**
	 * ���ձ����ݵ�IP
	 */
	public String receiveIp ;
	/**
	 * ���ձ����ݵ�port
	 */
	public Integer receivePort ;
	/**
	 * ���շ������ݵ�IP
	 * �������Ϊ�գ�����Ҫ�ظ�����
	 */
	public String returnIp ;
	/**
	 * ���շ������ݵ�port
	 * �������Ϊ�գ�����Ҫ�ظ�����
	 */
	public Integer returnPort ;
	
	/**
	 * ��������,Ŀǰ���������ͣ�һ������ͨ����obj(��null)��һ�����ֽ�����bytes
	 * ����UdpDataType����
	 */	
	public String dataType ;//�������ͣ�Ϊ�ռ�Ĭ��Ϊ��ͨ��������
	
	/**
	 * ���ֽ�������
	 */
	public Integer byteCount ;//���ֽ���
	/**
	 * ��ǰ�ֽ�������ţ�
	 */
	public Integer byteIndex ;//��ǰ�ֽ�������ţ�
	/**
	 * �����ֽ����鳤��
	 */
	public Integer byteLen ;//�ֽ����鳤��
	
	/**
	 * ��ǰ��Ҫ���������
	 */
	public Object obj ;
	/**
	 * ��Ҫ������ַ����͵��ֽ�����
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
