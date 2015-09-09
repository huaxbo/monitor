package com.automic.sso.po;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * PomfTbSysOrg entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pomf_tb_sys_org")
public class PomfTbSysOrg implements java.io.Serializable {

	// Fields

	private String id;
	private String orgnm;
	private String desc;
	private Set<PomfTbSysRole> pomfTbSysRoles = new HashSet<PomfTbSysRole>(0);

	// Constructors

	/** default constructor */
	public PomfTbSysOrg() {
	}

	/** minimal constructor */
	public PomfTbSysOrg(String id, String orgnm) {
		this.id = id;
		this.orgnm = orgnm;
	}

	/** full constructor */
	public PomfTbSysOrg(String id, String orgnm, String desc,
			Set<PomfTbSysRole> pomfTbSysRoles) {
		this.id = id;
		this.orgnm = orgnm;
		this.desc = desc;
		this.pomfTbSysRoles = pomfTbSysRoles;
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

	@Column(name = "orgnm", nullable = false, length = 30)
	public String getOrgnm() {
		return this.orgnm;
	}

	public void setOrgnm(String orgnm) {
		this.orgnm = orgnm;
	}

	@Column(name = "desc", length = 200)
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pomfTbSysOrg")
	public Set<PomfTbSysRole> getPomfTbSysRoles() {
		return this.pomfTbSysRoles;
	}

	public void setPomfTbSysRoles(Set<PomfTbSysRole> pomfTbSysRoles) {
		this.pomfTbSysRoles = pomfTbSysRoles;
	}

}