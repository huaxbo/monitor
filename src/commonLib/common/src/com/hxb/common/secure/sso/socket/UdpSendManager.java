package com.hxb.common.secure.sso.socket ;

import java.util.*;
import java.net.*;
import org.apache.log4j.*;

import com.hxb.ACException;
import com.hxb.common.sso.commu.udpServer.*;

import java.io.*;

public class UdpSendManager extends Thread{
	private static UdpSendManager instance ;
	private static UdpSendSocket socket ;
	private static Object synObj = new Object() ;
	private static ArrayList<UdpData> list = new ArrayList<UdpData>() ;
	
	private static Logger log = Logger.getLogger(UdpSendManager.class.getName()) ;
	
	
	private UdpSendManager(int port , int timeOut)throws SocketException{
		socket = new UdpSendSocket(port , timeOut) ;
	}
	
	/**
	 * 只进行一次初始化
	 * @return
	 * @throws ACException
	 */
	public static void initFirst(int port , int timeOut)throws SocketException{
		instance = new UdpSendManager(port , timeOut) ;
	}
	/**
	 * 得到唯一实例
	 * @return
	 * @throws ACException
	 */
	public static UdpSendManager singleInstance()throws ACException{
		if(instance == null){
			throw new ACException("请首先初始化" + UdpSendManager.class.getName() + "类实例！") ;
		}
		return instance ;
	}
	
	
	/**
	 * 发送数据
	 * @param obj
	 * @param ip
	 * @param port
	 * @throws ACException
	 */
	public void setSendObject(UdpData d)throws ACException{
		if(d == null || d.receiveIp == null || d.receivePort == null || d.obj == null){
			throw new ACException("UDP网络输出数据不完整！") ;
		}
		synchronized(synObj){
			list.add(d) ;
			synObj.notifyAll() ;
		}
	}

	/**
	 * 逻辑方法
	 */
	public void run(){
		while(true){
			synchronized(synObj){
				if(list.size() == 0){
					try{
						synObj.wait() ;
					}catch(Exception e){
						log.error("输出数据线程发生等待异常！") ;
						continue ;
					}
				}

				Iterator<UdpData> it = list.iterator() ;
				UdpData d = null ;
				while(it.hasNext()){
					d = it.next() ;
					try{
						ByteArrayOutputStream byteOut = new ByteArrayOutputStream() ;
						ObjectOutputStream objOut = new ObjectOutputStream(new DataOutputStream(byteOut)) ;
						objOut.writeObject(d) ;
						socket.send(byteOut.toByteArray(), d.receiveIp, d.receivePort.intValue()) ;

					}catch(ACException e){
						log.error(e.getMessage() , e) ;
					}catch(IOException e){
						log.error("由对象生成网络输出数据发生异常!" , e) ;
					}
					it.remove() ;
				}
			}
		}
		
	}
	
}
