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
	 * ��ȡ��һʵ��
	 */
	public static LocalHandler getSingle(){
		if(handler == null){
			handler = new LocalHandler();
		}
		return handler;
	}
	
	/**
	 * ��������
	 */
	public void execute(CommandAttach atta){
		//�豸�����ж�
		if(SessionManager.getSingleInstance().get(atta.getMeterId()) == null){
			atta.setReceiptOnline(false);
			
			return ;
		}
		atta.setReceiptOnline(true);
		
		String funcCode = atta.getFuncCode();
		if(funcCode != null && !funcCode.equals("")){
			if(funcCode.equals(LocalCode.local_reloadMeters)){
				//���¼��ز���豸�б�
				TbCsMeterService.getSingle().getAll(true);
				atta.setReceiptStatus(CommuConstant.receipt_succ);
			}else if(funcCode.equals(LocalCode.local_serverClock)){
				//��ȡͨѶ����ʱ��
				atta.setReceiptObj(DateTime.yyyy_MM_dd_HH_mm_ss());
				atta.setReceiptStatus(CommuConstant.receipt_succ);				
			}else if(funcCode.equals(LocalCode.local_onLines)){
				//�鿴�豸�����б�
				String[] vos = SessionManager.getSingleInstance().getAllId();
				atta.setReceiptObj(vos);				
			}else{//Զ�������
				CommandManager.getSingle().add(atta.getMeterId(), atta);
				atta.setReceiptStatus(CommuConstant.receipt_succ);	
			}
		}else{
			log.error("������Ϊ�գ�");
			atta.setReceiptMessage("������Ϊ�գ�������Ч��");
			atta.setReceiptStatus(CommuConstant.receipt_fail);
		}
	}
}
