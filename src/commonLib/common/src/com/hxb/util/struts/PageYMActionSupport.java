package com.hxb.util.struts;

import java.util.Calendar;
import java.util.Date;

import com.hxb.util.DateTime;

public class PageYMActionSupport  extends PageActionSupport {

	private static final long serialVersionUID = 19000101123412L;
	@PropertieType
	public String con_ym;
	

	public void firstRequest(){
		super.firstRequest() ;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		date.setTime(cal.getTimeInMillis());
		this.setCon_ym(DateTime.yyyy_MM(date));
	}


	public String getCon_ym() {
		return con_ym;
	}


	public void setCon_ym(String con_ym) {
		this.con_ym = con_ym;
	}
	

	
}
