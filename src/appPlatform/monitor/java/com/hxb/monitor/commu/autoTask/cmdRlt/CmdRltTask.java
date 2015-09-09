package com.hxb.monitor.commu.autoTask.cmdRlt;

import java.util.List;

import org.apache.log4j.Logger;

import com.hxb.commu.remote.protocol.ProtocolData;
import com.hxb.global.po.TbCsTempCmd;
import com.hxb.global.po.TbCsTempFailed;
import com.hxb.global.service.util.SpringContextUtil;
import com.hxb.global.util.SerializeUtil;
import com.hxb.monitor.commu.command.Resulter;
import com.hxb.monitor.service.temp.TempAllBusi;
import com.hxb.monitor.util.MonitorConstant;

public class CmdRltTask extends Thread {

	private Logger log = Logger.getLogger(CmdRltTask.class);
	private static CmdRltTask task;//single instance
	private Object synObj;//syn object
	private TempAllBusi busi;
	/**
	 * 
	 */
	private CmdRltTask(){}
	
	public static CmdRltTask getSingleOne(){
		if(task == null){
			task = new CmdRltTask();
			task.synObj = new Object();
			task.busi = (TempAllBusi)SpringContextUtil.getSpringBean("tempAllBusi");
		}
		
		return task;
	}
	
	/* 
	 * task execute
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){			
			if(busi != null){
				log.info("===开始处理命令结果任务！");
				List<Object> cmdRlts =	null;
				for(int k=0;k<MonitorConstant.times_scan_cmdResult;k++){
					try {
						sleep(MonitorConstant.max_wait_cmdResult/MonitorConstant.times_scan_cmdResult * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{}
					cmdRlts = busi.getSome(TbCsTempCmd.class);
					if(cmdRlts.size()>0){
						break;
					}
				}
				while(cmdRlts.size()>0){
					String[] ids = new String[cmdRlts.size()];
					//记录处理
					for(int i=0;i<cmdRlts.size();i++){
						TbCsTempCmd cmd = (TbCsTempCmd)cmdRlts.get(i);
						ids[i] = cmd.getId();
						Object rlt = SerializeUtil.getInstance().xml2Obj(cmd.getData(),
								new Class[]{ProtocolData.class});
						//命令结果唤醒
						Resulter.getResulterInstance().putCmdResult(cmd.getCommandId(), rlt);
						
						//命令结果入库处理
						try{
							CmdRltHandler.getSingleOne().execute(rlt);							
						}catch(Exception e){//保持数据处理失败的结果记录
							busi.saveFailed(new TbCsTempFailed(cmd.getCommandId(), cmd.getData(),cmd.getTs()));
						}finally{}
						
						
						//存储命令结果
						
						
					}					
					//删除已处理记录
					busi.delSome(TbCsTempCmd.class, ids);
					//检测是否有待处理记录
					try {
						sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cmdRlts = null;//clean cmdRlts
					cmdRlts = busi.getSome(TbCsTempCmd.class);
				}
				log.info("===结束处理命令结果任务！");
				synchronized(synObj){					
					try {
						log.info("===处理命令结果任务等待...");
						synObj.wait(MonitorConstant.max_scan_cmdResult * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{}
				}
			}
		}
	}
	
	/**
	 * 唤醒扫描任务
	 */
	public void notifyScan(){
		synchronized(synObj){
			synObj.notifyAll();
		}	
	}
}
