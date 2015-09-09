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
	 * ��ȡ��һʵ��
	 * @return
	 */
	public static RecvHandler getSingle(){
		if(handler == null){
			handler = new RecvHandler();
		}
		
		return handler;
	}
	
	
	/**
	 * ���ݴ���
	 * @param bts
	 */
	public void execute(IoSession session,byte[] bts){
		//�ϱ�����
		String hex = null;
		try {
			hex = UtilProtocol.getSingle().byte2Hex(bts, true).toUpperCase();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{}
		//У������ͷ
		String meterId = RecvUtil.checkHead(bts);//��ȡ�豸id
		if(hex != null){
			log.info("����豸[" + (meterId == null ? "null" : meterId) + "]�������ݣ�" + hex);
		}	
		if(meterId == null || meterId.equals("")){
			return ;
		}
		//У��crc
		if(!RecvUtil.checkCrc(bts)){
			log.warn("�ϱ�����CRCУ��ʧ�ܣ�");
			return ;
		}
		//��ȡ�豸id				
		
		MeterVO mvo = TbCsMeterService.getSingle().getMeter(meterId);
		if(mvo == null){
			log.warn("δ�����豸[" + meterId + "]��Ϣ��");
			return ;
		}
		//��ȡ����ʵ��
		String dv = mvo.getMeterDriver();
		if(dv == null || dv.equals("")){
			log.warn("�豸[" + meterId + "]Э������δ���ã�");
			return ;
		}
		ProtocolDriver driver = null;
		try {
			driver = (ProtocolDriver)Class.forName(dv).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			log.error("Э������" + dv + "ʵ����ʧ�ܣ�");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			log.error("Э������" + dv + "�޷����ʣ�");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			log.error("Э������" + dv + "δ�ҵ���");
		}finally{}	
		if(driver == null){
			log.warn("��ȡ����豸Э������Ϊ�գ�");
			return ;
		}
		//�ж��Ƿ�ע��or����
		String funcCode = driver.getFuncCode(bts);		
		if(funcCode == null || funcCode.equals("")){
			log.error("�豸[" + meterId + "]��ȡ������ʧ�ܣ�");
			return ;
		}
		//ά���豸����
		if(driver.isRegister(funcCode)){
			SessionManager.getSingleInstance().add(meterId, new SessionVO(meterId,session));//ע���豸����
			return ;
		}else{
			SessionManager.getSingleInstance().updateTimestamp(meterId);//�����豸����ʱ���
		}			
			
		//���ݴ���
		HashMap<String,Object> params = new HashMap<String,Object>(0);
		params.put("meterId", meterId);
		params.put("bts",bts);
		params.put("driver", driver);
		params.put("funcCode", funcCode);
		//�������
		taskNum++;
		taskNum /= 500;//��������500����	
		ThreadPoolManager.getThreadPoolManager("remoteRecvPool")
			.addTask(new TaskImpl("remove recive task-" + taskNum,params) {			
			@Override
			public boolean execute() {
				// TODO Auto-generated method stub
				String meterId = (String)params.get("meterId");
				byte[] bts = (byte[])params.get("bts");
				ProtocolDriver driver = (ProtocolDriver)params.get("driver");
				String funcCode = (String)params.get("funcCode");
				
				//���ݽ���
				Action act = driver.analyzeCommand(meterId,bts);
				if(driver.centerData != null){
					driver.centerData.setMeterId(meterId);
					driver.centerData.setFuncCode(funcCode);
				}
				if(act.equals(Action.nullAction)){
					log.warn("�޶���ִ�У�");
					
				}
				if(act.has(Action.autoReport)){//�����ϱ����ݴ���
					TbCsTempService.getSingle().save(null,
							SerializeUtil.getInstance().obj2Xml(driver.centerData, 
									new Class[]{ProtocolData.class,Data_10.class}));
				}
				if(act.has(Action.commandResult)){//���������ݴ���
					//������֪ͨ
					String commandId = CommandManager.getSingle().commandSucc(meterId, funcCode);
					driver.centerData.setCommandId(commandId);
					TbCsTempService.getSingle().save(commandId,
							SerializeUtil.getInstance().obj2Xml(driver.centerData, new Class[]{ProtocolData.class}));
					
				}
				if(act.has(Action.commandWait)){//�ӳ�����ȴ�����
								
					
				}
				if(act.has(Action.remoteConfirm)){//Զ��ȷ��
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
