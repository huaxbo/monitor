package com.hxb.global.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * @author Administrator
 * 通讯服务信息表
 */
@Entity
@Table(name = "tb_cs_server")
public class TbCsServer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4765771072785955142L;

	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 通讯服务名称
	 */
	private String name;
	/**
	 * 本地通讯ip
	 */
	private String ip;
	/**
	 * 本地通讯端口
	 */
	private Integer port;
	/**
	 * 本地通讯服务名
	 */
	private String context;
	
	@Id
	@GenericGenerator(name="hxbIDG",strategy="com.hxb.util.IDGenerator")
	@GeneratedValue(generator="hxbIDG")
	@Column(name="id",unique=true,nullable=false,length=17)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="name",length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="ip",length=15)
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Column(name="port")
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	@Column(name="context",length=20)
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
}
