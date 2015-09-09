package com.hxb.monitor.web.remote.online;


import com.hxb.global.common.vo.CommonVO;
import com.hxb.global.common.vo.VoFetchedAnno;

public class OnlineVO extends CommonVO{
	
	/**
	 * ����id
	 */
	@VoFetchedAnno(true)
	private String id;
	/**
	 * ͨѶ����id
	 */
	@VoFetchedAnno(true)
	private String serverId;
	/**
	 * ����豸id
	 */
	@VoFetchedAnno(true)
	private String meterId;
	/**
	 * ����豸����
	 */
	@VoFetchedAnno(true)
	private String meterNm;
	/**
	 * ��ض��豸ͨѶЭ��
	 */
	@VoFetchedAnno(true)
	private String meterPro;
	
	/**
	 * ����״̬
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
