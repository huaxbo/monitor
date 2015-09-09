package com.hxb.commu.remote.tcp.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import org.apache.log4j.*;

import com.hxb.commu.remote.tcp.handler.recv.RecvHandler;


public class RemoteIoHandler extends IoHandlerAdapter {


	private static Logger log = Logger.getLogger(RemoteIoHandler.class.getName());

    /**
     * 会话open时回调的方法 
     */
    public void sessionOpened(IoSession session) throws Exception {
    }

    /**
     * 发生异常时回调的方法 
     */
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.error(cause.getMessage());
    }
    @Override
	public void sessionClosed(IoSession session) throws Exception {    	
		super.sessionClosed(session);
	}

    /**
     * 接收到数据后，回调的方法，进行数据处理
     */
    public void messageReceived(IoSession session, Object message) throws Exception {
    	byte[] data = (byte[])message ;
    	RecvHandler.getSingle().execute(session, data) ;		
  }


}
