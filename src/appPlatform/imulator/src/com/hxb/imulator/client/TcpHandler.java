package com.hxb.imulator.client;

import org.apache.log4j.Logger;

import com.hxb.imulator.protocol.ProtocolDriver;
import com.hxb.imulator.protocol.util.UtilProtocol;
import com.hxb.imulator.protocol.ver_test.VerTestCode;

public class TcpHandler {

	private Logger log = Logger.getLogger(TcpHandler.class);
	private static TcpHandler handler;
	
	private String[] meter;
	
	private TcpHandler(){}
	
	/**
	 * @return
	 */
	public static TcpHandler getSingleOne(String[] meter){
		if(handler == null){
			handler = new TcpHandler();
			handler.meter = meter;
		}
		
		return handler;
	}
	
	
	/**
	 * 任务执行
	 * @return
	 */
	public byte[] execute(byte[] bts,TcpClient client){
		byte[] rlt = null;
		UtilProtocol up = UtilProtocol.getSingle();
		
		try {
			log.info("设备[" + meter[0] + "]接收到命令：" + up.byte2Hex(bts, true).toUpperCase());
			
			ProtocolDriver pd = (ProtocolDriver)Class.forName(meter[1]).newInstance();
			String funcCode = pd.analyzeFuncCode(bts);
			
			if(funcCode == null || funcCode.equals(VerTestCode.cd_heart)){
				
				return null;
			}
			
			rlt = pd.analyze(bts);//构建命令结果
			client.send(rlt);//命令结果回复		
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlt;
	}
	
	
}
