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
	 * 发送数据
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
//			log.info("发送数据长度:" + data.length) ;
			socket.send(packet) ;
		}catch(SocketException e){
			throw new ACException("网络UDP输出数据发生异常(" + ip + ":" + port + ")!" , e) ;
		}catch(IOException e){
			throw new ACException("网络UDP输出数据发生异常(" + ip + ":" + port + ")!" , e) ;
		}
	}


}
