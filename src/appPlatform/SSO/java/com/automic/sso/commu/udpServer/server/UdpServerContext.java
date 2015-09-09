package com.automic.sso.commu.udpServer.server;

import java.util.HashMap;

public class UdpServerContext {
	private static HashMap<String, Object> objects ;
	
	/**
	 * ��һʵ��
	 */
	private static UdpServerContext instance ;
	
	private UdpServerContext(){
		objects = new HashMap<String, Object>() ;
	}
	/**
	 * �õ�����ĵ�һʵ��
	 * @return ����ĵ�һʵ��
	 */
	public static UdpServerContext singleInstance(){
		if(instance == null){
			instance = new UdpServerContext() ;
		}
		return instance ;
	}
	
	/**
	 * ���������в��Ҷ���ʵ��
	 * @param key ע�����������е�ʵ��key
	 * @return ����ע�����������е�ʵ����������Ҳ���������null��
	 */
	public Object getObject(String key) {
		return objects.get(key);
	}
	/**
	 * ����������ע�����ʵ�������ע��Ķ���ʵ���������Ѿ�����������
	 * ע��Ķ���ʵ�����������׳��쳣��
	 * @param key ע��Ķ���ʵ��key ;
	 * @param object ע��Ķ���ʵ����
	 * @throws ACException ���ע��Ķ���ʵ���������Ѿ�����������
	 * ע��Ķ���ʵ�����������׳����쳣.
	 */
	public void setObject(String key, Object object) throws Exception {
		if(objects.containsKey(key)){
			throw new Exception("����������ע�����ʵ��ʧ�ܣ�����ʵ������" + key+"�����������Ѿ���ע��!") ;
		}
		objects.put(key, object) ;
	}
	
	/**
	 * �滻�������ж���ʵ��
	 */
	public void replaceObject(String key, Object object) {
		objects.put(key, object) ;
	}

}
