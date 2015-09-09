package com.hxb.commu.local.handler;


import org.apache.log4j.Logger;

import com.hxb.commu.local.common.CommandAttach;
import com.hxb.commu.local.common.LocalCode;
import com.hxb.commu.remote.tcp.manager.commands.CommandManager;
import com.hxb.commu.remote.tcp.manager.session.SessionManager;
import com.hxb.commu.service.meter.TbCsMeterService;
import com.hxb.commu.util.CommuConstant;
import com.hxb.util.DateTime;

public class LocalHandler {

	private Logger log = Logger.getLogger(LocalHandler.class);
	
	private static LocalHandler handler;
	
	private LocalHandler(){}
	
	/**
	 * 获取单一实例
	 */
	public static LocalHandler getSingle(){
		if(handler == null){
			handler = new LocalHandler();
		}
		return handler;
	}
	
	/**
	 * 处理任务
	 */
	public void execute(CommandAttach atta){
		//设备在线判断
		if(SessionManager.getSingleInstance().get(atta.getMeterId()) == null){
			atta.setReceiptOnline(false);
			
			return ;
		}
		atta.setReceiptOnline(true);
		
		String funcCode = atta.getFuncCode();
		if(funcCode != null && !funcCode.equals("")){
			if(funcCode.equals(LocalCode.local_reloadMeters)){
				//重新加载测控设备列表
				TbCsMeterService.getSingle().getAll(true);
				atta.setReceiptStatus(CommuConstant.receipt_succ);
			}else if(funcCode.equals(LocalCode.local_serverClock)){
				//读取通讯服务时钟
				atta.setReceiptObj(DateTime.yyyy_MM_dd_HH_mm_ss());
				atta.setReceiptStatus(CommuConstant.receipt_succ);				
			}else if(funcCode.equals(LocalCode.local_onLines)){
				//查看设备在线列表
				String[] vos = SessionManager.getSingleInstance().getAllId();
				atta.setReceiptObj(vos);				
			}else{//远程命令处理
				CommandManager.getSingle().add(atta.getMeterId(), atta);
				atta.setReceiptStatus(CommuConstant.receipt_succ);	
			}
		}else{
			log.error("功能码为空！");
			atta.setReceiptMessage("功能码为空，命令无效！");
			atta.setReceiptStatus(CommuConstant.receipt_fail);
		}
	}
}
