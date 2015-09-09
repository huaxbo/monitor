package com.hxb.commu.remote.tcp.handler.recv;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.hxb.common.threadPool2.ThreadPoolManager;
import com.hxb.common.threadPool2.impl.TaskImpl;
import com.hxb.commu.remote.protocol.Action;
import com.hxb.commu.remote.protocol.ProtocolData;
import com.hxb.commu.remote.protocol.ProtocolDriver;
import com.hxb.commu.remote.protocol.util.UtilProtocol;
import com.hxb.commu.remote.protocol.ver_test.cd_10.Data_10;
import com.hxb.commu.remote.tcp.manager.commands.CommandManager;
import com.hxb.commu.remote.tcp.manager.session.SessionManager;
import com.hxb.commu.remote.tcp.manager.session.SessionVO;
import com.hxb.commu.service.meter.MeterVO;
import com.hxb.commu.service.meter.TbCsMeterService;
import com.hxb.commu.service.temp.TbCsTempService;
import com.hxb.global.util.SerializeUtil;

public class RecvHandler {

	private Logger log = Logger.getLogger(RecvHandler.class);
	private static RecvHandler handler;
	private int taskNum;
	/**
	 * 
	 */
	private RecvHandler(){}
	
	/**
	 * 获取单一实例
	 * @return
	 */
	public static RecvHandler getSingle(){
		if(handler == null){
			handler = new RecvHandler();
		}
		
		return handler;
	}
	
	
	/**
	 * 数据处理
	 * @param bts
	 */
	public void execute(IoSession session,byte[] bts){
		//上报数据
		String hex = null;
		try {
			hex = UtilProtocol.getSingle().byte2Hex(bts, true).toUpperCase();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{}
		//校验数据头
		String meterId = RecvUtil.checkHead(bts);//获取设备id
		if(hex != null){
			log.info("测控设备[" + (meterId == null ? "null" : meterId) + "]接收数据：" + hex);
		}	
		if(meterId == null || meterId.equals("")){
			return ;
		}
		//校验crc
		if(!RecvUtil.checkCrc(bts)){
			log.warn("上报数据CRC校验失败！");
			return ;
		}
		//获取设备id				
		
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
		//判断是否注册or心跳
		String funcCode = driver.getFuncCode(bts);		
		if(funcCode == null || funcCode.equals("")){
			log.error("设备[" + meterId + "]获取功能码失败！");
			return ;
		}
		//维护设备连接
		if(driver.isRegister(funcCode)){
			SessionManager.getSingleInstance().add(meterId, new SessionVO(meterId,session));//注册设备连接
			return ;
		}else{
			SessionManager.getSingleInstance().updateTimestamp(meterId);//更新设备连接时间戳
		}			
			
		//数据处理
		HashMap<String,Object> params = new HashMap<String,Object>(0);
		params.put("meterId", meterId);
		params.put("bts",bts);
		params.put("driver", driver);
		params.put("funcCode", funcCode);
		//任务计数
		taskNum++;
		taskNum /= 500;//任务数满500置零	
		ThreadPoolManager.getThreadPoolManager("remoteRecvPool")
			.addTask(new TaskImpl("remove recive task-" + taskNum,params) {			
			@Override
			public boolean execute() {
				// TODO Auto-generated method stub
				String meterId = (String)params.get("meterId");
				byte[] bts = (byte[])params.get("bts");
				ProtocolDriver driver = (ProtocolDriver)params.get("driver");
				String funcCode = (String)params.get("funcCode");
				
				//数据解析
				Action act = driver.analyzeCommand(meterId,bts);
				if(driver.centerData != null){
					driver.centerData.setMeterId(meterId);
					driver.centerData.setFuncCode(funcCode);
				}
				if(act.equals(Action.nullAction)){
					log.warn("无动作执行！");
					
				}
				if(act.has(Action.autoReport)){//主动上报数据处理
					TbCsTempService.getSingle().save(null,
							SerializeUtil.getInstance().obj2Xml(driver.centerData, 
									new Class[]{ProtocolData.class,Data_10.class}));
				}
				if(act.has(Action.commandResult)){//命令结果数据处理
					//命令结果通知
					String commandId = CommandManager.getSingle().commandSucc(meterId, funcCode);
					driver.centerData.setCommandId(commandId);
					TbCsTempService.getSingle().save(commandId,
							SerializeUtil.getInstance().obj2Xml(driver.centerData, new Class[]{ProtocolData.class}));
					
				}
				if(act.has(Action.commandWait)){//延长命令等待处理
								
					
				}
				if(act.has(Action.remoteConfirm)){//远程确认
					byte[] cm = driver.remoteData;
					if(cm != null){
						SessionManager.getSingleInstance().send(meterId, cm);
					}					
				}
				
				return true;
			}
		});
	}
		
}
