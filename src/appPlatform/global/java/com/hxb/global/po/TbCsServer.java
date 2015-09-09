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
 * ͨѶ������Ϣ��
 */
@Entity
@Table(name = "tb_cs_server")
public class TbCsServer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4765771072785955142L;

	/**
	 * ����id
	 */
	private String id;
	/**
	 * ͨѶ��������
	 */
	private String name;
	/**
	 * ����ͨѶip
	 */
	private String ip;
	/**
	 * ����ͨѶ�˿�
	 */
	private Integer port;
	/**
	 * ����ͨѶ������
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
