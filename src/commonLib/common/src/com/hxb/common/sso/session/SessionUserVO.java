package com.hxb.common.sso.session;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * ����SessionUserVO����������ǵ����¼����������ġ�
 * 
 * ���SessionUserVO�������ڱ������Ի����ϣ�����Ҫ�������ԣ��������ж���ء�
 * ���Ǻ���������ڵ����¼�����е��û�VO�в��������Է�ʽ�õ���
 * ���԰���Щ��������ֵ�Եķ�ʽ����SessionUserVO���attributes����
 * �����У�����Ҫ��Щ����ֵ�ĵط����������ӷ�ʽ�õ���
 * 	 java.util.Hashtable<String, String> t = vo.getAttributes() ;
 * 	 if(t != null){
 * 	 	String roleName = (String)t.get("roleName") ;
 * 	 }
 *
 */

public class SessionUserVO   implements Serializable , SessionUserInterface {
	protected static final long serialVersionUID = -8095863075964700786L;

	protected String name;//�ʺ�

	protected String password;//����
	
	protected String fixedIP ;//�û�����(�̶�IP)

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
