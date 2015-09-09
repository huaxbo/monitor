package com.hxb.commu.remote.tcp.handler.send;

import org.apache.log4j.Logger;

import com.hxb.commu.local.common.CommandAttach;
import com.hxb.commu.remote.protocol.ProtocolDriver;
import com.hxb.commu.remote.tcp.manager.session.SessionManager;
import com.hxb.commu.service.meter.MeterVO;
import com.hxb.commu.service.meter.TbCsMeterService;

public class SendHandler {

	private Logger log = Logger.getLogger(SendHandler.class);
	
	private static SendHandler handler;
	
	private SendHandler(){}
	
	/**
	 * @return
	 */
	public static SendHandler getSingle(){
		if(handler == null){
			handler = new SendHandler();
		}
		
		return handler;
	}
	
	/**
	 * @param atta
	 */
	public void execute(CommandAttach atta){
		String meterId = atta.getMeterId();
		String funcCode = atta.getFuncCode();
		if(meterId == null || meterId.equals("")){
			log.warn("命令设备ID为空！");
			return ;
		}		
		//获取设备信息						
		MeterVO mvo = TbCsMeterService.getSingle().getMeter(meterId);
		if(mvo == null){
			log.warn("未配置设备[" + meterId + "]信息！");
			return ;
		}
		//获取驱动实例
		String dv = mvo.getMeterDriver();
		if(dv == null || dv.equals("")){
			log.warn("设备[" + meterId + "]协议驱动未配置！");
			return ;
		}
		ProtocolDriver driver = null;
		try {
			driver = (ProtocolDriver)Class.forName(dv).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			log.error("协议驱动" + dv + "实例化失败！");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			log.error("协议驱动" + dv + "无法访问！");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			log.error("协议驱动" + dv + "未找到！");
		}finally{}	
		if(driver == null){
			log.warn("获取测控设备协议驱动为空！");
			return ;
		}
		//构建命令数据
		byte[] bts = driver.createCommand(meterId, funcCode, atta);	
		SessionManager.getSingleInstance().send(meterId, bts);		
	}
	
}
