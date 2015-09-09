package com.hxb.monitor.commu.localServer;

import java.util.Hashtable;

import org.codehaus.xfire.client.Client;

import com.hxb.commu.local.common.CommandAttach;
import com.hxb.global.util.SerializeUtil;

public class LocalServerManager {

	/**
	 * client集合
	 */
	private static Hashtable<String,Client> clients = new Hashtable<String,Client>(0);
	private static Hashtable<String,LocalServerVO> servers = new Hashtable<String,LocalServerVO>(0);
	
	/**
	 * 添加client，cid作为键值
	 * @param client
	 */
	public static void addClient(String cid,Client client){
		
		clients.put(cid,client);
	}
	/**
	 * @param cid
	 * @param ip
	 * @param port
	 * @param context
	 */
	public static void addServer(String cid,String ip,Integer port,String context){
		if(!servers.containsKey(cid)){
			servers.put(cid, new LocalServerVO(ip,port,context));
		}
	}
	/**
	 * 根据id键值获取client
	 * @param cid
	 * @return
	 */
	private static Client getClient(String cid){
		if(cid == null){
			
			return null;
		}
		Client client = clients.get(cid);
		if(client == null){
			LocalServerFac.build(cid, servers.get(cid));
			client = clients.get(cid);
		}
		
		return client;
	}
	
	/**
	 * @param cid
	 * @param atta
	 * @return
	 * @throws Exception 
	 */
	public static String sendCommand(String cid,CommandAttach atta) throws Exception{
		
		Client c = getClient(cid);	
		
		if(c != null){
			Object[] rlt = (Object[])c.invoke("sendCommand", 
					new Object[]{SerializeUtil.getInstance().obj2Xml(atta, new Class[]{CommandAttach.class})});
			
			if(rlt != null && rlt.length>0){
				return (String)rlt[0];
			}			
		}
		return null;
		
	}
}
