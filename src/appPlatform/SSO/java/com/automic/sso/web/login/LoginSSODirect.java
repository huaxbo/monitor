package com.automic.sso.web.login ;


import com.automic.sso.util.*;
import com.automic.sso.session.server.DealSso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.automic.sso.session.UserVO ;

@Component("ssoDirectLogin")
@Scope("prototype")
public class LoginSSODirect  {

	@Autowired
	private LoginBusi busi ;
	@Autowired
	private DealSso dealSso ;
	@Autowired
	private UserVO userVo ;
	
	/**
	 * 有固定IP的用户，用cookie中的信息通过SSO直接登录
	 * @param name
	 * @param password
	 * @return
	 */
	public Object directLogin(String sessionId , String userInfo) {
		LoginSSODirect login = this.getThisBean() ;
		String[] namePassIp = userInfo.split(";") ;
		this.userVo = login.busi.login(namePassIp[0], namePassIp[1], namePassIp[2]) ;
		if(this.userVo == null){
			return null ;
		}else{
			//登录成功
			login.busi.loginLog(this.userVo) ;
			
			userVo.setSsoSessionId(sessionId) ;
			login.dealSso.logined(this.userVo, sessionId) ;
			return this.userVo ;
		}
	}

	/**
	 * 从spring容器中得到业务BEAN
	 * @return
	 */
	private LoginSSODirect getThisBean() {
		ApplicationContext ctx = SsoSysListener.getSpringContext() ;
		LoginSSODirect action = (LoginSSODirect) ctx.getBean("ssoDirectLogin");
		return action;
	}

}
