package com.hxb.monitor.commu.autoTask.autoRlt;

public class AutoRltHandler {

	private static AutoRltHandler handler;
	
	/**
	 * 
	 */
	private AutoRltHandler(){}

	
	/**
	 * @return
	 */
	public static AutoRltHandler getSingleOne(){
		if(handler == null){
			handler = new AutoRltHandler();
		}
		
		return handler;
	}
	
	
	/**
	 * ����������
	 * @param rlt
	 */
	public void execute(Object rlt) throws Exception{
		
				
	}
}
