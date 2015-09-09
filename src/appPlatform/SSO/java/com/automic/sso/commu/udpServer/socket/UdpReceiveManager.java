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
	 * ֻ����һ�γ�ʼ��
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
	 * �õ�Ψһʵ��
	 * @return
	 * @throws ACException
	 */
	public static UdpReceiveManager singleInstance() throws ACException {
		if (instance == null) {
			throw new ACException("�����ȳ�ʼ��" + UdpReceiveManager.class.getName()
					+ "��ʵ����");
		}
		return instance;
	}

	
	/**
	 * ��������
	 */
	public void run() {
		byte[] datas = null ;
		UdpData data = null;
		boolean hasException = false ;
		while (true) {
			if(socket == null){
				log.info("�ȴ�����UDP receive socket") ;
				try{
					sleep(1000) ;
				}catch(Exception e){
					;
				}
				continue ;
			}
			datas = socket.receive();
			if (datas == null || datas.length == 0) {
				//���ճ�ʱ���߷��������쳣
				continue;
			}
			
//			System.out.println((new String(datas)).trim()) ;
			
			try {
				ObjectInputStream objIn = new ObjectInputStream(
						new DataInputStream(new ByteArrayInputStream(datas)));
				data = (UdpData) objIn.readObject();
			} catch (Exception e) {
				hasException = true ;
				log.error("�ӽ��յ����������ɶ������쳣!" , e) ;
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
