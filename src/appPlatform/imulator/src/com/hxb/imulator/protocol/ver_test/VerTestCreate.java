package com.hxb.imulator.protocol.ver_test;

import com.hxb.imulator.protocol.ver_test.cd_10.Create_10;
import com.hxb.imulator.protocol.ver_test.cd_11.Create_11;
import com.hxb.imulator.protocol.ver_test.cd_heart.Create_heart;
import com.hxb.imulator.protocol.ver_test.cd_register.Create_register;


public class VerTestCreate {

	
	/**
	 * @param meterId
	 * @param code
	 * @return
	 */
	public static byte[] create_register(String meterId){
		
		return Create_register.getSingle().create(meterId);
	}
	/**
	 * @param meterId
	 * @param code
	 * @return
	 */
	public static byte[] create_heart(String meterId){
		
		return Create_heart.getSingle().create(meterId);
	}
	/**
	 * @param meterId
	 * @param funcCode
	 * @return
	 */
	public static byte[] create_10(String meterId,String funcCode){
		
		return Create_10.getSingle().create(meterId, funcCode);
	}
	/**
	 * @param meterId
	 * @param funcCode
	 * @return
	 */
	public static byte[] create_11(String meterId,String funcCode){
		
		return Create_11.getSingle().create(meterId, funcCode);
	}
}
