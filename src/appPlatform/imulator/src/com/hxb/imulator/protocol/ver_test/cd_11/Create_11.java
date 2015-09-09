package com.hxb.imulator.protocol.ver_test.cd_11;

import com.hxb.imulator.protocol.ver_test.VerTestConstant;
import com.hxb.imulator.protocol.ver_test.VerTestUtil;

public class Create_11 {

	private static int len = VerTestConstant.HEAD_LEN
			+ VerTestConstant.METER_LEN 
			+ VerTestConstant.CODE_LEN 
			+ 3;

	private static Create_11 creater;

	private Create_11() {}

	/**
	 * @return
	 */
	public static Create_11 getSingle() {
		if (creater == null) {
			creater = new Create_11();
		}

		return creater;
	}

	/**
	 * ππΩ®√¸¡Ó
	 * 
	 * @param meterId
	 * @param funcCode
	 * @return
	 */
	public byte[] create(String meterId,String funcCode) {
		byte[] bts = new byte[len];
		int idx = 0;
		idx = VerTestUtil.createHead(idx, bts);
		idx = VerTestUtil.createMeterId(idx, meterId, bts);
		idx = VerTestUtil.createFuncCode(idx, funcCode, bts);	
		
		
		idx = VerTestUtil.createCrc(idx, bts);
		
		return bts;
	}
}
