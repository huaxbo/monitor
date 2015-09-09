package com.hxb.monitor.commu.command;



import com.hxb.commu.local.common.CommandAttach;
import com.hxb.commu.util.CommuConstant;
import com.hxb.global.util.SerializeUtil;
import com.hxb.monitor.commu.autoTask.cmdRlt.CmdRltTask;
import com.hxb.monitor.commu.localServer.LocalServerManager;


public class Sender {

	public String message;//通讯服务器返回消息
	public Object receipt;//通讯服务返回结果
	
	/**
	 * 发送命令到信息发布服务器，并返回是否发送成功
	 * @param com
	 * @param cid
	 */
	public boolean sendAndAnswer(CommandAttach atta,String cid){
		try {
			String rlt = LocalServerManager.sendCommand(cid, atta);
			if(rlt != null){
				CommandAttach ca = (CommandAttach)SerializeUtil.getInstance().xml2Obj(rlt, new Class[]{CommandAttach.class});
				if(!ca.isReceiptOnline()){
					message = "设备不在线，无法发送当前命令！";
					
					return false;
				}else{
					if(ca.getReceiptStatus() != null
							&& ca.getReceiptStatus().equals(CommuConstant.receipt_fail)){
						message = ca.getReceiptMessage();
						
						return false;
					}	
					receipt = ca.getReceiptObj();
					
					//启动命令结果扫描工作
					CmdRltTask.getSingleOne().notifyScan();
					return true;
				}
			}else{
				message = "连接通讯服务失败，不能发送此命令！";
				
				return false;
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message = "连接通讯服务失败，不能发送此命令！";
			e.printStackTrace();
		}finally{}	
		
		
		return false;
	}
	
}
