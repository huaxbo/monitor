/**
 * 
 */
package com.hxb.monitor.commu.protocol;

/**
 * @author Administrator
 *
 */
public class GeneralProtocol {

	private String _PROTOCOL_KEY;
	private String _PROTOCOL_VALUE;
	
	/**
	 * 赋值协议键
	 * @param key
	 */
	public void setProtocolKey(String key){
		_PROTOCOL_KEY = key;
	}
	/**
	 * 赋值协议值
	 * @param value
	 */
	public void setProtocolValue(String value){
		_PROTOCOL_VALUE = value;
	}
	/**
	 * 获取协议键
	 * @return
	 */
	public String getProtocolKey(){
		return _PROTOCOL_KEY;
	}
	/**
	 * 获取协议值
	 * @return
	 */
	public String getProtocolValue(){
		return _PROTOCOL_VALUE == null || _PROTOCOL_VALUE.equals("") ? _PROTOCOL_KEY : _PROTOCOL_VALUE;
	}
	
}
