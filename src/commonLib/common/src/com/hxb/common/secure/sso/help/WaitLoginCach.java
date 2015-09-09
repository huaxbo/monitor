package com.hxb.common.secure.sso.help;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class WaitLoginCach extends Thread {
	private static Object synObj = new Object() ;
	
	private static long time = 60*1000 ;//60秒

	private static Hashtable<String , WaitLoginData> cach = new Hashtable<String , WaitLoginData>() ;
	
	public WaitLoginCach(){} 
	
	/**
	 * 设置最大有效等待时长
	 * @param time
	 * @return
	 */
	public static void setMaxWaitLoginTime(long time){
		WaitLoginCach.time = time ;
	}
	/**
	 * 通过ID得到等待数据
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
	 * 存入等待数据
	 * @param WaitLoginDataId
	 * @param se
	 */
	public static void putWaitLoginData(String dataId , WaitLoginData data){
		synchronized(synObj){
			cach.put(dataId , data) ;
		}
	}
		
	/**
	 * 清除过期等待数据
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
					//会话过期
					it.remove() ;
				}
			}
		}
	}
	
	/**
	 * 清除过期等待数据
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
