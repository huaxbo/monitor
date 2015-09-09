package com.hxb.common.threadPool2;

import com.hxb.common.threadPool2.inter.TaskInter;

public class ThreadTask extends Thread {

	private ThreadPool pool;
	private TaskInter task;//���幤������
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
				task.execute();//����ִ��
				//�߳��ͷ�
				if(pool != null){
					pool.freeThread(this);
				}
			}
			
			waitTask();
		}
	}
	
	/**
	 * �������
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
	 * ��������
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
