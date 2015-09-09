package com.hxb.global.service.log;

import org.aspectj.lang.ProceedingJoinPoint;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import com.hxb.common.secure.SessionUserHolder;
import com.hxb.common.secure.config.SecureConfig;
import com.hxb.common.secure.log.*;

public class AmLogMethod {

	private static boolean gotLogClass = false;

	@SuppressWarnings("rawtypes")
	private static Class logclass;

	public static String roleIdKey = "roleId" ;
	public static String userNameKey = "userName" ;
	public static String dateTimeKey = "dateTime" ;
	public static String logStrKey = "logStr" ;
	public static String currentIpKey = "currentIp" ;//当前IP

	/**
	 * 类方法日志控制的环绕增强
	 * 进行日志记录控制
	 * @param p
	 * @return 
	 * @throws Throwable
	 */
	public Object aroundMethod(ProceedingJoinPoint p) throws Throwable {

		if (SecureConfig.isLog == null
				|| SecureConfig.isLog.booleanValue() == false) {
			return p.proceed();
		}

		UserVO vo = (UserVO)SessionUserHolder.getUserInfo();
		if (vo == null) {
			throw new Exception(
					"进行日志记录时，没有得到会话中的用户信息，这是因为系统实际应用了权限和日志控制，而SecureFilterSSO配置成不应用权限和日志控制!");
		}

		Class clazz = p.getTarget().getClass();
		String methodName = p.getSignature().toLongString();

		Method[] methods = clazz.getDeclaredMethods();
		if (methods != null) {
			for (Method m : methods) {
				if (methodName.equals(m.toGenericString())) {
					this.log(m, vo);
					break;
				}
			}
		}
		return p.proceed();
	}

	/**
	 * 写日志
	 * @param m
	 * @param powers
	 * @return
	 */
	private void log(Method m, UserVO vo) {
		String log_value = ((Logor) (m.getAnnotation(Logor.class))).value();
		if(!gotLogClass){
			getLogClass() ;
		}
		if (log_value != null && !log_value.equals("")) {
			try{
				LogInterf log = (LogInterf)logclass.newInstance() ;
				Hashtable<String , String> logset = new Hashtable<String , String>() ;
				if(vo.getRoleId() != null){
					logset.put(roleIdKey, vo.getRoleId()) ;
				}
				if(vo.getNameCN() != null){
					logset.put(userNameKey , vo.getNameCN()) ;
				}
				if(vo.getCurrIp() != null){
					logset.put(currentIpKey, vo.getCurrIp()) ;
				}
				logset.put(dateTimeKey , yyyy_mm_dd_hh_mm_ss()) ;
				logset.put(logStrKey , log_value) ;
				log.log(logset) ;
			}catch(Exception e){
		    	e.printStackTrace() ;
		    }
		}
	}

	private static void getLogClass(){
		try{
			logclass = Class.forName(SecureConfig.logClass) ;
	    }catch(Exception e){
	    	e.printStackTrace() ;
	    }
	}
	private static String yyyy_mm_dd_hh_mm_ss() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System
				.currentTimeMillis()));
	}

}
