package com.hxb.imulator.protocol.ver_test;

import com.hxb.imulator.protocol.util.UtilProtocol;


public class VerTestUtil {
	
	
	/**
	 * 构建报文头
	 * @param idx
	 * @param bts
	 * @return
	 */
	public static int createHead(int idx,byte[] bts){
		bts[idx++] = VerTestConstant.HEAD_1;
		bts[idx++] = VerTestConstant.HEAD_2;
		
		return idx;
	}
	
	/**
	 * @param idx
	 * @param meterId
	 * @param bts
	 * @return
	 */
	public static int createMeterId(int idx,String meterId,byte[] bts){
		UtilProtocol up = UtilProtocol.getSingle();
		try {
			byte[] ms = up.string2BCD(meterId);
			for(byte m:ms){
				bts[idx++] = m;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}

		return idx;
	}
	
	/**
	 * @param idx
	 * @param funcCode
	 * @param bts
	 * @return
	 */
	public static int createFuncCode(int idx,String funcCode,byte[] bts){
		UtilProtocol up = UtilProtocol.getSingle();
		try{
			bts[idx++] = (byte) up.hex2Int(funcCode);			
		}catch(Exception e){
			
		}finally{}
		
		return idx;
	}
	
	/**
	 * 构建crc校验
	 * @param idx
	 * @param bts
	 * @return
	 */
	public static int createCrc(int idx,byte[] bts){
		
		
		return idx;
	}
	
	
}
