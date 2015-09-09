package com.automic.sso.session.session;

import java.util.*;

public class SessionCach {
	
	private static Object synObj = new Object() ;

	private static Hashtable<String , Session> cach = new Hashtable<String , Session>() ;
	
	private SessionCach(){} 
	
	/**
	 * ͨ��ID�����Ự����
	 * @param sessionId
	 * @return
	 */
	public static Session getSession(String sessionId){
		synchronized(synObj){
			return cach.get(sessionId) ;
		}
	}
	/**
	 * ����Ự����
	 * @param sessionId
	 * @param se
	 */
	public static void putSession(String sessionId , Session se){
		synchronized(synObj){
			cach.put(sessionId , se) ;
		}
	}
	
	/**
	 * ˢ�»Ự
	 * @param sessionId
	 */
	public static void freshSession(String sessionId){
		synchronized(synObj){
			Session se = cach.get(sessionId) ;
			if(se != null){
				se.stamp = System.currentTimeMillis() ;
			}
		}
	}
	
	/**
	 * �˳�������Ự�������
	 * @param sessionId
	 */
	public static void logout(String sessionId){
		synchronized(synObj){
			Session se = cach.get(sessionId) ;
			if(se != null){
				cach.remove(sessionId) ;
			}
		}
	}
	
	/**
	 * ������ڻỰ
	 */
	public static void cleanSession(long time){
		synchronized(synObj){
			Set<Map.Entry<String, Session>> entrys = cach.entrySet();
			Iterator<Map.Entry<String, Session>> it = entrys.iterator() ;
			Map.Entry<String, Session> entry = null ;
			Session se = null ;
			long now = System.currentTimeMillis() ;
			while(it.hasNext()){
				entry = it.next() ;
				se = entry.getValue() ;
				if(now - se.stamp > time){
					//�Ự����
					it.remove() ;
				}
			}
		}
	}
}
