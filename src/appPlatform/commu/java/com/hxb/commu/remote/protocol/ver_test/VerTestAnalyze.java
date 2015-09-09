package com.hxb.commu.remote.protocol.ver_test;

import com.hxb.commu.remote.protocol.ProtocolData;
import com.hxb.commu.remote.protocol.ver_test.cd_10.Answer_10;

public class VerTestAnalyze {

	/**
	 * 解析主动上报数据
	 * @param meterId
	 * @param bts
	 * @return
	 */
	public static ProtocolData analyze_10(String meterId,byte[] bts){
		
		return Answer_10.getSingle().parseData(meterId, bts);
	}
	
	
	
}
