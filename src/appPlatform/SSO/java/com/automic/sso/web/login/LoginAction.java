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
 * --Aspect������springAOP,��ʵ�����淽ʽȨ����֤��������־�洢����
 * --Controller������spring annotation��ʽע��Action������ֵΪAction��ע��id����struts�����ļ���ָ��spring����
 * --Scope������Ϊע��������÷�Χ������request��Ч
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
	 * ����busi�����
	 * --Autowired��spring annotation��ʽע�����
	 * �����Բ�������setter��getter������annotation��ʽע���Ѿ�������
	 * ע�⣺�˶���������ע��ѡ��@Service����ע�롣�Ҵ����ͱ�������context��û���ظ�����
	 */
	@Autowired
	private LoginBusi busi;
	@Autowired
	private DealSso dealSso ;
	

	/**
	 * �û���¼
	 * @param name
	 * @param password
	 * @return
	 */
	public String login() {
		
		HttpServletRequest request = ServletActionContext.getRequest() ;
		UserVO userVo = this.busi.login(name, password, null) ;
		if(userVo == null){
			request.setAttribute(Constant.message, "��¼ʧ�ܣ�") ;
			request.setAttribute(SSOReferer.ParameterReferer , referer) ;
			return "input" ;
		}else{
			//��¼�ɹ�
			String currIp = ServletActionContext.getRequest().getRemoteAddr() ;
			userVo.setCurrIp(currIp) ;
			
			//��¼��¼��־
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
				//%26��&��ת��
				referer += "%26" + SSOReferer.sessionId + "=" + sessionId ;
				return "redirectReferer" ;
			}else{
				return "succ" ;
			}
		}
	}
	
	//�����ỰID
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
