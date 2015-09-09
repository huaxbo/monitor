package com.automic.sso.po;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * PomfTbSysPower entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pomf_tb_sys_power")
public class PomfTbSysPower implements java.io.Serializable {

	// Fields

	private String pcd;
	private String pnm;
	private String pty;

	private Set<PomfTbSysRole> pomfTbSysRoles = new HashSet<PomfTbSysRole>(0);
	// Constructors

	/** default constructor */
	public PomfTbSysPower() {
	}

	/** full constructor */
	public PomfTbSysPower(String pcd, String pnm, String pty,Set<PomfTbSysRole> pomfTbSysRoles) {
		this.pcd = pcd;
		this.pnm = pnm;
		this.pty = pty;
		this.pomfTbSysRoles = pomfTbSysRoles;
	}

	// Property accessors
	@Id
	@Column(name = "pcd", unique = true, nullable = false, length = 9)
	public String getPcd() {
		return this.pcd;
	}

	public void setPcd(String pcd) {
		this.pcd = pcd;
	}

	@Column(name = "pnm", nullable = false, length = 20)
	public String getPnm() {
		return this.pnm;
	}

	public void setPnm(String pnm) {
		this.pnm = pnm;
	}

	@Column(name = "pty", nullable = false, length = 2)
	public String getPty() {
		return this.pty;
	}

	public void setPty(String pty) {
		this.pty = pty;
	}
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pomfTbSysPowers")
	public Set<PomfTbSysRole> getPomfTbSysRoles() {
		return this.pomfTbSysRoles;
	}

	public void setPomfTbSysRoles(Set<PomfTbSysRole> pomfTbSysRoles) {
		this.pomfTbSysRoles = pomfTbSysRoles;
	}

}