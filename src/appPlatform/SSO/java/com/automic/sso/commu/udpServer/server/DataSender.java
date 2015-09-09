package com.automic.sso.commu.udpServer.server;

import org.apache.log4j.Logger;

import com.automic.ACException;
import com.automic.common.sso.commu.udpServer.UdpData;
import com.automic.sso.commu.udpServer.socket.UdpSendManager;

public class DataSender {
	private static Logger log = Logger.getLogger(DataSender.class.getName()) ;
	/**
	 * 处理发送数据
	 * 由上层或二次发的部分调用，发送出去数据
	 * @param data
	 */
	public void sendData(UdpData data) {
		UdpServerContext ctx = UdpServerContext.singleInstance();
		UdpSendManager sender = (UdpSendManager)ctx.getObject(UdpServerConstance.UDPSENDSOCKETMANAGER) ;
		try{
			sender.setSendObject(data) ;
		}catch(ACException e){
			log.error(e.getMessage() , e) ;
		}
	}

}
