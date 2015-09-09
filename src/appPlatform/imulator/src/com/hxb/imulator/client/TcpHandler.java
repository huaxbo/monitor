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
	 * ����ִ��
	 * @return
	 */
	public byte[] execute(byte[] bts,TcpClient client){
		byte[] rlt = null;
		UtilProtocol up = UtilProtocol.getSingle();
		
		try {
			log.info("�豸[" + meter[0] + "]���յ����" + up.byte2Hex(bts, true).toUpperCase());
			
			ProtocolDriver pd = (ProtocolDriver)Class.forName(meter[1]).newInstance();
			String funcCode = pd.analyzeFuncCode(bts);
			
			if(funcCode == null || funcCode.equals(VerTestCode.cd_heart)){
				
				return null;
			}
			
			rlt = pd.analyze(bts);//����������
			client.send(rlt);//�������ظ�		
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rlt;
	}
	
	
}
