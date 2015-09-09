package com.hxb.util.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.hxb.util.ACConstant;
import com.opensymphony.xwork2.ActionSupport;

/**
 * �������ݿ���־��ѹ�����ݿ�
 * @author liu runyu
 *
 */

public class CleanDataBaseLog  extends ActionSupport {

	private static final long serialVersionUID = 20202020202020L;

	private CleanDataBaseLogBusi busi ;
	private String dataBaseName ;//���ݿ�����
	
	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public CleanDataBaseLogBusi getBusi() {
		return busi;
	}

	public void setBusi(CleanDataBaseLogBusi busi) {
		this.busi = busi;
	}

	public String clean() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if(this.dataBaseName == null){
			request.setAttribute(ACConstant.message, "�������ݿ����Ʊ�����д") ;
			return SUCCESS ;
		}
		this.busi.clean(this.dataBaseName) ;
		request.setAttribute(ACConstant.message, "�Ѿ�ִ��������־ѹ�����ݿ�����") ;
		return SUCCESS ;
	}
	
	

}