package com.hxb.commu.remote.protocol.ver_test;

import com.hxb.commu.remote.protocol.ver_test.cd_confirm.Create_confirm;

public class VerTestCreate {

	/**
	 * @param meterId
	 * @param code
	 * @return
	 */
	public static byte[] create_confirm(String meterId,String funcCode){
		
		return Create_confirm.getSingle().create(meterId, funcCode);
	}
	
	
}
