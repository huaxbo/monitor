package com.hxb.util.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.hxb.util.ACConstant;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 清理数据库日志，压缩数据库
 * @author liu runyu
 *
 */

public class CleanDataBaseLog  extends ActionSupport {

	private static final long serialVersionUID = 20202020202020L;

	private CleanDataBaseLogBusi busi ;
	private String dataBaseName ;//数据库名称
	
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
			request.setAttribute(ACConstant.message, "出错，数据库名称必须填写") ;
			return SUCCESS ;
		}
		this.busi.clean(this.dataBaseName) ;
		request.setAttribute(ACConstant.message, "已经执行清理日志压缩数据库命令") ;
		return SUCCESS ;
	}
	
	

}