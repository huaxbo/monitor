package com.automic.sso.commu.udpServer.server;

import org.apache.log4j.Logger;

import com.automic.common.threadPool.ACThreadPool;
import com.automic.common.sso.commu.udpServer.UdpData;

public class DataReceiver {
	private static Logger log = Logger.getLogger(DataReceiver.class.getName()) ;
	/**
	 * ������յ�������
	 * ����ɵײ�UdpReceiveManager������ã��ϲ����ο����ò��Ŵ˷���
	 * @param d
	 * @throws Exception
	 */
	public void receiveData(UdpData data) {
		UdpServerContext ctx = UdpServerContext.singleInstance();
		ACThreadPool pool = (ACThreadPool)ctx.getObject(UdpServerConstance.UDPTHREADPOOL) ;
		//���д���
		try{
			pool.SetThreadJob(new ThreadJob(data));
		}catch(Exception e){
			log.error(e.getMessage() , e) ;
		}
	}

}
