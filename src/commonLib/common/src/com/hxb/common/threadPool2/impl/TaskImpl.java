package com.hxb.common.threadPool2.impl;

import java.util.HashMap;

import com.hxb.common.threadPool2.inter.TaskInter;

public abstract class TaskImpl implements TaskInter {

	public String taskName;//counter
	public HashMap<String,Object> params;
	/**
	 * @param idx
	 */
	public TaskImpl(String taskName,HashMap<String,Object> params){
		this.taskName = taskName;
		this.params = params;
	}
	
	/* (non-Javadoc)
	 * @see com.hxb.common.threadPool2.inter.TaskInter#execute()
	 */
	@Override
	public abstract boolean execute();	
}
