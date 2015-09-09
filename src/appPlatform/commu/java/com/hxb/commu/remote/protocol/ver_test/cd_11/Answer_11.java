package com.hxb.commu.remote.protocol.ver_test.cd_11;

import org.apache.log4j.Logger;

import com.hxb.commu.remote.protocol.ProtocolData;

public class Answer_11 {
	
	private Logger log = Logger.getLogger(Answer_11.class);
	private static Answer_11 answer;
	
	private Answer_11(){}
	
	/**
	 * @return
	 */
	public static Answer_11 getSingle(){
		if(answer == null){
			answer = new Answer_11();
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
		Data_11 d = new Data_11();
		data.setData(d);
		
		d.setData1("data1");
		d.setData2("data2");
		d.setData3(10);
		
		log.info("解析召测实时值上报数据完成！");
		
		return data;
	}
}
