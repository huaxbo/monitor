package com.hxb.commu.remote.tcp.manager.commands;

import java.util.Enumeration;
import java.util.Hashtable;

import com.hxb.commu.local.common.CommandAttach;

public class CommandManager {

	private static CommandManager manager;
	
	/**
	 * �������
	 */
	private Hashtable<String,CommandQueue> commands = new Hashtable<String,CommandQueue>(0);
	/**
	 * ִ�е������嵥
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
	 * ��������
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
	 * ��ȡ��ִ������
	 * @param meterId
	 * @return
	 */
	public CommandAttach getCommand(String meterId){
		CommandQueue cq = commands.get(meterId);
		if(cq == null){//���������
			
			return null;
		}
		if(cq.getSize() == 0){//�������Ϊ��
			commands.remove(meterId);
			
			return null;
		}
		if(!isExecute(meterId)){//δ����ִ�е�����
			CommandAttach ca = cq.pop();
			executeCommands.put(meterId, ca);	
			
			return ca;
		}		
		
		return null;
	}
	/**
	 * ����豸ȫ������
	 * @param meterId
	 */
	public void removeAll(String meterId){
		executeCommands.remove(meterId);
		commands.remove(meterId);		
	}
	
	/**
	 * �����ִ�������¼
	 * @param meterId
	 */
	public void cleanCommand(){
	    Enumeration<String> es = commands.keys();
		while(es.hasMoreElements()){
			String meterId = es.nextElement();
			CommandQueue cq = commands.get(meterId);
			if(cq == null){//���������
				
				return ;
			}
			if(cq.getSize() == 0){//�������Ϊ��
				commands.remove(meterId);
			}
		}	
	}
	/**
	 * �Ƿ�ִ������
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
	 * ����ɹ�֪ͨ
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
					ca.notifyAll();//��������ȴ�
					executeCommands.remove(meterId);//���ִ���������¼
				}
				
				return ca.getCommandId();
			}
			
			return null;
		}
		
	}
	
	/**
	 * ����ʧ�ܴ���
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
