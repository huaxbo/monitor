package com.hxb.util.system;

import com.hxb.util.ACConstant;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ��������������������ϵͳ����������
 * @author liu runyu
 *
 */
public class ReStartPCServer extends ActionSupport {

	private static final long serialVersionUID = 1010101010101010L;


	public String reStart()throws Exception {
		Runtime rt = Runtime.getRuntime();
		rt.exec("shutdown.exe   -r   -f   -t   00");     
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute(ACConstant.message, "�Ѿ�ִ��������������������") ;
		return SUCCESS ;
	}
}
