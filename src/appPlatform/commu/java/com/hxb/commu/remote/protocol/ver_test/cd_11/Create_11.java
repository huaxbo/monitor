package com.hxb.commu.remote.protocol.ver_test.cd_11;

import com.hxb.commu.local.common.CommandAttach;
import com.hxb.commu.remote.protocol.util.UtilProtocol;
import com.hxb.commu.remote.protocol.ver_test.VerTestConstant;
import com.hxb.commu.remote.protocol.ver_test.VerTestUtil;

public class Create_11 {

	private int len = VerTestConstant.HEAD_LEN
			+ VerTestConstant.METER_LEN
			+ VerTestConstant.CODE_LEN
			+ 10;
	
	private static Create_11 create;
	
	private Create_11(){}
	
	/**
	 * 
	 */
	public static Create_11 getSingle(){
		if(create == null){
			create = new Create_11();
		}
		return create;
	}
	
	/**
	 * ¹¹½¨
	 * @param atta
	 * @return
	 */
	public byte[] create(String meterId,String funcCode,CommandAttach atta){
		UtilProtocol up = UtilProtocol.getSingle();
		byte[] bts = new byte[len];
		int idx = 0;
		idx = VerTestUtil.createHead(idx, bts);
		idx = VerTestUtil.createMeterId(idx, meterId, bts);
		idx = VerTestUtil.createFuncCode(idx, funcCode, bts);	
		
		
		idx = VerTestUtil.createCrc(idx, bts);
		
		return bts;
	}
}
