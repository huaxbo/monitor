package com.hxb.util.tag;

import javax.servlet.jsp.*;
//import com.automic.session.UserVO;
//import com.automic.common.secureSso.SecureHolder;

/**
 * <p>Title: </p>
 * <p>Description: �û�Ȩ�޼��(������ҳ) </p>
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
//			throw new JspException("����Ȩ����֤ʱ��û�еõ��Ự�е��û���Ϣ��������Ϊϵͳʵ��Ӧ����Ȩ�޿��ƣ���SecureFilterSSO���óɲ�Ӧ��Ȩ�޿���!") ;
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
