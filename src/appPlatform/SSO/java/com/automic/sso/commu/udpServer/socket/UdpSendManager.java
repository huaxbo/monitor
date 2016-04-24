package com.automic.sso.commu.udpServer.socket;

import java.util.*;
import org.apache.log4j.*;
import com.automic.ACException;
import com.automic.common.sso.commu.udpServer.*;
import java.io.*;


//import com.automic.sso.session.UserVO;

public class UdpSendManager extends Thread{

	/**
	 * 接收端缓存大小是1024,大于1024的要分包传
	 */
	private static int oneK = 1024 ;
	/**
	 * 接收端缓存大小是1024,加上本对象其他属性，所以每次分包只能为384
	 */
	private static int onceByteLen = 1024 ;
	private static UdpSendManager instance ;
	private static UdpSendSocket socket ;
	private static Object synObj = new Object() ;
	private static ArrayList<UdpData> list = new ArrayList<UdpData>() ;
	
	private static Logger log = Logger.getLogger(UdpSendManager.class.getName()) ;
	
	
	private UdpSendManager(int port , int timeOut)throws ACException{
		socket = new UdpSendSocket(port , timeOut) ;
	}
	
	/**
	 * 只进行一次初始化
	 * @return
	 * @throws ACException
	 */
	public static void initOnlyOnce(int port , int timeOut)throws ACException{
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
						
						int len = byteOut.size() ;
						if(len <= oneK){
							socket.send(byteOut.toByteArray(), d.receiveIp, d.receivePort.intValue()) ;
						}else{
							byte[] bs = byteOut.toByteArray() ;
							int size = (len/onceByteLen) + (len%onceByteLen>0?1:0) ;
							UdpData subd = new UdpData() ;
							subd.copyBase(d) ;
							subd.dataType = UdpDataType.BYTES ;
							subd.byteCount = size ;
							int subLen = onceByteLen ;
							for(int i = 0 ; i < size ; i++){
								if(i == size - 1){
									subLen = len - (onceByteLen * (size - 1)) ;
								}
								byte[] subb = new byte[subLen] ;
								subd.byteIndex = i ;
								subd.byteLen = subLen ;
								//System.arraycopy(Object   src,   int   srcPos,   Object   dest,   int   destPos,   int   length)     
								System.arraycopy(bs , i*onceByteLen , subb , 0 , subLen) ;
//								for(int k = 0 ; k < subLen ; k++){
//									subb[k] = 'a' ;
//								}
								//subd.bytes = new String(subb) ;
								subd.byteDatas = subb ;
								subd.obj = null ;
								ByteArrayOutputStream subbyteOut = new ByteArrayOutputStream() ;
								ObjectOutputStream subobjOut = new ObjectOutputStream(new DataOutputStream(subbyteOut)) ;
								subobjOut.writeObject(subd) ;
								try{
									Thread.sleep(50);//增强发送间隔
								}catch(Exception e){}
								socket.send(subbyteOut.toByteArray(), subd.receiveIp, subd.receivePort.intValue()) ;
							}
						}
						

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
