package com.hxb.common.secure.sso.socket ;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpReceiveSocket {
	private DatagramSocket socket ;
	private int dataLen ;
	private byte[] data ;

	protected UdpReceiveSocket(int port , int timeOut , int dataLen) throws SocketException{
		this.dataLen = dataLen ;
		socket = new DatagramSocket(port) ;
		socket.setSoTimeout(timeOut) ;
		data = new byte[dataLen] ;
	}
	
	/**
	 * ������
	 * @return
	 */
	protected byte[] receive(){
		DatagramPacket packet = new DatagramPacket(data , dataLen) ;
		boolean hasException = false ;
		try{
			socket.receive(packet) ;
		}catch(IOException e){
			//���ܷ�����ʱ�쳣
			hasException = true ;
		}
		if(hasException){
			return null ;
		}else{
			//������һ�����飬Ϊ�´ν���������׼��
			data = new byte[dataLen] ;
			return packet.getData() ;
		}
		
	}

}
