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
			
			//System.out.println("������־:������ID��" + regionId + " �û�����:" + userName + " ʱ��:" + dateTime + " ��������:" + logStr) ;
			
			LogBusi lb = this.getLogBusi() ;
			lb.saveLog(roleId , userName, dateTime, logStr , currIp) ;
		}
	}
	/**
	 * ��spring�����еõ�ҵ��BEAN
	 * @return
	 */
	private LogBusi getLogBusi() {
		ApplicationContext ctx = SysListener.getSpringContext() ;
		LogBusi action = (LogBusi) ctx.getBean("global_logBusi");
		return action;
	}

}
