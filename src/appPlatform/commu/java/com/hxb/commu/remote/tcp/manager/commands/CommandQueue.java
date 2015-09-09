package com.hxb.commu.remote.tcp.manager.commands;

import java.util.ArrayList;
import java.util.List;

import com.hxb.commu.local.common.CommandAttach;

public class CommandQueue {

	private List<CommandAttach> commands = new ArrayList<CommandAttach>(0);
	
	/**
	 * 
	 */
	private CommandQueue(){}
	
	/**
	 * ��ȡʵ������
	 * @return
	 */
	public static CommandQueue getOne(){
		
		return new CommandQueue();
	}
		
	/**
	 * �������
	 * @param atta
	 */
	public void push(CommandAttach atta){
		commands.add(atta);
	}
	
	/**
	 * ��������
	 * @return
	 */
	public CommandAttach pop(){
		if(commands.size()>0){
			
			return commands.remove(0);
		}
		
		return null;
	}
	
	/**
	 * ��ȡ���д�С
	 * @return
	 */
	public int getSize(){
		
		return commands.size();
	}
	
}
