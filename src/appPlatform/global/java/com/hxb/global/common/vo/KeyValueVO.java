/**   
* @Title: KeyValueVO.java 
* @Package com.hxb.global.common.vo 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author huaXinbo   
* @date 2014-4-21 ����01:45:52 
* @version V1.0   
*/ 
package com.hxb.global.common.vo;

/** 
 * @ClassName: KeyValueVO 
 * @Description: TODO(��ֵ��vo) 
 * @author huaXinbo
 * @date 2014-4-21 ����01:45:52 
 *  
 */
public class KeyValueVO {

	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param key_str
	* @param value_str 
	*/ 
	public KeyValueVO(String key_str,String value_str){
		if(key_str != null){
			key_str = "";
		}
		if(value_str == null){
			value_str = "";
		}
		this.key_str = key_str;
		this.value_str = value_str;
	}
	
	/** 
	* @Fields key_str : TODO(����String����) 
	*/ 
	private String key_str;
	/** 
	* @Fields value_str : TODO(ֵ��String����) 
	*/ 
	private String value_str;
	
	
	public String getKey_str() {
		return key_str;
	}
	public void setKey_str(String key_str) {
		this.key_str = key_str;
	}
	public String getValue_str() {
		return value_str;
	}
	public void setValue_str(String value_str) {
		this.value_str = value_str;
	}
	
	
}
