/**
 * 
 */
package com.hxb.common.threadPool2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * @author Administrator
 *
 */
public class ThreadPool {

	private ArrayList<ThreadTask> freeQueue = new ArrayList<ThreadTask>(0);//空闲线程序列
	private ArrayList<ThreadTask> busiQueue = new ArrayList<ThreadTask>(0);//任务线程序列
	private int minThreads = 0;//默认最小线程数
	private int maxThreads = 10;//默认最大线程数
	private String cfgName = "/threadPool.cfg.xml";
	/**	 * ThreadPool construct method
	 * initialized the pool parameters and the freeQuee
	 */
	public ThreadPool(){
		//初始线程池参数
		SAXBuilder saxb = new SAXBuilder();
		try {
			if(ThreadPool.class.getResource(cfgName) == null){
				cfgName = "." + cfgName;
			}
			Document doc = saxb.build(new File(ThreadPool.class.getResource(cfgName).getFile()));
			Element root = doc.getRootElement();
			minThreads = Integer.parseInt(root.getChildText("minThreads"));
			maxThreads = Integer.parseInt(root.getChildText("maxThreads"));			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
		//初始化线程池
		for(int i=0;i<minThreads;i++){
			freeQueue.add(new ThreadTask(this,i));
		}
	}
	
	/**
	 * add task to freeQuee
	 * @param task
	 */
	public synchronized void freeThread(ThreadTask task){
		freeQueue.add(task);
		busiQueue.remove(task);
	}
	/**
	 * get task from freeQuee
	 * @param task
	 */
	public synchronized ThreadTask getThread(){
		if(freeQueue.size() == 0 && busiQueue.size()<maxThreads){
			freeQueue.add(new ThreadTask(this,busiQueue.size()));
		}
		if(freeQueue.size()>0){
			ThreadTask task = freeQueue.remove(0);
			busiQueue.add(task);
			return task;
		}
		return null;
		
	}
}
