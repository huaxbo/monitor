package com.hxb.common.threadPool2;

import com.hxb.common.threadPool2.impl.TaskImpl;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		//ÈÎÎñ·ÖÅä
		for(int i=0;i<20;i++){
			ThreadPoolManager.getThreadPoolManager("pool-1").addTask(new TaskImpl("task-1",null) {				
				@Override
				public boolean execute() {
					// TODO Auto-generated method stub
					System.out.println("###task-" + taskName + "- exetute...");
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{}
					
					return true;
				}
			});
		}
		
	}

}
