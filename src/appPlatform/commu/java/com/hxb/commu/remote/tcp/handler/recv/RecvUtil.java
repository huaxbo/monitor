package com.hxb.commu.remote.tcp.handler.recv;

import com.hxb.commu.remote.protocol.util.UtilProtocol;
import com.hxb.commu.remote.protocol.ver_test.VerTestConstant;


public class RecvUtil {

	
	/**
	 * У������ͷ�Ϸ���
	 * @param bts
	 * @return
	 */
	public static String checkHead(byte[] bts){
		UtilProtocol up = UtilProtocol.getSingle();
		if(bts[0] == VerTestConstant.HEAD_1 
				&& bts[1] == VerTestConstant.HEAD_2){//�Ϸ�ͷУ��
			try {
				String meterId = up.BCD2String(bts, 
						VerTestConstant.HEAD_LEN,
						VerTestConstant.HEAD_LEN + VerTestConstant.METER_LEN - 1);
				
				return meterId;	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * У������crc
	 * @param bts
	 * @return
	 */
	public static boolean checkCrc(byte[] bts){
		
		return true;
	}
		
}
