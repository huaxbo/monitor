/**
 * 
 */
package com.hxb.common.threadPool2.util;

import java.util.ArrayList;

import com.hxb.common.threadPool2.inter.TaskInter;

/**
 * @author Administrator
 *
 */
public class TaskQueue {

	private ArrayList<TaskInter> queue = new ArrayList<TaskInter>(0);//╤сап
	
	/**
	 * queue is empty
	 * @return true|false
	 */
	public boolean isEmpty(){
		return queue.size() == 0;
	}
	/**
	 * in queue
	 */
	public void inQueue(TaskInter task){
		queue.add(task);
	}
	/**
	 * out queue
	 * @return task or null
	 */
	public TaskInter outQueue(){
		if(!isEmpty()){
			return queue.remove(0);
		}
		return null;
	}
	
	
}
