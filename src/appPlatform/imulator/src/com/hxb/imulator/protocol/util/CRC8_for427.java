package com.hxb.imulator.protocol.util;

public class CRC8_for427 {
	
	public int CRC8(byte[] b, int from , int end){
	  int crc = 0;
	  for(int i = from ; i <= end ; i++){
		    crc = crc ^ (b[i]);
		    for(int j = 0; j < 8; j++) {
		      if((crc & 0x80)!=0) {
		    	 crc ^= 0xe5;
		    	 crc *= 2;
		      }else{
		    	 crc *= 2;
		      }
		    }
	  }
	  return crc;
	} 

}
