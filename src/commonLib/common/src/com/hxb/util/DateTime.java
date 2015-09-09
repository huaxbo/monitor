package com.hxb.util;


import java.util.*;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar ;

public class DateTime {
	private DateTime() {
	}
	

	public static String yyyy() {
		return new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()));
	}
	public static String yy() {
		return new SimpleDateFormat("yy").format(new Date(System.currentTimeMillis()));
	}

	public static String MM() {
		return new SimpleDateFormat("MM").format(new Date(System.currentTimeMillis()));
	}

	public static String dd() {
		return new SimpleDateFormat("dd").format(new Date(System.currentTimeMillis()));
	}

	public static String HH() {
		return new SimpleDateFormat("HH").format(new Date(System.currentTimeMillis()));
	}

	public static String mm() {
		return new SimpleDateFormat("mm").format(new Date(System.currentTimeMillis()));
	}

	public static String ss() {
		return new SimpleDateFormat("ss").format(new Date(System.currentTimeMillis()));
	}

	public static String yyyy_MM_dd_HH_mm_ss() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
	}	
	public static String yyyy_MM_dd_HH_mm() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis());
	}
	public static String yyyy_MM_dd_HH() {
		return new SimpleDateFormat("yyyy-MM-dd HH").format(System.currentTimeMillis());
	}
	public static String yyyy_MM_dd() {
		return new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
	}
	public static String yyyy_MM() {
		return new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
	}
	public static String yyyy(Date date) {
		return new SimpleDateFormat("yyyy").format(date);
	}
	public static String yyyy_MM(Date date) {
		return new SimpleDateFormat("yyyy-MM").format(date);
	}
	public static String yyyy_MM_dd(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	public static String yyyy_mm_dd_hh(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH").format(date);
	}
	public static String yyyy_mm_dd_hh_mm(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}
	public static String yyyy_mm_dd_hh_mm_ss(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	public static Date yyyy_MM_dd_hh(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH").parse(date);
		} catch (Exception e) {
			date = "2000-01-01 00" ;
			try{
			   return new SimpleDateFormat("yyyy-MM-dd HH").parse(date);
			}catch (Exception ee) {
				return null ;
			}
		}
	}
	/** 
	* @Title: yyyy 
	* @Description: TODO(字符串转换为日期类型) 
	* @return Date    返回类型 
	* @throws 
	*/ 
	public static Date yyyy(String date) {
		try {
			return new SimpleDateFormat("yyyy").parse(date);
		} catch (Exception e) {
			date = "2000" ;
			try{
			   return new SimpleDateFormat("yyyy").parse(date);
			}catch (Exception ee) {
				return null ;
			}
		}
	}
	/** 
	* @Title: yyyy_MM 
	* @Description: TODO(字符串转换为日期类型) 
	* @return Date    返回类型 
	* @throws 
	*/ 
	public static Date yyyy_MM(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM").parse(date);
		} catch (Exception e) {
			date = "2000-01" ;
			try{
			   return new SimpleDateFormat("yyyy-MM").parse(date);
			}catch (Exception ee) {
				return null ;
			}
		}
	}
	public static Date yyyy_MM_dd(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (Exception e) {
			date = "2000-01-01" ;
			try{
			   return new SimpleDateFormat("yyyy-MM-dd").parse(date);
			}catch (Exception ee) {
				return null ;
			}
		}
	}
	/** 
	* @Title: yyyy_MM_dd_HH_mm_ss 
	* @Description: TODO(字符串转换为日期类型) 
	* @return Date    返回类型 
	* @throws 
	*/ 
	public static Date yyyy_MM_dd_HH_mm_ss(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		} catch (Exception e) {
			date = "2000-01-01 00:00:00" ;
			try{
			   return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
			}catch (Exception ee) {
				return null ;
			}
		}
	}
	public static String week(){
		return new SimpleDateFormat("EE").format(System.currentTimeMillis()); 	
	}

	
	public static String yyyyMMddhhmmss() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System
				.currentTimeMillis()));
	}
	public static String yyyyMMdd_hh_mm() {
		return new SimpleDateFormat("yyyyMMdd HH:mm").format(new Date(System
				.currentTimeMillis()));
	}
	public static String yyMMddhhmmss() {
		return new SimpleDateFormat("yyMMddHHmmss").format(new Date(System
				.currentTimeMillis()));
	}
	public static String yyMMddhhmmss(Date date) {
		return new SimpleDateFormat("yyMMddHHmmss").format(date);
	}
	public static String yyMMdd() {
		return new SimpleDateFormat("yyMMdd").format(new Date(System
				.currentTimeMillis()));
	}
	public static String yyyyMM() {
		return new SimpleDateFormat("yyyyMM").format(new Date(System
				.currentTimeMillis()));
	}
	public static String yyyyMMdd() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date(System
				.currentTimeMillis()));
	}
	public static String yyyyMMdd(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	public static String hhmmss() {
		return new SimpleDateFormat("HHmmss").format(new Date(System
				.currentTimeMillis()));
	}
	public static String hh_mm_ss() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date(System
				.currentTimeMillis()));
	}
	
	
	public static Date dateFrom_yyyy_MM_dd_HH_mm_ss(String ymdhms) throws Exception {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ymdhms);
	}
	public static Date dateFrom_yyyy_MM_dd_HH_mm(String ymdhm) throws Exception {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(ymdhm);
	}
	public static Date dateFrom_yyyy_MM_dd_HH(String ymdh) throws Exception {
		return new SimpleDateFormat("yyyy-MM-dd HH").parse(ymdh);
	}
	public static Date dateFrom_yyyy_MM_dd(String ymd) throws Exception {
		return new SimpleDateFormat("yyyy-MM-dd").parse(ymd);
	}
	/**
	 * 两个日期间相差分钟数
	 * @param dtStr1
	 * @param dtStr2
	 * @return
	 * @throws Exception
	 */
	public static long minutesBetweenYmdhm(String dtStr1 , String dtStr2)throws Exception{
		return ((dateFrom_yyyy_MM_dd_HH_mm(dtStr1).getTime()-dateFrom_yyyy_MM_dd_HH_mm(dtStr2).getTime())/1000)/60 ;
	}
	/**
	 * 两个日期相差小时数
	 * @param dtStr1
	 * @param dtStr2
	 * @return
	 * @throws Exception
	 */
	public static long hoursBetweenYmdh(String dtStr1 , String dtStr2)throws Exception{
		return (((dateFrom_yyyy_MM_dd_HH(dtStr1).getTime()-dateFrom_yyyy_MM_dd_HH(dtStr2).getTime())/1000)/60)/60 ;
	}
	

	/**
	 * 字符串型日期(2009-09-10)转成年月日数组
	 * 此方法比yyyy_MM_dd_2_ymdGroup_2慢得多
	 * @param yyyy_MM_DD
	 * @return
	 */
