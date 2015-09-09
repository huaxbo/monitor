package com.hxb.global.web;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
public class GlobalAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2728822594565150569L;

	
	/**
	 * ·µ»Ø¿Õ°×Ò³
	 * @return
	 */
	public String blank(){
		
		return "blank";
	}
	
	
}
