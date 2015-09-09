package com.hxb.util.struts;

import java.util.Calendar;
import java.util.Date;

import com.hxb.util.DateTime;

public class PageYMDHActionSupport  extends PageDTActionSupport {

	private static final long serialVersionUID = 19000101123412L;
	
	public void firstRequest(){
		super.firstRequest() ;
		Date date = new Date();
		// set end date
		Calendar cal = Calendar.getInstance();
		date.setTime(cal.getTimeInMillis());
		this.setCon_endDt(DateTime.yyyy_mm_dd_hh(date));
		// set begin date
		cal.add(Calendar.DAY_OF_YEAR, -7);
		date.setTime(cal.getTimeInMillis());			
		this.setCon_startDt(DateTime.yyyy_mm_dd_hh(date));
	}
}
