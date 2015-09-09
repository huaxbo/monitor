package com.automic.sso.po;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PomfTbSysPower entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class PomfTbSysRolePowerId implements java.io.Serializable {

	// Fields

	private String roleId;
	private String pcd;

	// Constructors

	/** default constructor */
	public PomfTbSysRolePowerId() {
	}

	/** full constructor */
	public PomfTbSysRolePowerId(String roleId,String pcd) {
		this.roleId = roleId;
		this.pcd = pcd;
	}

	// Property accessors
	@Column(name = "pcd", nullable = false, length = 9)
	public String getPcd() {
		return this.pcd;
	}

	public void setPcd(String pcd) {
		this.pcd = pcd;
	}

	@Column(name = "roleId", nullable = false, length = 16)
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}