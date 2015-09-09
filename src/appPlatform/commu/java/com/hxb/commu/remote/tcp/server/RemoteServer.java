package com.hxb.commu.remote.tcp.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.core.session.IdleStatus ;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder ;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter ;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;

import com.hxb.commu.remote.tcp.server.codec.RemoteCodecFactory;
import com.hxb.commu.util.CommuConstant;

public class RemoteServer {
	
	private static RemoteServer server;
	private Logger log = Logger.getLogger(RemoteServer.class);
	
	/**
	 * 
	 */
	private RemoteServer(){}
	
	/**
	 * @return
	 */
	public static RemoteServer getSingle(){
		if(server == null){
			server = new RemoteServer();
		}
		
		return server;
	}
	
	/**
	 * ��������
	 */
	public boolean start(){		

		/**
		 * �첽����������ͨ�Ž����������������������
		 * ͬʱ����IoProcessor����
		 * ����ط�����ִ��������IO ������Ĭ�����õ��̸߳�����CPU �ĺ���+1��Ʃ�磺��CPU ˫
		 * �˵ĵ��ԣ�Ĭ�ϵ�IoProcessor �̻߳ᴴ��3 ������Ҳ����˵һ��IoAcceptor ����
		 * IoConnector Ĭ�ϻ����һ��IoProcessor �أ����������3 ��IoProcessor����ΪIO ����
		 * �ķ���Դ����������ʹ��IoProcessor ����������ݵĶ�д������������������ܡ���Ҳ��
		 * ��ǰ��˵��IoAccetor��IoConnector ʹ��һ��Selector����IoProcessor ʹ���Լ�������
		 * Selector ��ԭ��
		 * ��ôΪʲôIoProcessor ���е�IoProcessor ����ֻ��CPU �ĺ�����1 �أ���ΪIO ��д��
		 * ���Ǻķ�CPU �Ĳ�������ÿһ��CPU ͬʱֻ������һ���̣߳����IoProcessor ���е�
		 * IoProcessor ������������Խ��Խ�á�
		 */
		NioSocketAcceptor acceptor = new NioSocketAcceptor(CommuConstant.remote_processors);
		
		SocketSessionConfig seCon = acceptor.getSessionConfig() ;
		
		/* ���ö�����һ��������ѻ����С��
		 * һ�㲻��Ҫ���������������ΪIoProcessor ���Զ���
		 * ������Ĵ�С������Ե���setMinReadBufferSize()��setMaxReadBufferSize()��������
		 * ������IoProcessor ��������Զ���������������ָ�������䡣
		 */
		//seCon.setReadBufferSize(1024);
		//�����������ӿ���ʱ��
		seCon.setIdleTime(IdleStatus.BOTH_IDLE, CommuConstant.remote_idle);

		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain() ;
		//����������
		chain.addLast("protocol", new ProtocolCodecFilter(new RemoteCodecFactory()));
		//��־������
		if(CommuConstant.remote_logs){
			LoggingFilter lf = new LoggingFilter();
			lf.setSessionCreatedLogLevel(LogLevel.NONE);
			lf.setSessionIdleLogLevel(LogLevel.NONE);
			chain.addLast("logger", lf);
		}		
		
		/*
		 * һ��ExecutorFilter ����Ҫ����ProtocolCodecFilter ��
		 * �����ĺ��棬Ҳ���ǲ�Ҫ�ñ���������ڶ������߳��ϣ�����Ҫ������IoProcessor ���ڵ�
		 * �̣߳���Ϊ����봦������ݶ�����IoProcessor ��ȡ�ͷ��͵ģ�û��Ҫ�����µ��̣߳���
		 * �����ܷ������½���һ��ʹ��ExecutorFilter �ĵ��ͳ����ǽ�ҵ���߼���Ʃ�磺��ʱ����
		 * �ݿ���������ڵ������߳������У�Ҳ����˵��IO �����޹صĲ������Կ���ʹ��
		 * ExecutorFilter ���첽ִ�С�
		 */
		chain.addLast("exceutor", new ExecutorFilter());

		//ҵ���߼�������������������Ự�������������
		RemoteIoHandler handler = new RemoteIoHandler();
		acceptor.setHandler(handler);
		
		try {
			acceptor.bind(new InetSocketAddress(CommuConstant.remote_port));
			log.info("Զ������ͨ�ŷ���(PORT:" + CommuConstant.remote_port + ")�Ѿ�����!");
		} catch (IOException e) {
			log.error("Զ������ͨ�ŷ���(PORT:" + CommuConstant.remote_port + ")����ʧ�ܣ�");
			return false;
		}finally{}

		return true;
	}
}
