package com.hxb.util;

import java.net.URL;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class Config {

	private static Hashtable<String, String> configs;
	private static Config config;
	
	/**
	 * 得到唯一实例
	 * 
	 * @return
	 */
	public static Config getInstance() {
		if (config == null) {
			config = new Config();
			configs = new Hashtable<String, String>();
		}
		return config;
	}
	    
	private Config() {
	};	
	/**
	 * 根据配置名称得到配置值
	 * 
	 * @param name
	 * @return
	 */
	public static String getConfig(String name) {
		if (configs == null) {
			return null;
		}
		return (String) configs.get(name);
	}

	/**
	 * 初始化配置
	 * 
	 * @param fileUrl
	 * @throws MtException
	 */
	public void init(URL fileUrl) throws Exception {
		parseConfig(createDom(fileUrl));		
	}
	/**
	 * 重新初始化
	 * 
	 * @param fileUrl
	 * @throws Exception
	 */
	public void reInit(URL fileUrl) throws Exception {
		configs = new Hashtable<String, String>();
		parseConfig(createDom(fileUrl));
	}

	/**
	 * 
	 * @return
	 */
	private Document createDom(URL configFileURL) throws Exception {
		if (configFileURL == null) {
			throw new Exception("没有得到配置文件!", null);
		}
		Document doc = null;
		try {
			SAXBuilder sb = new SAXBuilder();
			doc = sb.build(configFileURL);
			if (doc == null) {
				throw new Exception("没有生成配置文件的DOM对象!", null);
			}
		} catch (Exception e) {
			throw new Exception("生成配置文件的DOM对象失败!", e);
		}
		return doc;
	}

	/**
	 * 分析服务器运行参数
	 * 
	 * @throws CommuException
	 */
	@SuppressWarnings("unchecked")
	private void parseConfig(Document doc) throws Exception {
		Element root = doc.getRootElement();
		if (root == null) {
			throw new Exception("没有得到配置文件根元素config!");
		}
		List list = root.getChildren();
		if (list == null) {
			throw new Exception("配置文件没有配置元素!");
		}
		Iterator it = list.iterator();
		Element e = null;
		String name = null;
		String tx = null;
		while (it.hasNext()) {
			e = (Element) it.next();
			if (e == null) {
				throw new Exception("得到配置文件中元素配置出错!");
			}
			name = e.getName();
			if (configs.get(name) != null) {
				throw new Exception("配置文件中元素" + name + "重名！");
			}
			tx = e.getText();
			if (tx == null || tx.trim().equals("")) {
				throw new Exception("配置文件中" + name + "配置内容为空！");
			}
			
			configs.put(name, tx);	
		}
	}
}
