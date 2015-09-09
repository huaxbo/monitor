package com.hxb.global.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * PomfTbSysUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_sys_user")
public class TbSysUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2520399681754827066L;
	// Fields

	private String id;
	private String roleId;
	private TbSysRole tbSysRole;
	private String name;
	private String password;
	private String isAdmin;
	private String nameCn;
	private String fixedIp;
	private String fix;
	private String duty;
	private String tel;
	private String email;

	
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

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password", nullable = false, length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "isAdmin", nullable = false, length = 1)
	public String getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "nameCn", length = 50)
	public String getNameCn() {
		return this.nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	@Column(name = "fixedIp", length = 15)
	public String getFixedIp() {
		return this.fixedIp;
	}

	public void setFixedIp(String fixedIp) {
		this.fixedIp = fixedIp;
	}

	@Column(name = "fix", length = 1)
	public String getFix() {
		return this.fix;
	}

	public void setFix(String fix) {
		this.fix = fix;
	}

	@Column(name = "duty", length = 20)
	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Column(name = "tel", length = 20)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "email", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="roleid",length=16)
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}