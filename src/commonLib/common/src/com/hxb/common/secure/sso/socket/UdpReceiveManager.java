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
	
	//等待回数据的轮询次数
	private static final int waitTimes = 10 ;
	//回数据超时时长(30秒)
	private static final long dataTimeOut = 1000*30 ;

	private static UdpReceiveSocket socket;
	private static ArrayList<UdpData> list = new ArrayList<UdpData>() ;

	private static Hashtable<String , Hashtable<String , UdpData>> subDatas = new Hashtable<String , Hashtable<String , UdpData>>() ;
	
	
	private static Logger log = Logger.getLogger(UdpReceiveManager.class
			.getName());

	
	private UdpReceiveManager(){
	}

	/**
	 * 只进行一次初始化
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
	 * 得到唯一实例
	 * @return
	 * @throws ACException
	 */
	public static UdpReceiveManager newInstance() throws ACException {
		return new UdpReceiveManager();
	}
	
	/**
	 * 得到命令回数
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
					//等待超时
					flag = false ;
				}
			}
		}
		return rd ;
	}
	/**
	 * 清除超时数据
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
					//超时
					it.remove() ;
				}
			}
		}
	}

	/**
	 * 接收数据
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
				//前面已经接收了10条数据
				//执行一次超时数据清理
				count = 0 ;
				this.cleanData() ;
			}
			datas = socket.receive();
			if (datas == null || datas.length == 0) {
				//接收超时或者发生其他异常
				continue;
			}
			try {
				ObjectInputStream objIn = new ObjectInputStream(
						new DataInputStream(new ByteArrayInputStream(datas)));
				data = (UdpData) objIn.readObject();
				if(data.dataType != null && data.dataType.equals(UdpDataType.BYTES)){
					//组装数据
					this.packageData(data) ;
					pageData = true ;
				}
			} catch (Exception e) {
				e.printStackTrace() ;
				hasException = true ;
				log.error("从接收的数据中生成对象发生异常!" , e) ;
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
			//非完整数据，舍弃数据
			log.error("从单点登录服务收到的数据不完整，舍充该数据") ;
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
			//数据收全
			UdpData subd = null ;
			boolean hasError = false ;
			int dataLen = 0 ;
			for(int i = 0 ; i < d.byteCount ; i++){
				subd = set.get(i + "") ;
				if(subd == null){
					//出错了
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
					log.error("从接收的组装数据中生成对象发生异常!" , e) ;
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
