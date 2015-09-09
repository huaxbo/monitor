/**   
* @Title: CheckPower.java 
* @Package com.hxb.global.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author huaXinbo   
* @date 2014-3-25 下午04:07:11 
* @version V1.0   
*/ 
package com.hxb.global.util;

import com.hxb.common.secure.SessionUserHolder;
import com.hxb.common.sso.session.SessionUserVO;

/** 
 * @ClassName: CheckPower 
 * @Description: TODO(权限校验::供页面标签判断使用) 
 * @author huaXinbo
 * @date 2014-3-25 下午04:07:11 
 *  
 */
public class CheckPower {

	
	/** 
	* @Title: check 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return boolean    返回类型:true:拥有权限、false：未有权限 
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
