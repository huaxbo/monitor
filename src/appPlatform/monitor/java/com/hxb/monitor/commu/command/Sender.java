package com.hxb.monitor.commu.command;



import com.hxb.commu.local.common.CommandAttach;
import com.hxb.commu.util.CommuConstant;
import com.hxb.global.util.SerializeUtil;
import com.hxb.monitor.commu.autoTask.cmdRlt.CmdRltTask;
import com.hxb.monitor.commu.localServer.LocalServerManager;


public class Sender {

	public String message;//ͨѶ������������Ϣ
	public Object receipt;//ͨѶ���񷵻ؽ��
	
	/**
	 * ���������Ϣ�������������������Ƿ��ͳɹ�
	 * @param com
	 * @param cid
	 */
	public boolean sendAndAnswer(CommandAttach atta,String cid){
		try {
			String rlt = LocalServerManager.sendCommand(cid, atta);
			if(rlt != null){
				CommandAttach ca = (CommandAttach)SerializeUtil.getInstance().xml2Obj(rlt, new Class[]{CommandAttach.class});
				if(!ca.isReceiptOnline()){
					message = "�豸�����ߣ��޷����͵�ǰ���";
					
					return false;
				}else{
					if(ca.getReceiptStatus() != null
							&& ca.getReceiptStatus().equals(CommuConstant.receipt_fail)){
						message = ca.getReceiptMessage();
						
						return false;
					}	
					receipt = ca.getReceiptObj();
					
					//����������ɨ�蹤��
					CmdRltTask.getSingleOne().notifyScan();
					return true;
				}
			}else{
				message = "����ͨѶ����ʧ�ܣ����ܷ��ʹ����";
				
				return false;
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message = "����ͨѶ����ʧ�ܣ����ܷ��ʹ����";
			e.printStackTrace();
		}finally{}	
		
		
		return false;
	}
	
}
