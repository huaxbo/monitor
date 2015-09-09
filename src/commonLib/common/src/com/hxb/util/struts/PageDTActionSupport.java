package com.hxb.util.struts;


public class PageDTActionSupport extends PageActionSupport {

	private static final long serialVersionUID = 190001011564545L;
	@PropertieType
	public String con_startDt ;
	@PropertieType
	public String con_endDt ;
	
	public void firstRequest(){
		super.firstRequest() ;
	}

	public String getCon_startDt() {
		return con_startDt;
	}

	public void setCon_startDt(String con_startDt) {
		this.con_startDt = con_startDt;
	}

	public String getCon_endDt() {
		return con_endDt;
	}

	public void setCon_endDt(String con_endDt) {
		this.con_endDt = con_endDt;
	}
	
	

}
