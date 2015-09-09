package com.automic.sso.commu.udpServer.socket;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import org.apache.log4j.Logger;
import com.automic.ACException;
import com.automic.common.sso.commu.udpServer.UdpData;
import com.automic.sso.commu.udpServer.server.DataReceiver;

public class UdpReceiveManager extends Thread {

	private static UdpReceiveManager instance;
	private static UdpReceiveSocket socket;
	private static Logger log = Logger.getLogger(UdpReceiveManager.class
			.getName());

	
	private UdpReceiveManager(int port, int timeOut, int dataLen)
			throws ACException {
		socket = new UdpReceiveSocket(port, timeOut, dataLen);
	}

	/**
	 * 只进行一次初始化
	 * @return
	 * @throws ACException
	 */
	public static void initOnlyOnce(int port, int timeOut, int dataLen)
			throws ACException {
		if (instance == null) {
			instance = new UdpReceiveManager(port, timeOut, dataLen);
		}
	}

	/**
	 * 得到唯一实例
	 * @return
	 * @throws ACException
	 */
	public static UdpReceiveManager singleInstance() throws ACException {
		if (instance == null) {
			throw new ACException("请首先初始化" + UdpReceiveManager.class.getName()
					+ "类实例！");
		}
		return instance;
	}

	
	/**
	 * 接收数据
	 */
	public void run() {
		byte[] datas = null ;
		UdpData data = null;
		boolean hasException = false ;
		while (true) {
			if(socket == null){
				log.info("等待创建UDP receive socket") ;
				try{
					sleep(1000) ;
				}catch(Exception e){
					;
				}
				continue ;
			}
			datas = socket.receive();
			if (datas == null || datas.length == 0) {
				//接收超时或者发生其他异常
				continue;
			}
			
//			System.out.println((new String(datas)).trim()) ;
			
			try {
				ObjectInputStream objIn = new ObjectInputStream(
						new DataInputStream(new ByteArrayInputStream(datas)));
				data = (UdpData) objIn.readObject();
			} catch (Exception e) {
				hasException = true ;
				log.error("从接收的数据中生成对象发生异常!" , e) ;
			}
			if(hasException){
				hasException = false ;
			}else{
				datas = null ;
				new DataReceiver().receiveData(data) ;
			}
		}
	}
}
