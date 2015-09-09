package com.hxb.commu.remote.protocol.ver_test.cd_10;

import org.apache.log4j.Logger;

import com.hxb.commu.remote.protocol.ProtocolData;

public class Answer_10 {

	private Logger log = Logger.getLogger(Answer_10.class);
	private static Answer_10 answer;
	
	private Answer_10(){}
	
	/**
	 * @return
	 */
	public static Answer_10 getSingle(){
		if(answer == null){
			answer = new Answer_10();
		}
		
		return answer;
	}
	
	
	/**
	 * @param meterId
	 * @param bts
	 * @return
	 */
	public ProtocolData parseData(String meterId,byte[] bts){
		ProtocolData data = new ProtocolData();
		Data_10 d = new Data_10();
		data.setData(d);
		
		d.setData1("data1");
		d.setData2("data2");
		d.setData3(10);
		
		log.info("解析主动上报数据完成！");
		
		return data;
	}
}
