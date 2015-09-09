package com.hxb.common.secure.sso.socket ;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.util.*;
import java.net.*;
import org.apache.log4j.Logger;

import com.hxb.ACException;
import com.hxb.common.sso.commu.udpServer.*;

public class UdpReceiveManager extends Thread {

	private static final Object synObj = new Object() ;
	
	private static UdpReceiveManager instance ;
	
	//�ȴ������ݵ���ѯ����
	private static final int waitTimes = 10 ;
	//�����ݳ�ʱʱ��(30��)
	private static final long dataTimeOut = 1000*30 ;

	private static UdpReceiveSocket socket;
	private static ArrayList<UdpData> list = new ArrayList<UdpData>() ;

	private static Hashtable<String , Hashtable<String , UdpData>> subDatas = new Hashtable<String , Hashtable<String , UdpData>>() ;
	
	
	private static Logger log = Logger.getLogger(UdpReceiveManager.class
			.getName());

	
	private UdpReceiveManager(){
	}

	/**
	 * ֻ����һ�γ�ʼ��
	 * @return
	 * @throws ACException
	 */
	public static void initAndStartFirst(int port, int timeOut, int dataLen)
			throws SocketException {
			instance = new UdpReceiveManager() ;
			socket = new UdpReceiveSocket(port, timeOut, dataLen);
			instance.start() ;
	}

	/**
	 * �õ�Ψһʵ��
	 * @return
	 * @throws ACException
	 */
	public static UdpReceiveManager newInstance() throws ACException {
		return new UdpReceiveManager();
	}
	
	/**
	 * �õ��������
	 * @param id
	 * @return
	 */
	public UdpData getReturnData(String id , long waitTime){
		UdpData rd = null ;
		UdpData d = null ;
		int count = 0 ;
		boolean flag = true ;
		synchronized(synObj){
			while(flag){
				Iterator<UdpData> it = list.iterator() ;
				while(it.hasNext()){
					d = it.next() ;
					if(d.id != null && d.id.equals(id)){
						rd = d ;
						break ;
					}
				}
				if(rd != null){
					flag = false ;
				}else{
					try{
						synObj.wait(waitTime/waitTimes) ;
					}catch(Exception e){
					}
				}
				count++ ;
				if(count >= waitTimes){
					//�ȴ���ʱ
					flag = false ;
				}
			}
		}
		return rd ;
	}
	/**
	 * �����ʱ����
	 *
	 */
	private void cleanData() {
		UdpData d = null;
		long now = System.currentTimeMillis() ;
		synchronized (synObj) {
			Iterator<UdpData> it = list.iterator();
			while (it.hasNext()) {
				d = it.next();
				if (now - d.timeStamp.longValue() > dataTimeOut) {
					//��ʱ
					it.remove() ;
				}
			}
		}
	}

	/**
	 * ��������
	 */
	@SuppressWarnings("finally")
	public void run() {
		byte[] datas = null ;
		UdpData data = null;
		boolean hasException = false ;
		boolean pageData = false ;
		int count = 0 ;
		while (true) {
			if(socket == null){
				try{
				sleep(1000) ;
				}catch(Exception e){}
				continue ;
			}
			count ++ ;
			if(count >= 10){
				//ǰ���Ѿ�������10������
				//ִ��һ�γ�ʱ��������
				count = 0 ;
				this.cleanData() ;
			}
			datas = socket.receive();
			if (datas == null || datas.length == 0) {
				//���ճ�ʱ���߷��������쳣
				continue;
			}
			try {
				ObjectInputStream objIn = new ObjectInputStream(
						new DataInputStream(new ByteArrayInputStream(datas)));
				data = (UdpData) objIn.readObject();
				if(data.dataType != null && data.dataType.equals(UdpDataType.BYTES)){
					//��װ����
					this.packageData(data) ;
					pageData = true ;
				}
			} catch (Exception e) {
				e.printStackTrace() ;
				hasException = true ;
				log.error("�ӽ��յ����������ɶ������쳣!" , e) ;
			}finally{
				if(hasException){
					hasException = false ;
				}else{
					if(!pageData){
						datas = null ;
						synchronized(synObj){
							list.add(data) ;
							synObj.notifyAll() ;
						}
					}else{
						pageData = false ;
					}
				}
				continue ;
			}
		}
	}
	
	private synchronized void packageData(UdpData d){
		String id = d.id ;
		if(id == null || d.byteCount == null || d.byteIndex == null || d.byteLen == null){
			//���������ݣ���������
			log.error("�ӵ����¼�����յ������ݲ���������������") ;
			return ;
		}
		Hashtable<String , UdpData> set = null ;
		if(subDatas.containsKey(id)){
			set = subDatas.get(id) ;
		}else{
			set = new Hashtable<String , UdpData>() ;
			subDatas.put(id, set) ;
		}
		set.put(d.byteIndex + "", d) ;
		if(set.size() >= d.byteCount.intValue()){
			//������ȫ
			UdpData subd = null ;
			boolean hasError = false ;
			int dataLen = 0 ;
			for(int i = 0 ; i < d.byteCount ; i++){
				subd = set.get(i + "") ;
				if(subd == null){
					//������
					hasError = true ;
					break ;
				}
				dataLen += subd.byteLen.intValue() ;
			}
			if(hasError){
			}else{
				byte[] bytes = new byte[dataLen] ;
				int destPos = 0 ;
				for(int i = 0 ; i < d.byteCount ; i++){
					subd = set.get(i + "") ;
					byte[] subb = subd.byteDatas ;
					//System.arraycopy(Object   src,   int   srcPos,   Object   dest,   int   destPos,   int   length)     
					System.arraycopy(subb, 0, bytes, destPos, subb.length);
					destPos += subd.byteLen ;
				}
				boolean hasException = false ;
				UdpData data = null ;
				try {
					ObjectInputStream objIn = new ObjectInputStream(
							new DataInputStream(new ByteArrayInputStream(bytes)));
					data = (UdpData) objIn.readObject();
				} catch (Exception e) {
					hasException = true ;
					log.error("�ӽ��յ���װ���������ɶ������쳣!" , e) ;
				}finally{
					if(hasException){
						hasException = false ;
					}else{
						synchronized(synObj){
							list.add(data) ;
							synObj.notifyAll() ;
						}
					}
					set = null ;
					subDatas.remove(id) ;
				}
				
			}
		}
	}
}
