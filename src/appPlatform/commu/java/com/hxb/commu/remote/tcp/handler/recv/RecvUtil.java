package com.hxb.commu.remote.tcp.handler.recv;

import com.hxb.commu.remote.protocol.util.UtilProtocol;
import com.hxb.commu.remote.protocol.ver_test.VerTestConstant;


public class RecvUtil {

	
	/**
	 * 校验数据头合法性
	 * @param bts
	 * @return
	 */
	public static String checkHead(byte[] bts){
		UtilProtocol up = UtilProtocol.getSingle();
		if(bts[0] == VerTestConstant.HEAD_1 
				&& bts[1] == VerTestConstant.HEAD_2){//合法头校验
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
	 * 校验数据crc
	 * @param bts
	 * @return
	 */
	public static boolean checkCrc(byte[] bts){
		
		return true;
	}
		
}