//	@SuppressWarnings("deprecation")
//	public static int[] yyyy_MM_dd_2_ymdGroup_1(String yyyy_MM_DD){
//		Date d = null ;
//		try{
//			d = new SimpleDateFormat("yyyy-MM-dd").parse(yyyy_MM_DD);
//		}catch (Exception e) {
//			d = new Date() ;
//			e.printStackTrace() ;
//		}
//		return new int[]{d.getYear() , d.getMonth() , d.getDate()} ;
//	}
	/**
	 * 字符串型日期(2009-09-10)转成年月日数组
	 * @param yyyy_MM_DD
	 * @return
	 */
	public static int[] yyyy_MM_dd_2_ymdGroup(String yyyy_MM_DD){
		int y = Integer.parseInt(yyyy_MM_DD.substring(0 , 4)) ;
		int m = Integer.parseInt(yyyy_MM_DD.substring(5 , 7)) ;
		int d = Integer.parseInt(yyyy_MM_DD.substring(8 , 10)) ;
		return new int[]{y,m,d} ;
	}
	/**
	 * 字符串型日期(2009-09-10 00)转成年月日时数组
	 * @param yyyy_MM_DD
	 * @return
	 */
	public static int[] yyyy_MM_dd_HH_2_ymdhGroup(String yyyy_MM_DD_HH){
		int y = Integer.parseInt(yyyy_MM_DD_HH.substring(0 , 4)) ;
		int m = Integer.parseInt(yyyy_MM_DD_HH.substring(5 , 7)) ;
		int d = Integer.parseInt(yyyy_MM_DD_HH.substring(8 , 10)) ;
		int h = Integer.parseInt(yyyy_MM_DD_HH.substring(11 , 13)) ;
		return new int[]{y,m,d,h} ;
	}
	/**
	 * 字符串型日期(2009-09-10 00:00)转成年月日时分数组
	 * @param yyyy_MM_DD
	 * @return
	 */
	public static int[] yyyy_MM_dd_HH_MM_2_ymdhGroup(String yyyy_MM_DD_HH_MM){
		int y = Integer.parseInt(yyyy_MM_DD_HH_MM.substring(0 , 4)) ;
		int m = Integer.parseInt(yyyy_MM_DD_HH_MM.substring(5 , 7)) ;
		int d = Integer.parseInt(yyyy_MM_DD_HH_MM.substring(8 , 10)) ;
		int h = Integer.parseInt(yyyy_MM_DD_HH_MM.substring(11 , 13)) ;
		int mm = Integer.parseInt(yyyy_MM_DD_HH_MM.substring(14 , 16)) ;
		return new int[]{y,m,d,h,mm} ;
	}
	/**
	 * 字符串型日期(2009-09-10 00:00)转成年月日时分秒数组
	 * @param yyyy_MM_DD
	 * @return
	 */
	public static int[] yyyy_MM_dd_HH_MM_SS_2_ymdhGroup(String yyyy_MM_DD_HH_MM_SS){
		int y = Integer.parseInt(yyyy_MM_DD_HH_MM_SS.substring(0 , 4)) ;
		int m = Integer.parseInt(yyyy_MM_DD_HH_MM_SS.substring(5 , 7)) ;
		int d = Integer.parseInt(yyyy_MM_DD_HH_MM_SS.substring(8 , 10)) ;
		int h = Integer.parseInt(yyyy_MM_DD_HH_MM_SS.substring(11 , 13)) ;
		int mm = Integer.parseInt(yyyy_MM_DD_HH_MM_SS.substring(14 , 16)) ;
		int s = Integer.parseInt(yyyy_MM_DD_HH_MM_SS.substring(17 , 19)) ;
		return new int[]{y,m,d,h,mm,s} ;
	}
	

	
	/**
	 * 得到明天
	 * @return
	 */
	public static String tomorrow_ymd(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, +1);
		date.setTime(cal.getTimeInMillis());			
		return DateTime.yyyy_MM_dd(date) ;
	}
	/**
	 * 得到明天
	 * @return
	 */
	public static String tomorrow_yyyy_MM_dd(int thisYear , int thisMonth , int thisDay){
		int tomorrow = 0 ;
		int year = 0 ;
		int month = 0 ;
		if(thisDay == 31){
			if(thisMonth == 1 || thisMonth == 3 || thisMonth == 5 || thisMonth == 7 || thisMonth == 8 || thisMonth == 10){
				year = thisYear ;
				month = thisMonth + 1 ;
				tomorrow = 1 ;
			}else if(thisMonth == 12){
				year = thisYear + 1 ;
				month = 1 ;
				tomorrow = 1 ;
			}else{
				//不存在这种情况
			}
		}else if(thisDay == 30){
			if(thisMonth == 4 || thisMonth == 6 || thisMonth == 9 || thisMonth == 11){
				year = thisYear ;
				month = thisMonth + 1 ;
				tomorrow = 1 ;
			}else{
				year = thisYear ;
				month = thisMonth ;
				tomorrow = 31 ;
			}
		}else if(thisDay == 29){
			if(thisMonth == 2){
				year = thisYear ;
				month = thisMonth + 1 ;
				tomorrow = 1 ;
			}else{
				year = thisYear ;
				month = thisMonth ;
				tomorrow = 30 ;
			}
		}else if(thisDay == 28){
			if(thisMonth == 2){
				if(!isLeapYear(thisYear)){
					year = thisYear ;
					month = thisMonth + 1 ;
					tomorrow = 1 ;
				}else{
					year = thisYear ;
					month = thisMonth ;
					tomorrow = 29 ;
				}
			}else{
				year = thisYear ;
				month = thisMonth ;
				tomorrow = 29 ;
			}
		}else{
			year = thisYear ;
			month = thisMonth ;
			tomorrow = thisDay + 1 ;
		}
		return year + "-" 
			+ (month > 9?(month + ""):("0" + month)) + "-" 
			+ (tomorrow > 9?(tomorrow + ""):("0" + tomorrow)) ;
	}
	
	
	/**
	 * 得到上一小时所对应的日期
	 * 2009-01-01 00:01:01
	 * @return
	 */
	public static String lastHour_YMDHMS(String YMDHMS){
		if(YMDHMS.length() != 19){
			return null ;
		}
		int y = Integer.parseInt(YMDHMS.substring(0 , 4)) ;
		int m = Integer.parseInt(YMDHMS.substring(5 , 7)) ;
		int d = Integer.parseInt(YMDHMS.substring(8 , 10)) ;
		int h = Integer.parseInt(YMDHMS.substring(11 , 13)) ;
		int f = Integer.parseInt(YMDHMS.substring(14 , 16)) ;
		int s = Integer.parseInt(YMDHMS.substring(17 , 19)) ;

		String r = "" ;
		if(h == 0){
			r = yesterday_yyyy_MM_dd(y , m , d) ;
			h = 23 ;
		}else{
			r = YMDHMS.substring(0 , 10) ;
		}
		r += " " 
			+(h > 9?(h + ""):("0" + h)) + ":" 
			+ (f > 9?(f + ""):("0" + f)) + ":" 
			+ (s > 9?(s + ""):("0" + s)) ;
		return r ;
	}
	/**
	 * 得到上一小时
	 * @return
	 */
	public static String lastHour_yyyy_MM_dd_HH(int thisYear , int thisMonth , int thisDay , int thisHour){
		String ymdh = null ;
		if(thisHour == 0){
			ymdh = yesterday_yyyy_MM_dd(thisYear , thisMonth , thisDay) + " 23"  ;
		}else{
			ymdh = thisYear + "-" 
			+ (thisMonth > 9?(thisMonth + ""):("0" + thisMonth)) + "-" 
			+ (thisDay > 9?(thisDay + ""):("0" + thisDay)) 
			+ " " + ((thisHour - 1) > 9?((thisHour - 1) + ""):("0" + (thisHour - 1))) ;
		}
		return ymdh ;
	}
	
	/**
	 * 得到前x小时对应的日期
	 * @param ymdhm
	 * @return
	 */
	public static String lastXHour_yyyy_MM_dd_HH(String ymdh , int xhour){
		Date date = null ;
		Date d = null ;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH").parse(ymdh);
		} catch (Exception e) {
			try {
				d = new SimpleDateFormat("yyyy-MM-dd HH").parse(yyyy_MM_dd_HH_mm_ss());
			} catch (Exception ee) {
			}
		}finally{
			Calendar cal = Calendar.getInstance();  
			cal.setTime(d);			
			cal.add(Calendar.HOUR_OF_DAY, -xhour);
			date = cal.getTime() ;
		}
		return DateTime.yyyy_mm_dd_hh_mm(date) ;
	}
	/** 
	* @Title: lastXHour_yyyy_MM_dd_HH
	* @Description: TODO(上n个小时日期) 
	* @return Date    返回类型 
	* @throws 
	*/ 
	public static Date lastXHour_yyyy_MM_dd_HH(Date dt,int xhour){
		if(dt == null){
			return null;
		}
		Date date = null;
		Calendar cal = Calendar.getInstance();  
		cal.setTime(dt);			
		cal.add(Calendar.HOUR_OF_DAY, -xhour);
		date = cal.getTime() ;
		
		return DateTime.yyyy_MM_dd_hh(DateTime.yyyy_mm_dd_hh(date));
	}
	/**
	 * 得到前x小时对应的日期
	 * @param ymdhm
	 * @return
	 */
	public static String lastXHour_yyyy_MM_dd_HH_mm(String ymdhm , int xhour){
		Date date = null ;
		Date d = null ;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(ymdhm);
		} catch (Exception e) {
			try {
				d = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(yyyy_MM_dd_HH_mm_ss());
			} catch (Exception ee) {
			}
		}finally{
			Calendar cal = Calendar.getInstance();  
			cal.setTime(d);			
			cal.add(Calendar.HOUR_OF_DAY, -xhour);
			date = cal.getTime() ;
		}
		return DateTime.yyyy_mm_dd_hh_mm(date) ;
	}	
	/**
	 * 得到前x小时对应的日期
	 * @param ymdhm
	 * @return
	 */
	public static String lastXHour_yyyy_MM_dd_HH_mm_ss(String ymdhms , int xhour){
		Date date = null ;
		Date d = null ;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ymdhms);
		} catch (Exception e) {
			try {
				d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(yyyy_MM_dd_HH_mm_ss());
			} catch (Exception ee) {
			}
		}finally{
			Calendar cal = Calendar.getInstance();  
			cal.setTime(d);			
			cal.add(Calendar.HOUR_OF_DAY, -xhour);
			date = cal.getTime() ;
		}
		return DateTime.yyyy_mm_dd_hh_mm(date) ;
	}

	/**
	 * 得到后x小时对应的日期
	 * @param ymdhm
	 * @return
	 */
	public static String nextXHour_yyyy_MM_dd_HH(String ymdh , int xhour){
		Date date = null ;
		Date d = null ;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH").parse(ymdh);
		} catch (Exception e) {
			try {
				d = new SimpleDateFormat("yyyy-MM-dd HH").parse(yyyy_MM_dd_HH_mm_ss());
			} catch (Exception ee) {
			}
		}finally{
			Calendar cal = Calendar.getInstance();  
			cal.setTime(d);			
			cal.add(Calendar.HOUR_OF_DAY, xhour);
			date = cal.getTime() ;
		}
		return DateTime.yyyy_mm_dd_hh_mm(date) ;
	}
	/** 
	* @Title: lastXHour_yyyy_MM_dd_HH
	* @Description: TODO(下n个小时日期) 
	* @return Date    返回类型 
	* @throws 
	*/ 
	public static Date nextXHour_yyyy_MM_dd_HH(Date dt,int xhour){
		if(dt == null){
			return null;
		}
		Date date = null;
		Calendar cal = Calendar.getInstance();  
		cal.setTime(dt);			
		cal.add(Calendar.HOUR_OF_DAY, xhour);
		date = cal.getTime() ;
		
		return DateTime.yyyy_MM_dd_hh(DateTime.yyyy_mm_dd_hh(date));
	}
	/**
	 * 得到后x小时对应的日期
	 * @param ymdhm
	 * @return
	 */
	public static String nextXHour_yyyy_MM_dd_HH_mm(String ymdhm , int xhour){
		Date date = null ;
		Date d = null ;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(ymdhm);
		} catch (Exception e) {
			try {
				d = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(yyyy_MM_dd_HH_mm_ss());
			} catch (Exception ee) {
			}
		}finally{
			Calendar cal = Calendar.getInstance();  
			cal.setTime(d);			
			cal.add(Calendar.HOUR_OF_DAY, xhour);
			date = cal.getTime() ;
		}
		return DateTime.yyyy_mm_dd_hh_mm(date) ;
	}
	/**
	 * 得到后x小时对应的日期
	 * @param ymdhm
	 * @return
	 */
	public static String nextXHour_yyyy_MM_dd_HH_mm_ss(String ymdhms , int xhour){
		Date date = null ;
		Date d = null ;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ymdhms);
		} catch (Exception e) {
			try {
				d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(yyyy_MM_dd_HH_mm_ss());
			} catch (Exception ee) {
			}
		}finally{
			Calendar cal = Calendar.getInstance();  
			cal.setTime(d);			
			cal.add(Calendar.HOUR_OF_DAY, xhour);
			date = cal.getTime() ;
		}
		return DateTime.yyyy_mm_dd_hh_mm(date) ;
	}



	/**
	 * 得到x小时前对应的日期(年月日时)
	 * @return
	 */
	public static String lastXHour_yyyy_MM_dd_HH(int xhour){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -xhour);
		date.setTime(cal.getTimeInMillis());			
		return DateTime.yyyy_mm_dd_hh(date) ;
	}
	/**
	 * 得到x小时前对应的日期(年月日时分)
	 * @return
	 */
	public static String lastXHour_yyyy_MM_dd_HH_mm(int xhour){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -xhour);
		date.setTime(cal.getTimeInMillis());			
		return DateTime.yyyy_mm_dd_hh_mm(date) ;
	}
	/**
	 * 得到x小时前对应的日期(年月日时分秒)
	 * @return
	 */
	public static String lastXHour_yyyy_MM_dd_HH_mm_ss(int xhour){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, -xhour);
		date.setTime(cal.getTimeInMillis());			
		return DateTime.yyyy_mm_dd_hh_mm_ss(date) ;
	}
	/**
	 * 得到x小时前对应的日期(年月日时分秒)
	 * @return
	 */
	public static String lastXMinute_yyyy_MM_dd_HH_mm_ss(int xminute){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		long now = cal.getTimeInMillis() ;
		long last = now - (xminute * 60 * 1000) ;
		date.setTime(last);			
		return DateTime.yyyy_mm_dd_hh_mm_ss(date) ;
	}
	
	/**
	 * 得到x小时后对应的日期(年月日时)
	 * @return
	 */
	public static String nextXHour_yyyy_MM_dd_HH(int xhour){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, xhour);
		date.setTime(cal.getTimeInMillis());			
		return DateTime.yyyy_mm_dd_hh(date) ;
	}
	/**
	 * 得到x小时后对应的日期(年月日时分)
	 * @return
	 */
	public static String nextXHour_yyyy_MM_dd_HH_mm(int xhour){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, xhour);
		date.setTime(cal.getTimeInMillis());			
		return DateTime.yyyy_mm_dd_hh_mm(date) ;
	}
	/**
	 * 得到x小时后对应的日期(年月日时分秒)
	 * @return
	 */
	public static String nextXHour_yyyy_MM_dd_HH_mm_ss(int xhour){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, xhour);
		date.setTime(cal.getTimeInMillis());			
		return DateTime.yyyy_mm_dd_hh_mm_ss(date) ;
	}
	
	/**
	 * 得到昨天
	 * @return
	 */
	public static String yesterday_ymd(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		date.setTime(cal.getTimeInMillis());			
		return DateTime.yyyy_MM_dd(date) ;
	}
	
	/**
	 * 得到昨天
	 * @return
	 */
	public static String yesterday_yyyy_MM_dd(int thisYear , int thisMonth , int thisDay){
		int yesterday = 0 ;
		int lastYear = 0 ;
		int lastMonth = 0 ;
		if(thisDay == 1){
			if(thisMonth == 1 || thisMonth == 2 || thisMonth == 4 || thisMonth == 6 || thisMonth == 8 || thisMonth == 9 || thisMonth == 11 ){
				yesterday = 31 ;
			}else if( thisMonth == 3){
				if(isLeapYear(thisYear)){
					yesterday = 29 ;
				}else{
					yesterday = 28 ;
				}
			}else {
				yesterday = 30 ;
			}
			
			if(thisMonth == 1){
				lastYear = thisYear - 1 ;
				lastMonth = 12 ;
			}else{
				lastYear = thisYear ;
				lastMonth = thisMonth - 1 ; 
			}
		}else{
			yesterday = thisDay - 1 ;
			lastMonth = thisMonth ; 
			lastYear = thisYear ;
		}
		return lastYear + "-" 
			+ (lastMonth > 9?(lastMonth + ""):("0" + lastMonth)) + "-" 
			+ (yesterday > 9?(yesterday + ""):("0" + yesterday)) ;
		
	}
	/**
	 * 得到上月份
	 * @return
	 */
	public static String lastMonth_ym(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -((Integer.parseInt(dd()))));
		date.setTime(cal.getTimeInMillis());			
		return DateTime.yyyy_MM(date) ;
	}
	/**
	 * 得到上月份
	 * @return
	 */
	public static String lastMonth_ym(int thisMonth){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		if(thisMonth == 2){
			cal.add(Calendar.DAY_OF_YEAR, - 30);
		}else{
			cal.add(Calendar.DAY_OF_YEAR, - 32);
		}
		date.setTime(cal.getTimeInMillis());			
		return DateTime.yyyy_MM(date) ;
	}
	/**
	 * 得到上月份
	 * @return
	 */
	public static String lastMonth_ym(int year , int thisMonth){
		thisMonth = thisMonth - 1 ;
		if(thisMonth == 0){
			year = year - 1 ;
			thisMonth = 12 ;
		}
		return  ("" + year) + "-" + (thisMonth<10?("0"+thisMonth):(""+thisMonth)) ;
	}
	/**
	 * 得到上月份
	 * @return
	 */
	public static String lastMonth_ymd(int year , int thisMonth , int thisDate){
		thisMonth = thisMonth - 1 ;
		if(thisMonth == 0){
			year = year - 1 ;
			thisMonth = 12 ;
		}
		if(thisMonth == 2){
			if(isLeapYear(year)){
				if(thisDate > 29){
					thisDate = 29 ;
				}
			}else{
				if(thisDate > 28){
					thisDate = 28 ;
				}
			}
		}else if(thisMonth == 4 || thisMonth == 6 || thisMonth == 9 || thisMonth == 11){
			if(thisDate > 30){
				thisDate = 30 ;
			}
			
		}
		return  ("" + year) + "-" + (thisMonth<10?("0"+thisMonth):(""+thisMonth)) + "-" + (thisDate<10?("0"+thisDate):(""+thisDate)) ;
	}
	/**
	 * 得到上上月份
	 * @return
	 */
	public static String lastLastMonth_ym(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -((Integer.parseInt(dd())) + 32));
		date.setTime(cal.getTimeInMillis());			
		return DateTime.yyyy_MM(date) ;
	}
	/**
	 * 得到上年度
	 * @return
	 */
	public static String lastYear(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, - 365);
		date.setTime(cal.getTimeInMillis());			
		return DateTime.yyyy(date) ;
	}
	
	/**
	 * 比方法compareDateStrByChar慢
	 * d1比d2日期更近返回1,相等返回0，更远返回-1
	 * @param d1
	 * @param d2
	 * @return
	 */
