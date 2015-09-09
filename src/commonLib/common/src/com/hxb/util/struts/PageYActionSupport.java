package com.hxb.util.struts;

import java.util.Calendar;
import java.util.Date;

import com.hxb.util.DateTime;

public class PageYActionSupport extends PageActionSupport{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7377514779213427481L;
	
	@PropertieType
	public String con_year;
	

	public void firstRequest(){
		super.firstRequest() ;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		date.setTime(cal.getTimeInMillis());
		this.setCon_year(DateTime.yyyy(date));
	}


	public String getCon_year() {
		return con_year;
	}


	public void setCon_year(String con_year) {
		this.con_year = con_year;
	}
	
	
}
