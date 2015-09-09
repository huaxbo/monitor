package com.automic.sso.session.server;

import java.util.HashMap;


public class SeServerContext {
	private static HashMap<String, Object> objects ;
	
	/**
	 * 单一实例
	 */
	private static SeServerContext instance ;
	
	private SeServerContext(){
		objects = new HashMap<String, Object>() ;
	}
	/**
	 * 得到对象的单一实例
	 * @return 对象的单一实例
	 */
	public static SeServerContext singleInstance(){
		if(instance == null){
			instance = new SeServerContext() ;
		}
		return instance ;
	}
	
	/**
	 * 在上下文中查找对象实例
	 * @param key 注册在上下文中的实例key
	 * @return 返回注册在上下文中的实例，如果查找不到，返回null。
	 */
	public Object getObject(String key) {
		return objects.get(key);
	}
	/**
	 * 在上下文中注册对象实例。如果注册的对象实例名称与已经在上下文中
	 * 注册的对象实例重名，将抛出异常。
	 * @param key 注册的对象实例key ;
	 * @param object 注册的对象实例；
	 * @throws ACException 如果注册的对象实例名称与已经在上下文中
	 * 注册的对象实例重名，将抛出的异常.
	 */
	public void setObject(String key, Object object) throws Exception {
		if(objects.containsKey(key)){
			throw new Exception("在上下文中注册对象实例失败，对象实例名称" + key+"在上下文中已经有注册!") ;
		}
		objects.put(key, object) ;
	}
	
	/**
	 * 替换上下文中对象实例
	 */
	public void replaceObject(String key, Object object) {
		objects.put(key, object) ;
	}

}
