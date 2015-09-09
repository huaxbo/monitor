package com.hxb.global.po;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * PomfTbSysLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_sys_log")
public class TbSysLog implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1029207318906484441L;
	// Fields

	private String id;
	private String roleId;
	private TbSysRole tbSysRole;
	private String userName;
	private Date optTm;
	private String optCnt;
	private String optIp;

	
	// Property accessors
	@Id
	@GenericGenerator(name="hxbIDG",strategy="com.hxb.util.IDGenerator")
	@GeneratedValue(generator="hxbIDG")
	@Column(name="id",unique=true,nullable=false,length=16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId", nullable = false,insertable=false,updatable=false)
	public TbSysRole getTbSysRole() {
		return this.tbSysRole;
	}

	public void setTbSysRole(TbSysRole tbSysRole) {
		this.tbSysRole = tbSysRole;
	}

	@Column(name = "userName", length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "optTm", nullable = false, length = 23)
	public Date getOptTm() {
		return this.optTm;
	}

	public void setOptTm(Date optTm) {
		this.optTm = optTm;
	}

	@Column(name = "optCnt", length = 200)
	public String getOptCnt() {
		return this.optCnt;
	}

	public void setOptCnt(String optCnt) {
		this.optCnt = optCnt;
	}

	@Column(name = "optIp", length = 20)
	public String getOptIp() {
		return this.optIp;
	}

	public void setOptIp(String optIp) {
		this.optIp = optIp;
	}

	@Column(name="roleId",length=16)
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}