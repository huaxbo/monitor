package com.automic.sso.commu.udpServer.server;

import org.apache.log4j.Logger;

import com.automic.common.threadPool.ACThreadPool;
import com.automic.common.sso.commu.udpServer.UdpData;

public class DataReceiver {
	private static Logger log = Logger.getLogger(DataReceiver.class.getName()) ;
	/**
	 * 处理接收到的数据
	 * 这个由底层UdpReceiveManager对象调用，上层或二次开发用不着此方法
	 * @param d
	 * @throws Exception
	 */
	public void receiveData(UdpData data) {
		UdpServerContext ctx = UdpServerContext.singleInstance();
		ACThreadPool pool = (ACThreadPool)ctx.getObject(UdpServerConstance.UDPTHREADPOOL) ;
		//进行处理
		try{
			pool.SetThreadJob(new ThreadJob(data));
		}catch(Exception e){
			log.error(e.getMessage() , e) ;
		}
	}

}
