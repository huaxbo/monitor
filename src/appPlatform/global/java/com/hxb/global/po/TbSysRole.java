package com.hxb.global.po;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * TbSysRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_sys_role")
public class TbSysRole implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2805812758057788140L;
	private String id;
	private String name;
	private String roleDesc;

	private Set<TbSysPower> tbSysPowers = new HashSet<TbSysPower>(0);
	private Set<TbSysLog> tbSysLogs = new HashSet<TbSysLog>(0);
	private Set<TbSysUser> tbSysUsers = new HashSet<TbSysUser>(0);

	
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

	@Column(name = "Name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "roleDesc", length = 200)
	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "tb_sys_rolePower", joinColumns = { @JoinColumn(name = "roleId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "pcd", nullable = false, updatable = false) })
	public Set<TbSysPower> getTbSysPowers() {
		return this.tbSysPowers;
	}

	public void setTbSysPowers(Set<TbSysPower> tbSysPowers) {
		this.tbSysPowers = tbSysPowers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tbSysRole")
	public Set<TbSysLog> getTbSysLogs() {
		return this.tbSysLogs;
	}

	public void setTbSysLogs(Set<TbSysLog> tbSysLogs) {
		this.tbSysLogs = tbSysLogs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tbSysRole")
	public Set<TbSysUser> getTbSysUsers() {
		return this.tbSysUsers;
	}

	public void setTbSysUsers(Set<TbSysUser> tbSysUsers) {
		this.tbSysUsers = tbSysUsers;
	}
	
}