//	public static int compareDateStrByYMDH(String d1 , String d2){
//		try{
//			Date da1 = new SimpleDateFormat("yyyy-MM-dd HH").parse(d1);
//			Date da2 = new SimpleDateFormat("yyyy-MM-dd HH").parse(d2);
//			return da1.compareTo(da2) ;
//		}catch(Exception e){
//			return 0 ;
//		}
//	}
	/**
	 * d1比d2日期更近返回1(d1>d2),相等返回0，更远返回-1(d1<d2)
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int compareDateStrByChar(String d1 , String d2){
		int r = 0 ;
		if(d1 == null || d2 == null || d1.length() != d2.length()){
			return r ;
		}
		for(int i = 0 ; i < d1.length() ; i++){
			if(d1.charAt(i)>d2.charAt(i)){
				return 1 ;
			}else{
				if(d1.charAt(i)<d2.charAt(i)){
					return -1 ;
				}
			}
		}
		return r ;
	}
	
	/**
	 * 字符串型转变为整数
	 * @param str
	 * @return
	 */
	public static long dateTime_str2int(String str){
		if(str == null || str.trim().equals("")){
			return 0L ;
		}
		str = str.replaceAll("-", "") ;
		str = str.replaceAll(":", "") ;
		str = str.replaceAll(" ", "") ;
		return Long.parseLong(str) ;
	}
	
