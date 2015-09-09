/**   
* @Title: CheckPower.java 
* @Package com.hxb.global.util 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author huaXinbo   
* @date 2014-3-25 ����04:07:11 
* @version V1.0   
*/ 
package com.hxb.global.util;

import com.hxb.common.secure.SessionUserHolder;
import com.hxb.common.sso.session.SessionUserVO;

/** 
 * @ClassName: CheckPower 
 * @Description: TODO(Ȩ��У��::��ҳ���ǩ�ж�ʹ��) 
 * @author huaXinbo
 * @date 2014-3-25 ����04:07:11 
 *  
 */
public class CheckPower {

	
	/** 
	* @Title: check 
	* @Description: TODO(������һ�仰�����������������) 
	* @return boolean    ��������:true:ӵ��Ȩ�ޡ�false��δ��Ȩ�� 
	* @throws 
	*/ 
	public static boolean check(String power){
		if(power == null || power.equals("")){
			return true;
		}
		SessionUserVO vo = (SessionUserVO)SessionUserHolder.getUserInfo();
		if(vo != null && vo.getPowers() != null){
			for(String pd:vo.getPowers()){
				if(power.equals(pd)){
					return true;
				}
			}
		}
		
		return false;
	}
}
