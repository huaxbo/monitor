package com.hxb.global.service.log;

import java.util.*;

import org.springframework.context.ApplicationContext;


import com.hxb.common.secure.log.*;
import com.hxb.global.util.SysListener;

public class AmLog implements LogInterf {

	public void log(Hashtable<String, String> logSet) {
		if(logSet != null){
			String roleId = logSet.get(AmLogMethod.roleIdKey) ;
			String userName = logSet.get(AmLogMethod.userNameKey) ;
			String dateTime = logSet.get(AmLogMethod.dateTimeKey) ;
			String logStr = logSet.get(AmLogMethod.logStrKey) ;
			String currIp = logSet.get(AmLogMethod.currentIpKey) ;
			
			//System.out.println("操作日志:行政区ID：" + regionId + " 用户名称:" + userName + " 时间:" + dateTime + " 操作内容:" + logStr) ;
			
			LogBusi lb = this.getLogBusi() ;
			lb.saveLog(roleId , userName, dateTime, logStr , currIp) ;
		}
	}
	/**
	 * 从spring容器中得到业务BEAN
	 * @return
	 */
	private LogBusi getLogBusi() {
		ApplicationContext ctx = SysListener.getSpringContext() ;
		LogBusi action = (LogBusi) ctx.getBean("global_logBusi");
		return action;
	}

}