//	public static void main(String args[]){
//		String s = "2010-01-03 20:09:01" ;
//		System.out.println(yyymmddhhmmss_str2int(s)) ;
//	}

	public static String[] yearGroup1() {
		String years[] = null;
		int thisYear = Integer.parseInt((new SimpleDateFormat("yyyy"))
				.format(new Date(System.currentTimeMillis())));
		int len = (thisYear - 2005) + 1;
		years = new String[len];
		for (int i = 0; i < len; i++)
			years[i] = "" + (thisYear - i);

		return years;
	}

	public static String[] yearGroup2() {
		String[] years = { "1900", "1901", "1902", "1903", "1904", "1905",
				"1906", "1907", "1908", "1909", "1910", "1911", "1912", "1913",
				"1914", "1915", "1916", "1917", "1918", "1919", "1920", "1921",
				"1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929",
				"1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937",
				"1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945",
				"1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953",
				"1954", "1955", "1956", "1957", "1958", "1959", "1960", "1961",
				"1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969",
				"1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977",
				"1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985",
				"1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993",
				"1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001",
				"2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
				"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017",
				"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025",
				"2026", "2027", "2028", "2029" };
		return years;
	}

	public static String[] monthGroup() {
		String[] month = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12" };
		return month;
	}

	public static String[] dateGroup() {
		String[] date = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
				"31" };
		return date;
	}

	public static String[] timeGroup() {
		String[] time = { "00", "01", "02", "03", "04", "05", "06", "07", "08",
				"09", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23" };
		return time;
	}
	

	/**
	 * 判断是否为润年
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return new GregorianCalendar().isLeapYear(year) ;
    }
	
	/**
	 * 得到从今天起往前一个月的日期列表
	 * 要求不能存在两个以上月的日期（如上月是二月，今天是3月1日）
	 * 不包括当天日期及上月份与当前日相同的日期
	 * @return
	 */
	public static ArrayList<MonthDateVO> createOneMonthDate(){
		ArrayList<MonthDateVO> list = new ArrayList<MonthDateVO>() ;

		int thisTotal = 0 ;
		int beforTotal = 0 ;
		int year = Integer.parseInt(yyyy()) ;
		int month = Integer.parseInt(MM()) ;
		int date = Integer.parseInt(dd()) ;
		
		int beforeYear = year ;
		int beforeMonth = month - 1 ;
		int beforeDate = 31 ;
		thisTotal = date ;
		beforTotal = 31 - thisTotal ;
		if(month == 1){
			beforeYear = year - 1 ;
			beforeMonth = 12 ;
			beforeDate = 31 ;
		}
		if(month == 3){
			beforeMonth = 2 ;
			if(isLeapYear(year)){
				beforeDate = 29 ;
				if(beforTotal > 29){
					beforTotal = 29 ;
				}
			}else{
				beforeDate = 28 ;
				if(beforTotal > 28){
					beforTotal = 28 ;
				}
			}
			
		}
		switch(month){
			case 2 : { beforeMonth = 1 ; beforeDate = 31 ; break ;}
			case 4 : { beforeMonth = 3 ; beforeDate = 31 ; break ;}
			case 5 : { beforeMonth = 4 ; beforeDate = 30 ; break ;}
			case 6 : { beforeMonth = 5 ; beforeDate = 31 ; break ;}
			case 7 : { beforeMonth = 6 ; beforeDate = 30 ; break ;}
			case 8 : { beforeMonth = 7 ; beforeDate = 31 ; break ;}
			case 9 : { beforeMonth = 8 ; beforeDate = 31 ; break ;}
			case 10 : { beforeMonth = 9 ; beforeDate = 30 ; break ;}
			case 11 : { beforeMonth = 10 ; beforeDate = 31 ; break ;}
			case 12 : { beforeMonth = 11 ; beforeDate = 30 ; break ;}
		}
		int n = 0 ;
		int index = 1 ;
		for(int i = thisTotal ; i > 0 ; i--){
			MonthDateVO vo = new MonthDateVO() ;
			vo.setDate(year + "-" + month + "-" + (date - n)) ;
			vo.setIndex(date - n + "") ;
			if(date - n != date){
				list.add(vo) ;
			}
			index++ ;
			n++ ;
		}
		n = 0 ;
		for(int i = beforTotal ; i > 0 ; i--){
			MonthDateVO vo = new MonthDateVO() ;
			vo.setDate(beforeYear + "-" + beforeMonth + "-" + (beforeDate - n)) ;
			vo.setIndex(beforeDate - n + "") ;
			if(beforeDate - n != date){
				list.add(vo) ;
			}
			index++ ;
			n++ ;
		}
		return list ;
	}
	
	/**
	 * 两个日期比较(格式如 2008-09-09 00:00:00)
	 * @param one
	 * @param two
	 * @return
	 */
	public static boolean oneBigTow( String one , String two){
		one = one.replaceAll("-", "") ;
		one = one.replaceAll(" ", "") ;
		one = one.replaceAll(":", "") ;
		two = two.replaceAll("-", "") ;
		two = two.replaceAll(" ", "") ;
		two = two.replaceAll(":", "") ;
		Long onel = Long.parseLong(one) ;
		Long twol = Long.parseLong(two) ;
		if(onel.longValue() >= twol.longValue()){
			return true ;
		}else{
			return false ;
		}
		
	}
	/**
	 * 将2009-02-12格式的日期分解为年 月 日字符串数组
	 * @param ymd
	 * @return
	 */
	public static String[] altYmd(String ymd){
		if(ymd == null || ymd.length() < 10){
			return null ;
		}
		String[] s = new String[3] ;
		s[0] = ymd.substring(0 , 4) ;
		s[1] = ymd.substring(5 , 7) ;
		if(s[1].charAt(0) == '0'){
			s[1] = s[1].substring(1 , s[1].length()) ;
		}
		s[2] = ymd.substring(8,10) ;
		if(s[2].charAt(0) == '0'){
			s[2] = s[2].substring(1 , s[2].length()) ;
		}
		return s ;
	}
//	
//	public static void main(String args[]){
//		String s = "2345" ;
//		String s2 = "2346" ;
//		int com = s.compareTo(s2) ;
//
//		System.out.println(com) ;
//	}

}
