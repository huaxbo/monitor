package com.hxb.commu.remote.tcp.server.codec ;

import java.io.*;

 
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.core.buffer.* ;
import org.apache.mina.core.session.IoSession; 


/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class RemoteDecoder extends CumulativeProtocolDecoder {
	/*
	 * ����
	 * (non-Javadoc)
	 * @see org.apache.mina.filter.codec.CumulativeProtocolDecoder#doDecode(org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer, org.apache.mina.filter.codec.ProtocolDecoderOutput)
	 */
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws IOException, Exception {
		byte[] data = new byte[in.limit()];
		in.get(data);//getһ���ֽڣ���Ӧposition����ƶ�һ���ֽ�
		out.write(data);
		
		return true;
	}
	
	

}
