package com.hxb.commu.remote.tcp.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import org.apache.log4j.*;

import com.hxb.commu.remote.tcp.handler.recv.RecvHandler;


public class RemoteIoHandler extends IoHandlerAdapter {


	private static Logger log = Logger.getLogger(RemoteIoHandler.class.getName());

    /**
     * �Ựopenʱ�ص��ķ��� 
     */
    public void sessionOpened(IoSession session) throws Exception {
    }

    /**
     * �����쳣ʱ�ص��ķ��� 
     */
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.error(cause.getMessage());
    }
    @Override
	public void sessionClosed(IoSession session) throws Exception {    	
		super.sessionClosed(session);
	}

    /**
     * ���յ����ݺ󣬻ص��ķ������������ݴ���
     */
    public void messageReceived(IoSession session, Object message) throws Exception {
    	byte[] data = (byte[])message ;
    	RecvHandler.getSingle().execute(session, data) ;		
  }


}
