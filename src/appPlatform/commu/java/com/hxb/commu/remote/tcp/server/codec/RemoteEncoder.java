package com.hxb.commu.remote.tcp.server.codec ;

import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import java.io.*;


public class RemoteEncoder extends ProtocolEncoderAdapter {


    /* (non-Javadoc)
     * @see org.apache.mina.filter.codec.ProtocolEncoder#encode(org.apache.mina.core.session.IoSession, java.lang.Object, org.apache.mina.filter.codec.ProtocolEncoderOutput)
     */
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws IOException, Exception{
        byte[] com = (byte[]) message;
        int capacity = (com==null?0:com.length) ;
        IoBuffer buffer = IoBuffer.allocate(capacity, false);
        buffer.put(com);
        buffer.flip();
        out.write(buffer);
    }

 }
