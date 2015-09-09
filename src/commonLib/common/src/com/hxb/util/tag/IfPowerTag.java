package com.hxb.util.tag;

import javax.servlet.jsp.*;
//import com.automic.session.UserVO;
//import com.automic.common.secureSso.SecureHolder;

/**
 * <p>Title: </p>
 * <p>Description: 用户权限检查(部分网页) </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class IfPowerTag extends IfTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3606909666574800022L;

	@SuppressWarnings("unused")
	private String powerKey = null;

	public void setKey(String powerKey) {
		this.powerKey = powerKey;
	}

	protected boolean check() throws JspException {
//		UserVO vo = SecureHolder.getUserInfo() ;
//		if(vo == null){
//			throw new JspException("进行权限验证时，没有得到会话中的用户信息，这是因为系统实际应用了权限控制，而SecureFilterSSO配置成不应用权限控制!") ;
//		}
//		String powers[] = vo.getPowers() ;
//		if(powers != null){
//			for(int i = 0 ; i < powers.length ; i++){
//				if(powers[i].equals(this.powerKey)){
//					return true ;
//				}
//			}
//		}
		return false ;
	}

}
