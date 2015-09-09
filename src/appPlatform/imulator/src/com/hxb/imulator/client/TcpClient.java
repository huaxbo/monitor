package com.hxb.imulator.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class TcpClient {

	private SocketChannel channel;
	private Selector selector;
	private ByteBuffer buffer = ByteBuffer.allocate(1024);
	private TcpHandler handler;
	
	
	public TcpClient(String ip,int port,TcpHandler handler) throws IOException{
		channel = SocketChannel.open(new InetSocketAddress(ip, port));
		channel.configureBlocking(false);
		
		selector = Selector.open();
		channel.register(selector, SelectionKey.OP_READ);
		
		this.handler = handler;
		
		//启动接收任务
		new RevHandler(this).start();
	}
	
	
	/**
	 * @param ip
	 * @param port
	 * @throws IOException 
	 */
	public void send(byte[] bts) throws IOException{
		buffer.put(bts);
		buffer.flip();
		channel.write(buffer);
		buffer.clear();
	}	
	
	
	/**
	 * @author Administrator
	 *
	 */
	class RevHandler extends Thread{
		
		private TcpClient tc;
		
		public RevHandler(TcpClient tc){
			this.tc = tc;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				boolean roll = true;
				while(roll){
					selector.select();
					Set<SelectionKey> keys = selector.selectedKeys();
					Iterator<SelectionKey> it = keys.iterator();
					while(it.hasNext()){
						SelectionKey key = it.next();
						if(key.isReadable()){
							SocketChannel sc = (SocketChannel)key.channel();
							sc.read(buffer);
							buffer.flip();
							handler.execute(buffer.array(),tc);
							buffer.clear();
							
							/*key.cancel();
							selector.close();
							channel.close();
							
							roll = false;*/
						}
						it.remove();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{}
		}		
	}
}
