package com.hxb.monitor.web.manager.server;


import com.hxb.global.common.vo.CommonVO;
import com.hxb.global.common.vo.VoFetchedAnno;

public class ServerVO extends CommonVO{
	
	/**
	 * 主键id
	 */
	@VoFetchedAnno(true)
	private String id;
	/**
	 * 通讯服务名称
	 */
	@VoFetchedAnno(true)
	private String name;
	/**
	 * 本地通讯ip
	 */
	@VoFetchedAnno(true)
	private String ip;
	/**
	 * 本地通讯端口
	 */
	@VoFetchedAnno(true)
	private Integer port;
	/**
	 * 本地通讯服务名
	 */
	@VoFetchedAnno(true)
	private String context;
		
	/**
	 * 
	 */
	public ServerVO(){}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
		
	
	
}
