package com.hxb.common.sso.session;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * 本类SessionUserVO定义的属性是单点登录控制所必须的。
 * 
 * 如果SessionUserVO的子类在本类属性基础上，还需要其他属性，可认自行定义必。
 * 但是后定义的属性在单点登录控制中的用户VO中不能以属性方式得到，
 * 可以把这些属性以名值对的方式放在SessionUserVO类的attributes集合
 * 属性中，在需要这些属性值的地方以以下例子方式得到。
 * 	 java.util.Hashtable<String, String> t = vo.getAttributes() ;
 * 	 if(t != null){
 * 	 	String roleName = (String)t.get("roleName") ;
 * 	 }
 *
 */

public class SessionUserVO   implements Serializable , SessionUserInterface {
	protected static final long serialVersionUID = -8095863075964700786L;

	protected String name;//帐号

	protected String password;//密码
	
	protected String fixedIP ;//用户常用(固定IP)

	protected String[] powers;
	
	protected Hashtable<String , String> attributes ;
	
	
	public SessionUserVO(){
		powers = new String[0] ;
		attributes = new Hashtable<String , String>() ;
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

	public String getFixedIP() {
		return fixedIP;
	}

	public void setFixedIP(String fixedIP) {
		this.fixedIP = fixedIP;
	}

	public String[] getPowers() {
		return powers;
	}

	public void setPowers(String[] powers) {
		this.powers = powers;
	}
	public Hashtable<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Hashtable<String, String> attributes) {
		this.attributes = attributes;
	}



}
