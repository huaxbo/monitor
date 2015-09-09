package com.automic.sso.po;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;

import org.hibernate.annotations.Entity;

/**
 * PomfTbSysPower entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pomf_tb_sys_rolePower")
public class PomfTbSysRolePower implements java.io.Serializable {

	private PomfTbSysRolePowerId id;

	/** default constructor */
	public PomfTbSysRolePower(){
		
	}
	/** minimal constructor */
	public PomfTbSysRolePower(PomfTbSysRolePowerId id){
		this.id = id;
	}
	
	 // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="roleId", column=@Column(name="roleId", nullable=false, length=16) ), 
        @AttributeOverride(name="pcd", column=@Column(name="pcd", nullable=false, length=9) ) } )

	public PomfTbSysRolePowerId getId() {
		return id;
	}

	public void setId(PomfTbSysRolePowerId id) {
		this.id = id;
	}

	
}