package com.hxb.common.threadPool2;

import com.hxb.common.threadPool2.inter.TaskInter;

public class ThreadTask extends Thread {

	private ThreadPool pool;
	private TaskInter task;//具体工作任务
	private int threadIndx;
	private Object synObj = new Object();
	/**
	 * ThreadTask construct method
	 * initialized the ThreadPool instance
	 * @param pool
	 */
	public ThreadTask(ThreadPool pool,int idx){
		this.pool = pool;
		this.threadIndx = idx;
		this.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(task != null){
				task.execute();//任务执行
				//线程释放
				if(pool != null){
					pool.freeThread(this);
				}
			}
			
			waitTask();
		}
	}
	
	/**
	 * 任务挂起
	 */
	public void waitTask(){
		try {
			synchronized(this.synObj){
				this.synObj.wait();	
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
	}
	/**
	 * 唤醒任务
	 * @param task
	 */
	public void notifyTask(TaskInter task){
		synchronized(this.synObj){
			this.task = task;
			this.synObj.notifyAll();		
		}
	}
	
	public TaskInter getTask() {
		return task;
	}

	public void setTask(TaskInter task) {
		this.task = task;
	}
	
	
	

}
