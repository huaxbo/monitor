package com.hxb.common.secure.sso.socket ;

import java.io.*;
import java.net.*;

import com.hxb.ACException;

public class UdpSendSocket {
	
	private DatagramSocket socket ;
//	private static Logger log = Logger.getLogger(UdpSendSocket.class.getName()) ;

	protected UdpSendSocket(int port , int timeOut) throws SocketException{
		socket = new DatagramSocket(port) ;
		socket.setSoTimeout(timeOut) ;
	}
	
	
	/**
	 * ��������
	 * @param obj
	 * @param IP
	 * @param port
	 */
	protected void send(byte[] data , String ip , int port) throws ACException{
		try{
			DatagramPacket packet = new DatagramPacket(
					data , 
					data.length , 
					InetAddress.getByName(ip) , 
					port) ;
//			log.info("�������ݳ���:" + data.length) ;
			socket.send(packet) ;
		}catch(SocketException e){
			throw new ACException("����UDP������ݷ����쳣(" + ip + ":" + port + ")!" , e) ;
		}catch(IOException e){
			throw new ACException("����UDP������ݷ����쳣(" + ip + ":" + port + ")!" , e) ;
		}
	}


}
