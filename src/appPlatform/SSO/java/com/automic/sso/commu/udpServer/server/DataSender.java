package com.automic.sso.commu.udpServer.server;

import org.apache.log4j.Logger;

import com.automic.ACException;
import com.automic.common.sso.commu.udpServer.UdpData;
import com.automic.sso.commu.udpServer.socket.UdpSendManager;

public class DataSender {
	private static Logger log = Logger.getLogger(DataSender.class.getName()) ;
	/**
	 * ����������
	 * ���ϲ����η��Ĳ��ֵ��ã����ͳ�ȥ����
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
