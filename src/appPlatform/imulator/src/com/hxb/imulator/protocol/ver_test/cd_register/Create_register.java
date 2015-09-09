package com.hxb.imulator.protocol.ver_test.cd_register;

import com.hxb.imulator.protocol.ver_test.VerTestCode;
import com.hxb.imulator.protocol.ver_test.VerTestConstant;
import com.hxb.imulator.protocol.ver_test.VerTestUtil;

public class Create_register {

	private static int len = VerTestConstant.HEAD_LEN
			+ VerTestConstant.METER_LEN 
			+ VerTestConstant.CODE_LEN 
			+ 3;

	private static Create_register heart;

	private Create_register() {}

	/**
	 * @return
	 */
	public static Create_register getSingle() {
		if (heart == null) {
			heart = new Create_register();
		}

		return heart;
	}

	/**
	 * ��������
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
		idx = VerTestUtil.createFuncCode(idx, VerTestCode.cd_register, bts);	
				
		idx = VerTestUtil.createCrc(idx, bts);
		
		return bts;
	}
}
