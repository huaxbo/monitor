package com.hxb.global.po;

import java.io.Serializable;

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
 * @author Administrator
 * 测控设备信息表
 */
@Entity
@Table(name = "tb_cs_meter")
public class TbCsMeter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8529961867395936661L;

	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 通讯服务id
	 */
	private String serverId;
	/**
	 * 测控设备名称
	 */
	private String meterNm;
	/**
	 * 测控设备id（编号）
	 */
	private String meterId;
	/**
	 * 测控设备通讯协议
	 */
	private String meterPro;
	/**
	 * 测控设备通讯协议驱动
	 */
	private String meterDriver;
	/**
	 * 通讯服务
	 */
	private TbCsServer server;
	
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
	@Column(name="serverId",length=17)
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	@Column(name="meterNm",length=50)
	public String getMeterNm() {
		return meterNm;
	}
	public void setMeterNm(String meterNm) {
		this.meterNm = meterNm;
	}
	@Column(name="meterId",length=20)
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	@Column(name="meterPro",length=20)
	public String getMeterPro() {
		return meterPro;
	}
	public void setMeterPro(String meterPro) {
		this.meterPro = meterPro;
	}
	@Column(name="meterDriver",length=200)
	public String getMeterDriver() {
		return meterDriver;
	}
	public void setMeterDriver(String meterDriver) {
		this.meterDriver = meterDriver;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="serverId",insertable=false,updatable=false)
	public TbCsServer getServer() {
		return server;
	}
	public void setServer(TbCsServer server) {
		this.server = server;
	}
	
}
