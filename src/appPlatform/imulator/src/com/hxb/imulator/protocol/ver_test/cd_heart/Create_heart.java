package com.hxb.imulator.protocol.ver_test.cd_heart;

import com.hxb.imulator.protocol.ver_test.VerTestCode;
import com.hxb.imulator.protocol.ver_test.VerTestConstant;
import com.hxb.imulator.protocol.ver_test.VerTestUtil;

public class Create_heart {

	private static int len = VerTestConstant.HEAD_LEN
			+ VerTestConstant.METER_LEN 
			+ VerTestConstant.CODE_LEN 
			+ 3;

	private static Create_heart heart;

	private Create_heart() {}

	/**
	 * @return
	 */
	public static Create_heart getSingle() {
		if (heart == null) {
			heart = new Create_heart();
		}

		return heart;
	}

	/**
	 * ππΩ®√¸¡Ó
	 * 
	 * @param meterId
	 * @param funcCode
	 * @return
	 */
	public byte[] create(String meterId) {
		byte[] bts = new byte[len];
		int idx = 0;
		idx = VerTestUtil.createHead(idx, bts);
		idx = VerTestUtil.createMeterId(idx, meterId, bts);
		idx = VerTestUtil.createFuncCode(idx, VerTestCode.cd_heart, bts);	
		
		
		idx = VerTestUtil.createCrc(idx, bts);
		
		return bts;
	}
}
