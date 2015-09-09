package com.automic.sso.web.login ;

import javax.servlet.http.*;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.automic.common.sso.login.*;
import com.automic.sso.util.*;
import com.automic.sso.session.UserVO ;
import com.automic.sso.session.server.DealSso;
import com.automic.sso.util.SsoSysActionSupport;
/**
 * @author Administrator
 * --Aspect：设置springAOP,以实现切面方式权限验证、操作日志存储功能
 * --Controller：设置spring annotation方式注入Action，参数值为Action的注入id，供struts配置文件中指向spring调用
 * --Scope：设置为注入对象作用范围，设置request有效
 */
@Aspect
@Controller("loginAction")
@Scope("request")
public class LoginAction extends SsoSysActionSupport {

	private static final long serialVersionUID = -200808270942001L;
	
	private String name ;
	private String password ;
	private String referer ;
	
	/**
	 * 声明busi层对象
	 * --Autowired：spring annotation方式注入对象。
	 * 此属性不必生成setter、getter方法，annotation方式注入已经做处理
	 * 注意：此对象必须添加注释选项@Service进行注入。且此类型必须整个context中没有重复类型
	 */
	@Autowired
	private LoginBusi busi;
	@Autowired
	private DealSso dealSso ;
	

	/**
	 * 用户登录
	 * @param name
	 * @param password
	 * @return
	 */
	public String login() {
		
		HttpServletRequest request = ServletActionContext.getRequest() ;
		UserVO userVo = this.busi.login(name, password, null) ;
		if(userVo == null){
			request.setAttribute(Constant.message, "登录失败！") ;
			request.setAttribute(SSOReferer.ParameterReferer , referer) ;
			return "input" ;
		}else{
			//登录成功
			String currIp = ServletActionContext.getRequest().getRemoteAddr() ;
			userVo.setCurrIp(currIp) ;
			
			//记录登录日志
			this.busi.loginLog(userVo) ;

			
			String sessionId = null ;
			if(userVo.getFixedIP() != null&&!userVo.getFixedIP().trim().equals("")){
				sessionId = userVo.getFixedIP() ;
			}else{
				sessionId = this.createSessionId() ;
			}
			
			userVo.setSsoSessionId(sessionId) ;
			
			this.dealSso.logined(userVo, sessionId) ;
			if (referer != null && !referer.equals("")) {
				//%26是&的转义
				referer += "%26" + SSOReferer.sessionId + "=" + sessionId ;
				return "redirectReferer" ;
			}else{
				return "succ" ;
			}
		}
	}
	
	//创建会话ID
	private synchronized String createSessionId(){
		return "" + System.nanoTime() ;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReferer() {
		return referer;
	}


	public void setReferer(String referer) {
		this.referer = referer;
	}

}
