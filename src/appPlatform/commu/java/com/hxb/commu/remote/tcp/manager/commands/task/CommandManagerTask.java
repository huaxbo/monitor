package com.hxb.commu.remote.tcp.manager.commands.task;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.hxb.common.threadPool2.ThreadPoolManager;
import com.hxb.common.threadPool2.impl.TaskImpl;
import com.hxb.commu.local.common.CommandAttach;
import com.hxb.commu.remote.tcp.handler.send.SendHandler;
import com.hxb.commu.remote.tcp.manager.commands.CommandManager;
import com.hxb.commu.remote.tcp.manager.commands.CommandQueue;
import com.hxb.commu.remote.tcp.manager.session.SessionManager;
import com.hxb.commu.remote.tcp.manager.session.SessionVO;
import com.hxb.commu.util.CommuConstant;

public class CommandManagerTask {

	private Logger log = Logger.getLogger(CommandManagerTask.class);
	
	private static CommandManagerTask task;
	private int taskNum;
	private CommandManagerTask(){}
	
	/**
	 * @return
	 */
	public static CommandManagerTask getSingle(){
		if(task == null){
			task = new CommandManagerTask();
		}
		
		return task;
	}
	
	/**
	 * 执行任务
	 */
	public void execute(){
		CommandManager cmgr = CommandManager.getSingle();
		cmgr.cleanCommand();//清除无效命令记录
		Hashtable<String,CommandQueue> cds = cmgr.getCommands();
		Iterator<String> it = cds.keySet().iterator();
		while(it.hasNext()){
			String meterId = it.next();
			SessionVO svo = SessionManager.getSingleInstance().get(meterId);
			if(svo == null){
				log.warn("测控设备[" + meterId + "]暂不在线，此命令无法成功发送!");
				cmgr.removeAll(meterId);//清空设备待发送命令
				continue ;
			}
			
			if(!cmgr.isExecute(meterId)){
				CommandAttach ca = cmgr.getCommand(meterId);
				if(ca != null){
					HashMap<String,Object> params = new HashMap<String,Object>(0);
					params.put("ca", ca);
					params.put("meterId", meterId);					
					//命令执行调度
					//任务计数
					taskNum++;
					taskNum /= 500;//任务数满500置零	
					ThreadPoolManager.getThreadPoolManager("remoteCommandPool")
						.addTask(new TaskImpl("remote command task-" + taskNum,params) {						
						@Override
						public boolean execute() {
							// TODO Auto-generated method stub
							CommandAttach ca = (CommandAttach)params.get("ca");
							String meterId = (String)params.get("meterId");
							
							for(int i=0;i<CommuConstant.command_maxFails;i++){
								synchronized(ca){
									SendHandler.getSingle().execute(ca);//发送远程命令
									try {
										ca.wait(CommuConstant.command_perInterval * 1000);
										if(ca.isSucc()){//命令成功
											break;
										}
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}finally{}
								}
							}
							if(!ca.isSucc()){//命令失败处理
								 CommandManager.getSingle().commandFail(meterId);
								 log.warn("测控设备[" + meterId + "]远程命令操作超时，测控设备未有结果回复！");
								 SessionManager.getSingleInstance().plusCmdFails(meterId);
							}else{
								SessionManager.getSingleInstance().cleanCmdFails(meterId);
							}
							
							return false;
						}
					});
				}
			}else{
				log.info("测控设备[" + meterId + "]有命令正在处理，等待中...");
			}
		}
	}
	
}
