package com.automic.sso.session.server;

import java.util.HashMap;


public class SeServerContext {
	private static HashMap<String, Object> objects ;
	
	/**
	 * ��һʵ��
	 */
	private static SeServerContext instance ;
	
	private SeServerContext(){
		objects = new HashMap<String, Object>() ;
	}
	/**
	 * �õ�����ĵ�һʵ��
	 * @return ����ĵ�һʵ��
	 */
	public static SeServerContext singleInstance(){
		if(instance == null){
			instance = new SeServerContext() ;
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
