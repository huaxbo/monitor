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
	 * ��ֵЭ���
	 * @param key
	 */
	public void setProtocolKey(String key){
		_PROTOCOL_KEY = key;
	}
	/**
	 * ��ֵЭ��ֵ
	 * @param value
	 */
	public void setProtocolValue(String value){
		_PROTOCOL_VALUE = value;
	}
	/**
	 * ��ȡЭ���
	 * @return
	 */
	public String getProtocolKey(){
		return _PROTOCOL_KEY;
	}
	/**
	 * ��ȡЭ��ֵ
	 * @return
	 */
	public String getProtocolValue(){
		return _PROTOCOL_VALUE == null || _PROTOCOL_VALUE.equals("") ? _PROTOCOL_KEY : _PROTOCOL_VALUE;
	}
	
}
