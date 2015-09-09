package com.hxb.common.secure.sso.help;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class WaitLoginCach extends Thread {
	private static Object synObj = new Object() ;
	
	private static long time = 60*1000 ;//60��

	private static Hashtable<String , WaitLoginData> cach = new Hashtable<String , WaitLoginData>() ;
	
	public WaitLoginCach(){} 
	
	/**
	 * ���������Ч�ȴ�ʱ��
	 * @param time
	 * @return
	 */
	public static void setMaxWaitLoginTime(long time){
		WaitLoginCach.time = time ;
	}
	/**
	 * ͨ��ID�õ��ȴ�����
	 * @param WaitLoginDataId
	 * @return
	 */
	public static WaitLoginData getWaitLoginData(String dataId){
		synchronized(synObj){
			WaitLoginData d = cach.get(dataId) ;
			cach.remove(dataId) ;
			return d ;
		}
	}
	/**
	 * ����ȴ�����
	 * @param WaitLoginDataId
	 * @param se
	 */
	public static void putWaitLoginData(String dataId , WaitLoginData data){
		synchronized(synObj){
			cach.put(dataId , data) ;
		}
	}
		
	/**
	 * ������ڵȴ�����
	 */
	private static void cleanWaitLoginData(){
		synchronized(synObj){
			Set<Map.Entry<String, WaitLoginData>> entrys = cach.entrySet();
			Iterator<Map.Entry<String, WaitLoginData>> it = entrys.iterator() ;
			Map.Entry<String, WaitLoginData> entry = null ;
			WaitLoginData d = null ;
			long now = System.currentTimeMillis() ;
			while(it.hasNext()){
				entry = it.next() ;
				d = entry.getValue() ;
				if(now - d.stamp > time){
					//�Ự����
					it.remove() ;
				}
			}
		}
	}
	
	/**
	 * ������ڵȴ�����
	 */
	public void run() {
		while(true){
			try{
				sleep(5000) ;
			}catch(Exception e){}
			WaitLoginCach.cleanWaitLoginData() ;
		}
	}

}
