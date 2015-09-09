package com.hxb.monitor.web.remote.online;


import com.hxb.global.common.vo.CommonVO;
import com.hxb.global.common.vo.VoFetchedAnno;

public class OnlineVO extends CommonVO{
	
	/**
	 * 主键id
	 */
	@VoFetchedAnno(true)
	private String id;
	/**
	 * 通讯服务id
	 */
	@VoFetchedAnno(true)
	private String serverId;
	/**
	 * 测控设备id
	 */
	@VoFetchedAnno(true)
	private String meterId;
	/**
	 * 测控设备名称
	 */
	@VoFetchedAnno(true)
	private String meterNm;
	/**
	 * 测控额设备通讯协议
	 */
	@VoFetchedAnno(true)
	private String meterPro;
	
	/**
	 * 在线状态
	 */
	private String online;
	
	
	/**
	 * 
	 */
	public OnlineVO(){}
	
		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getServerId() {
		return serverId;
	}


	public void setServerId(String serverId) {
		this.serverId = serverId;
	}


	public String getMeterId() {
		return meterId;
	}


	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}


	public String getMeterNm() {
		return meterNm;
	}


	public void setMeterNm(String meterNm) {
		this.meterNm = meterNm;
	}


	public String getMeterPro() {
		return meterPro;
	}


	public void setMeterPro(String meterPro) {
		this.meterPro = meterPro;
	}


	public String getOnline() {
		return online;
	}


	public void setOnline(String online) {
		this.online = online;
	}

}
