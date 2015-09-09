package com.hxb.global.po;

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
@Table(name = "tb_sys_power")
public class TbSysPower implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5562143042266816686L;
	private String pcd;
	private String pnm;
	private String pty;

	private Set<TbSysRole> tbSysRoles = new HashSet<TbSysRole>(0);
	

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
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tbSysPowers")
	public Set<TbSysRole> getPomfTbSysRoles() {
		return this.tbSysRoles;
	}

	public void setPomfTbSysRoles(Set<TbSysRole> tbSysRoles) {
		this.tbSysRoles = tbSysRoles;
	}

}