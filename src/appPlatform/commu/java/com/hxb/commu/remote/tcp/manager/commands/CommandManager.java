package com.hxb.commu.remote.tcp.manager.commands;

import java.util.Enumeration;
import java.util.Hashtable;

import com.hxb.commu.local.common.CommandAttach;

public class CommandManager {

	private static CommandManager manager;
	
	/**
	 * 命令队列
	 */
	private Hashtable<String,CommandQueue> commands = new Hashtable<String,CommandQueue>(0);
	/**
	 * 执行的命令清单
	 */
	private Hashtable<String,CommandAttach> executeCommands = new Hashtable<String,CommandAttach>(0);
	/**
	 * 
	 */
	private CommandManager(){}
	
	/**
	 * @return
	 */
	public static CommandManager getSingle(){
		if(manager == null){
			manager = new CommandManager();
		}
		return manager;
	}
	
	/**
	 * 增加命令
	 * @param meterId
	 * @param atta
	 */
	public void add(String meterId,CommandAttach atta){
		if(commands.get(meterId) == null){			
			commands.put(meterId, CommandQueue.getOne());
		}
		CommandQueue queue = commands.get(meterId);
		queue.push(atta);	
	}
		
	/**
	 * 获取待执行命令
	 * @param meterId
	 * @return
	 */
	public CommandAttach getCommand(String meterId){
		CommandQueue cq = commands.get(meterId);
		if(cq == null){//无命令队列
			
			return null;
		}
		if(cq.getSize() == 0){//命令队列为空
			commands.remove(meterId);
			
			return null;
		}
		if(!isExecute(meterId)){//未有正执行的命令
			CommandAttach ca = cq.pop();
			executeCommands.put(meterId, ca);	
			
			return ca;
		}		
		
		return null;
	}
	/**
	 * 清除设备全部命令
	 * @param meterId
	 */
	public void removeAll(String meterId){
		executeCommands.remove(meterId);
		commands.remove(meterId);		
	}
	
	/**
	 * 清空无执行命令记录
	 * @param meterId
	 */
	public void cleanCommand(){
	    Enumeration<String> es = commands.keys();
		while(es.hasMoreElements()){
			String meterId = es.nextElement();
			CommandQueue cq = commands.get(meterId);
			if(cq == null){//无命令队列
				
				return ;
			}
			if(cq.getSize() == 0){//命令队列为空
				commands.remove(meterId);
			}
		}	
	}
	/**
	 * 是否执行命令
	 * @param meterId
	 * @return
	 */
	public boolean isExecute(String meterId){
		synchronized(executeCommands){
			CommandAttach ca = executeCommands.get(meterId);
			if(ca == null){
				return false;
			}
			
			return true;
		}
	}
	
	/**
	 * 命令成功通知
	 * @param meterId,funcCode
	 * @return commandId
	 */
	public String commandSucc(String meterId,String funcCode){
		synchronized(executeCommands){
			CommandAttach ca = executeCommands.get(meterId);
			if(ca != null 
					&& ca.getFuncCode().equals(funcCode)){
				synchronized(ca){
					ca.setSucc(true);
					ca.notifyAll();//唤醒命令等待
					executeCommands.remove(meterId);//清除执行中命令记录
				}
				
				return ca.getCommandId();
			}
			
			return null;
		}
		
	}
	
	/**
	 * 命令失败处理
	 * @param meterId
	 */
	public void commandFail(String meterId){
		synchronized(executeCommands){
			executeCommands.remove(meterId);			
		}
	}

	public Hashtable<String, CommandQueue> getCommands() {
		return commands;
	}

	public void setCommands(Hashtable<String, CommandQueue> commands) {
		this.commands = commands;
	}
}
