package com.automic.sso.po;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * PomfTbSysRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pomf_tb_sys_role")
public class PomfTbSysRole implements java.io.Serializable {

	// Fields

	private String id;
	private PomfTbSysOrg pomfTbSysOrg;
	private String name;
	private String roleDesc;

	private Set<PomfTbSysPower> pomfTbSysPowers = new HashSet<PomfTbSysPower>(0);
	private Set<PomfTbSysLog> pomfTbSysLogs = new HashSet<PomfTbSysLog>(0);
	private Set<PomfTbSysUser> pomfTbSysUsers = new HashSet<PomfTbSysUser>(0);

	// Constructors

	/** default constructor */
	public PomfTbSysRole() {
	}

	/** minimal constructor */
	public PomfTbSysRole(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public PomfTbSysRole(String id, PomfTbSysOrg pomfTbSysOrg, String name,
			String roleDesc, Set<PomfTbSysPower> pomfTbSysPowers,
			Set<PomfTbSysLog> pomfTbSysLogs, Set<PomfTbSysUser> pomfTbSysUsers) {
		this.id = id;
		this.pomfTbSysOrg = pomfTbSysOrg;
		this.name = name;
		this.roleDesc = roleDesc;
		this.pomfTbSysPowers = pomfTbSysPowers;
		this.pomfTbSysLogs = pomfTbSysLogs;
		this.pomfTbSysUsers = pomfTbSysUsers;
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
	@JoinColumn(name = "orgid")
	public PomfTbSysOrg getPomfTbSysOrg() {
		return this.pomfTbSysOrg;
	}

	public void setPomfTbSysOrg(PomfTbSysOrg pomfTbSysOrg) {
		this.pomfTbSysOrg = pomfTbSysOrg;
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
	@JoinTable(name = "pomf_tb_sys_rolePower", joinColumns = { @JoinColumn(name = "roleId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "pcd", nullable = false, updatable = false) })
	public Set<PomfTbSysPower> getPomfTbSysPowers() {
		return this.pomfTbSysPowers;
	}

	public void setPomfTbSysPowers(Set<PomfTbSysPower> pomfTbSysPowers) {
		this.pomfTbSysPowers = pomfTbSysPowers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pomfTbSysRole")
	public Set<PomfTbSysLog> getPomfTbSysLogs() {
		return this.pomfTbSysLogs;
	}

	public void setPomfTbSysLogs(Set<PomfTbSysLog> pomfTbSysLogs) {
		this.pomfTbSysLogs = pomfTbSysLogs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pomfTbSysRole")
	public Set<PomfTbSysUser> getPomfTbSysUsers() {
		return this.pomfTbSysUsers;
	}

	public void setPomfTbSysUsers(Set<PomfTbSysUser> pomfTbSysUsers) {
		this.pomfTbSysUsers = pomfTbSysUsers;
	}

}