package com.hxb.util.system;

import com.hxb.util.ACConstant;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 重新启动服务器，操作系统级重新启动
 * @author liu runyu
 *
 */
public class ReStartPCServer extends ActionSupport {

	private static final long serialVersionUID = 1010101010101010L;


	public String reStart()throws Exception {
		Runtime rt = Runtime.getRuntime();
		rt.exec("shutdown.exe   -r   -f   -t   00");     
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute(ACConstant.message, "已经执行重新启动服务器命令") ;
		return SUCCESS ;
	}
}
