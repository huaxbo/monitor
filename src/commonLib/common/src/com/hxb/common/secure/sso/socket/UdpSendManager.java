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
	 * ֻ����һ�γ�ʼ��
	 * @return
	 * @throws ACException
	 */
	public static void initFirst(int port , int timeOut)throws SocketException{
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
						socket.send(byteOut.toByteArray(), d.receiveIp, d.receivePort.intValue()) ;

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
