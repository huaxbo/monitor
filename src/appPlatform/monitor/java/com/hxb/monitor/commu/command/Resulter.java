/**
 * 
 */
package com.hxb.monitor.commu.command;

import java.util.HashMap;

import com.hxb.monitor.util.MonitorConstant;



/**
 * @author at
 * 处理召测命令返回结果
 */
public class Resulter {

	private static HashMap<String,Object[]> result = new HashMap<String,Object[]>(0);
	private int commandResultWaitTime = 10 * 1000;//等待命令结果时间（ms）
	
	/**
	 * get SendCommandResult instance
	 * @return
	 */
	public static Resulter getResulterInstance(){
		Resulter scr = new Resulter();
		scr.commandResultWaitTime = MonitorConstant.max_wait_cmdResult * 1000;
		
		return scr; 
	}
	/**
	 * 获取命令结果
	 * @param commandId
	 * @return
	 */
	public Object getCmdResult(String commandId){
		Object[] oo = new Object[]{null};
		if(!result.containsKey(commandId)){
			result.put(commandId, oo);
		}else{
			oo = result.get(commandId);
		}
		synchronized(oo){
			try {
				oo.wait(commandResultWaitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				oo = result.remove(commandId);
			}
		}
		
		return oo[0];
	}
	/**
	 * 放置命令结果
	 * @param commandId
	 * @param dp
	 */
	public void putCmdResult(String commandId,Object dp){
		Object[] oo = null;
		if(result.containsKey(commandId)){
			oo = result.get(commandId);
			synchronized(oo){
				oo[0] = dp;
				oo.notifyAll();
			}
		}
	}
}
