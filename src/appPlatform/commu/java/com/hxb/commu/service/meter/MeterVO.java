package com.hxb.commu.service.meter;

import com.hxb.global.common.vo.CommonVO;
import com.hxb.global.common.vo.VoFetchedAnno;

public class MeterVO extends CommonVO {

	@VoFetchedAnno
	private String meterId;
	@VoFetchedAnno
	private String meterPro;
	@VoFetchedAnno
	private String meterDriver;
	
	
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public String getMeterPro() {
		return meterPro;
	}
	public void setMeterPro(String meterPro) {
		this.meterPro = meterPro;
	}
	public String getMeterDriver() {
		return meterDriver;
	}
	public void setMeterDriver(String meterDriver) {
		this.meterDriver = meterDriver;
	}
	
}
