package com.hxb.global.wsClient.imp;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hxb.global.wsClient.WsDataSender;
import com.hxb.global.wsClient.wsClient.WsClient;

public class WsDataSenderImp implements WsDataSender {

	private static ArrayList<Object[]> datas = new ArrayList<Object[]>(0);
	private static Logger log = Logger.getLogger(WsDataSenderImp.class);
	static {
		new WsDataSenderImp().new DataHandlerThread().start();
	}

	@Override
	public void sendData(String command, StringBuffer data) {
		// TODO Auto-generated method stub
		pushData(command,data);
	}

	/**
	 * push data to wait list
	 * 
	 * @param command
	 * @param data
	 */
	public void pushData(String command, StringBuffer data) {
		synchronized (datas) {
			datas.add(new Object[] { command, data });
			datas.notifyAll();
		}
	}

	/**
	 * pop data from wait list
	 * 
	 * @return
	 */
	public synchronized Object[] popData() {
		Object[] otmp = null;
		synchronized (datas) {
			if (datas.size() > 0) {
				otmp = datas.remove(0);
			}
		}
		return otmp;
	}

	/**
	 * ʵ������Զ���ϱ�����
	 * 
	 * @param command
	 * @param data
	 */
	private static String dataHandler(String command, StringBuffer data) {
		log.info("\n################\n" + command + "\n" + data.toString() + "\n################\n");
		return new WsClient().webServiceRequest(command, data) ;
	}
	/**
	 * �����������߳� 
	 */
	public class DataHandlerThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			/**
			 * �����Ŷ�����
			 */
			while (true) {
				try {
					Object[] oo = popData();
					if (oo == null) {
						synchronized(datas){
							datas.wait();
						}
					} else {
						dataHandler((String) oo[0], (StringBuffer) oo[1]);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					;
				}
			}
		}
	}
}
