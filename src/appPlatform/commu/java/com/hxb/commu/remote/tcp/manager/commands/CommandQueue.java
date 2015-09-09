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
	 * 获取实例对象
	 * @return
	 */
	public static CommandQueue getOne(){
		
		return new CommandQueue();
	}
		
	/**
	 * 加入队列
	 * @param atta
	 */
	public void push(CommandAttach atta){
		commands.add(atta);
	}
	
	/**
	 * 弹出队列
	 * @return
	 */
	public CommandAttach pop(){
		if(commands.size()>0){
			
			return commands.remove(0);
		}
		
		return null;
	}
	
	/**
	 * 获取队列大小
	 * @return
	 */
	public int getSize(){
		
		return commands.size();
	}
	
}
