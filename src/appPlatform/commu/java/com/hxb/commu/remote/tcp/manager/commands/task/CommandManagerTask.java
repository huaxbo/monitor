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
	 * ִ������
	 */
	public void execute(){
		CommandManager cmgr = CommandManager.getSingle();
		cmgr.cleanCommand();//�����Ч�����¼
		Hashtable<String,CommandQueue> cds = cmgr.getCommands();
		Iterator<String> it = cds.keySet().iterator();
		while(it.hasNext()){
			String meterId = it.next();
			SessionVO svo = SessionManager.getSingleInstance().get(meterId);
			if(svo == null){
				log.warn("����豸[" + meterId + "]�ݲ����ߣ��������޷��ɹ�����!");
				cmgr.removeAll(meterId);//����豸����������
				continue ;
			}
			
			if(!cmgr.isExecute(meterId)){
				CommandAttach ca = cmgr.getCommand(meterId);
				if(ca != null){
					HashMap<String,Object> params = new HashMap<String,Object>(0);
					params.put("ca", ca);
					params.put("meterId", meterId);					
					//����ִ�е���
					//�������
					taskNum++;
					taskNum /= 500;//��������500����	
					ThreadPoolManager.getThreadPoolManager("remoteCommandPool")
						.addTask(new TaskImpl("remote command task-" + taskNum,params) {						
						@Override
						public boolean execute() {
							// TODO Auto-generated method stub
							CommandAttach ca = (CommandAttach)params.get("ca");
							String meterId = (String)params.get("meterId");
							
							for(int i=0;i<CommuConstant.command_maxFails;i++){
								synchronized(ca){
									SendHandler.getSingle().execute(ca);//����Զ������
									try {
										ca.wait(CommuConstant.command_perInterval * 1000);
										if(ca.isSucc()){//����ɹ�
											break;
										}
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}finally{}
								}
							}
							if(!ca.isSucc()){//����ʧ�ܴ���
								 CommandManager.getSingle().commandFail(meterId);
								 log.warn("����豸[" + meterId + "]Զ�����������ʱ������豸δ�н���ظ���");
								 SessionManager.getSingleInstance().plusCmdFails(meterId);
							}else{
								SessionManager.getSingleInstance().cleanCmdFails(meterId);
							}
							
							return false;
						}
					});
				}
			}else{
				log.info("����豸[" + meterId + "]���������ڴ����ȴ���...");
			}
		}
	}
	
}
