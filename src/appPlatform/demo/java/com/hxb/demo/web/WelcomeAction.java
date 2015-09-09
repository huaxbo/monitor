package com.hxb.demo.web;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller("demoWelcomeAction")
public class WelcomeAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8251404299179776738L;
	
	
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute(){
		
		return "main";
	}

}
