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
			log.warn("�����豸IDΪ�գ�");
			return ;
		}		
		//��ȡ�豸��Ϣ						
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
		//������������
		byte[] bts = driver.createCommand(meterId, funcCode, atta);	
		SessionManager.getSingleInstance().send(meterId, bts);		
	}
	
}
