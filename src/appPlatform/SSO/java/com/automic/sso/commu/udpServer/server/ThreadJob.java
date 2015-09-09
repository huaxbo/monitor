package com.automic.sso.commu.udpServer.server;

import com.automic.ACException;
import com.automic.common.threadPool.ACThreadJob;
import com.automic.common.sso.commu.udpServer.UdpData;
import com.automic.sso.session.server.DealSso;

public class ThreadJob implements ACThreadJob{

	private UdpData data ;
	
	public ThreadJob(UdpData data){
		this.data = data ;
	}
	
	public void execute() throws Exception {
		DealSso ds = new DealSso() ;
		Object o = ds.dealReceiveData(data.obj) ;
		if(o == null){
			//o:处理数据的结果
			//o==null:此命令没有需要返回的结果 , 例如刷新session的命令
			return ;
		}
		if(data.returnIp == null || data.returnPort == null ){
			throw new ACException("UDP网络接收的数据不完整！缺少returnIp和returnPort") ;
		}
		//id不变
		//data.id = data.id ;
		
		//时间戳，用于数据超时计算
		if(data.timeStamp == null){
			data.timeStamp = System.currentTimeMillis() ;
		}
		
//		String tempIp = data.receiveIp ;
//		Integer tempPort = data.receivePort ;
		data.receiveIp = data.returnIp ;
		data.receivePort = data.returnPort ;
		//不需要回复了，不用设置
//		data.returnIp = tempIp ;
//		data.returnPort = tempPort ;
		data.obj = o ;
		
		new DataSender().sendData(data) ;
	}
	
}
