package com.hxb.global.wsClient.wsClient ;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.codehaus.xfire.client.Client;

public class WsClient {
	
	private static Logger log = Logger.getLogger(WsClient.class) ;
	
	private static final String wsdl= "/config/config.webService.wsdl";
	private static Client client = null ;
	static{
		/*if(client == null){
			try {
				InputStream in = WsClient.class.getResourceAsStream(wsdl) ;
				client = new Client(in, null) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
	}
	/**
	 * 请求webService服务
	 * @param command
	 * @param data
	 * @return
	 */
	public String webServiceRequest(String command , StringBuffer data){
		try {
			if(client != null){
				Object[] results = client.invoke("receive", new Object[]{command , data.toString()}) ;
				return (String)results[0] ;
			}else{
				log.error("系统初始化时，不能生成webService服务客户端！") ;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null ;
	}
	
	
	public static void main(String[] args) throws MalformedURLException, Exception{
		
		Client c = new Client(new URL("http://localhost:8080/commu/services/commuLocalServer?wsdl"));
		c.invoke("sendCommand", new Object[]{null});
		
	}
}
