/**
 * 
 */
package com.hxb.common.threadPool2;

import java.util.Hashtable;

import com.hxb.common.threadPool2.inter.TaskInter;
import com.hxb.common.threadPool2.util.TaskQueue;

/**
 * @author Administrator
 *
 */
public class ThreadPoolManager {

	private static Hashtable<String,ThreadPoolManager> managers = new Hashtable<String,ThreadPoolManager>(0);
		
	private ThreadPool pool;//thread pool
	private TaskQueue queue;
	private boolean running = false;
	/**
	 * build
	 */
	private ThreadPoolManager(){}
	
	/**
	 * get single ThreadPoolManager instance by poolName
	 * param:poolName
	 */
	public synchronized static ThreadPoolManager getThreadPoolManager(String poolName){
		ThreadPoolManager manager = managers.get(poolName);
		if(manager == null){
			manager = new ThreadPoolManager();
			manager.pool = new ThreadPool();
			manager.queue = new TaskQueue();
			managers.put(poolName, manager);
			
			manager.startTaskHandle();
		}
		
		return manager;
	}
	
	/**
	 * start task handle
	 */
	public void startTaskHandle(){
		if(!running){
			new Thread(new TaskHandleThread()).start();
			running = true;
		}		
	}
	
	/**
	 * 添加新任务
	 * @param task
	 */
	public void addTask(TaskInter task){
		if(queue != null){
			queue.inQueue(task);
		}
	}
	/**
	 * @author Administrator
	 *
	 */
	private class TaskHandleThread implements Runnable{
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				if(!queue.isEmpty()){
					TaskInter task = queue.outQueue();
					ThreadTask thTask = pool.getThread();
					while(thTask == null){//获取空闲线程，处理任务
						thTask = pool.getThread();
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{}
					}
					thTask.notifyTask(task);//执行任务
				}else{
					try {
						Thread.sleep(1000);//等待1s
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{}
				}
			}
		}
		
	}
}
