package com.hxb.monitor.commu.autoTask.cmdRlt;

public class CmdRltHandler {

	private static CmdRltHandler handler;
	
	/**
	 * 
	 */
	private CmdRltHandler(){}

	
	/**
	 * @return
	 */
	public static CmdRltHandler getSingleOne(){
		if(handler == null){
			handler = new CmdRltHandler();
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
