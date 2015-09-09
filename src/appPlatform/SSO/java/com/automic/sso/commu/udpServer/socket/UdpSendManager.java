package com.automic.sso.commu.udpServer.socket;

import java.util.*;
import org.apache.log4j.*;
import com.automic.ACException;
import com.automic.common.sso.commu.udpServer.*;
import java.io.*;


//import com.automic.sso.session.UserVO;

public class UdpSendManager extends Thread{

	/**
	 * ���ն˻����С��1024,����1024��Ҫ�ְ���
	 */
	private static int oneK = 1024 ;
	/**
	 * ���ն˻����С��1024,���ϱ������������ԣ�����ÿ�ηְ�ֻ��Ϊ384
	 */
	private static int onceByteLen = 384 ;
	private static UdpSendManager instance ;
	private static UdpSendSocket socket ;
	private static Object synObj = new Object() ;
	private static ArrayList<UdpData> list = new ArrayList<UdpData>() ;
	
	private static Logger log = Logger.getLogger(UdpSendManager.class.getName()) ;
	
	
	private UdpSendManager(int port , int timeOut)throws ACException{
		socket = new UdpSendSocket(port , timeOut) ;
	}
	
	/**
	 * ֻ����һ�γ�ʼ��
	 * @return
	 * @throws ACException
	 */
	public static void initOnlyOnce(int port , int timeOut)throws ACException{
		instance = new UdpSendManager(port , timeOut) ;
	}
	/**
	 * �õ�Ψһʵ��
	 * @return
	 * @throws ACException
	 */
	public static UdpSendManager singleInstance()throws ACException{
		if(instance == null){
			throw new ACException("�����ȳ�ʼ��" + UdpSendManager.class.getName() + "��ʵ����") ;
		}
		return instance ;
	}
	
	/**
	 * ��������
	 * @param obj
	 * @param ip
	 * @param port
	 * @throws ACException
	 */
	public void setSendObject(UdpData d)throws ACException{
		if(d == null || d.receiveIp == null || d.receivePort == null || d.obj == null){
			throw new ACException("UDP����������ݲ�������") ;
		}
		synchronized(synObj){
			list.add(d) ;
			synObj.notifyAll() ;
		}
	}

	/**
	 * �߼�����
	 */
	public void run(){
		while(true){
			synchronized(synObj){
				if(list.size() == 0){
					try{
						synObj.wait() ;
					}catch(Exception e){
						log.error("��������̷߳����ȴ��쳣��") ;
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
								socket.send(subbyteOut.toByteArray(), subd.receiveIp, subd.receivePort.intValue()) ;
							}
						}
						

					}catch(ACException e){
						log.error(e.getMessage() , e) ;
					}catch(IOException e){
						log.error("�ɶ�����������������ݷ����쳣!" , e) ;
					}
					it.remove() ;
				}
			}
		}
		
	}
	
}
