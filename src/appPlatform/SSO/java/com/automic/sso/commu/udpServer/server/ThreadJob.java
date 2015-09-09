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
			//o:�������ݵĽ��
			//o==null:������û����Ҫ���صĽ�� , ����ˢ��session������
			return ;
		}
		if(data.returnIp == null || data.returnPort == null ){
			throw new ACException("UDP������յ����ݲ�������ȱ��returnIp��returnPort") ;
		}
		//id����
		//data.id = data.id ;
		
		//ʱ������������ݳ�ʱ����
		if(data.timeStamp == null){
			data.timeStamp = System.currentTimeMillis() ;
		}
		
//		String tempIp = data.receiveIp ;
//		Integer tempPort = data.receivePort ;
		data.receiveIp = data.returnIp ;
		data.receivePort = data.returnPort ;
		//����Ҫ�ظ��ˣ���������
//		data.returnIp = tempIp ;
//		data.returnPort = tempPort ;
		data.obj = o ;
		
		new DataSender().sendData(data) ;
	}
	
}
