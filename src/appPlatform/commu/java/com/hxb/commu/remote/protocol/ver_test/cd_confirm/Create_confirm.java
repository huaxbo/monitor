package com.hxb.commu.remote.protocol.ver_test.cd_confirm;

import com.hxb.commu.remote.protocol.ver_test.VerTestConstant;
import com.hxb.commu.remote.protocol.ver_test.VerTestUtil;

public class Create_confirm {

	private static int len = VerTestConstant.HEAD_LEN
			+ VerTestConstant.METER_LEN
			+ VerTestConstant.CODE_LEN
			+ 10;
	
	private static Create_confirm confirm;
	
	private Create_confirm(){}
	
	/**
	 * @return
	 */
	public static Create_confirm getSingle(){
		if(confirm == null){
			confirm = new Create_confirm();
		}
		
		return confirm;
	}
	
	/**
	 * ππΩ®√¸¡Ó
	 * @param meterId
	 * @param funcCode
	 * @return
	 */
	public byte[] create(String meterId,String funcCode){
		byte[] bts = new byte[len];
		int idx = 0;
		idx = VerTestUtil.createHead(idx, bts);
		idx = VerTestUtil.createMeterId(idx, meterId, bts);
		idx = VerTestUtil.createFuncCode(idx, funcCode, bts);	
		
		
		idx = VerTestUtil.createCrc(idx, bts);
		
		
		return bts;
	}
}
