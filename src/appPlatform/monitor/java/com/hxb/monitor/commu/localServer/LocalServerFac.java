package com.hxb.monitor.commu.localServer;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.codehaus.xfire.client.Client;

public class LocalServerFac {

	private static Logger log = Logger.getLogger(LocalServerFac.class);
	
	
	/**
	 * @param svo
	 */
	public static void build(String cid,LocalServerVO svo){
		if(svo != null){
			build(cid,svo.getIp(),svo.getPort(),svo.getContext());
		}
	}
	/**
	 * 构建客户端
	 * @param ip
	 * @param port
	 * @param context
	 * @return
	 */
	public static void build(String id,String ip,Integer port,String context){
		if(ip != null && port != null && context != null){
			try {
				Client client =new Client(new URL("http://" + ip + ":" + port 
						+ "/" + context + "/services/commuLocalServer?wsdl"));				
				if(client != null){
					LocalServerManager.addClient(id, client);
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				log.error("构建本地通讯服务[" + ip + ":" + port + "/" + context + "]webService客户端失败！");
				log.error(e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("构建本地通讯服务[" + ip + ":" + port + "/" + context + "]webService客户端失败！");
				log.error(e);
			}finally{}
		}
		
	}
}

