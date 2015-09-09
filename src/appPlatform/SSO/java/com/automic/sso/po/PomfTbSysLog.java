package com.automic.sso.po;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.automic.util.DateTime;

/**
 * PomfTbSysLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pomf_tb_sys_log")
public class PomfTbSysLog implements java.io.Serializable {

	// Fields

	private String id;
	private String roleId;
	private PomfTbSysRole pomfTbSysRole;
	private String userName;
	private Date optTm;
	private String optCnt;
	private String optIp;

	//非数据库属性
	/** 
	* @Fields optTm_str : TODO(操作时间别称) 
	*/ 
	private String optTm_str;
	// Constructors
	
	/** default constructor */
	public PomfTbSysLog() {
	}

	/** minimal constructor */
	public PomfTbSysLog(String id, PomfTbSysRole pomfTbSysRole, Date optTm) {
		this.id = id;
		this.pomfTbSysRole = pomfTbSysRole;
		this.optTm = optTm;
	}

	/** full constructor */
	public PomfTbSysLog(String id, PomfTbSysRole pomfTbSysRole,
			String userName, Date optTm, String optCnt, String optIp) {
		this.id = id;
		this.pomfTbSysRole = pomfTbSysRole;
		this.userName = userName;
		this.optTm = optTm;
		this.optCnt = optCnt;
		this.optIp = optIp;
	}

	// Property accessors
	@Id
	@GenericGenerator(name="automicIDG",strategy="com.automic.util.IDGenerator")
	@GeneratedValue(generator="automicIDG")
	@Column(name="id",unique=true,nullable=false,length=16)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId", nullable = false,insertable=false,updatable=false)
	public PomfTbSysRole getPomfTbSysRole() {
		return this.pomfTbSysRole;
	}

	public void setPomfTbSysRole(PomfTbSysRole pomfTbSysRole) {
		this.pomfTbSysRole = pomfTbSysRole;
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

	@Transient
	public String getOptTm_str() {
		if(optTm != null){
			optTm_str = DateTime.yyyy_mm_dd_hh_mm_ss(optTm);
		}
		return optTm_str;
	}

	public void setOptTm_str(String optTm_str) {
		this.optTm_str = optTm_str;
	}

}