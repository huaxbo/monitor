package com.hxb.monitor.commu.localServer;

public class LocalServerVO {

	
	/**
	 * @param ip
	 * @param port
	 * @param context
	 */
	public LocalServerVO(String ip,Integer port,String context){
		this.ip = ip;
		this.port = port;
		this.context = context;
	}
	
	private String ip;
	private Integer port;
	private String context;
	
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
