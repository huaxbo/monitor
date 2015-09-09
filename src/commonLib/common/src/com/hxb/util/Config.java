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
	 * �õ�Ψһʵ��
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
	 * �����������Ƶõ�����ֵ
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
	 * ��ʼ������
	 * 
	 * @param fileUrl
	 * @throws MtException
	 */
	public void init(URL fileUrl) throws Exception {
		parseConfig(createDom(fileUrl));		
	}
	/**
	 * ���³�ʼ��
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
			throw new Exception("û�еõ������ļ�!", null);
		}
		Document doc = null;
		try {
			SAXBuilder sb = new SAXBuilder();
			doc = sb.build(configFileURL);
			if (doc == null) {
				throw new Exception("û�����������ļ���DOM����!", null);
			}
		} catch (Exception e) {
			throw new Exception("���������ļ���DOM����ʧ��!", e);
		}
		return doc;
	}

	/**
	 * �������������в���
	 * 
	 * @throws CommuException
	 */
	@SuppressWarnings("unchecked")
	private void parseConfig(Document doc) throws Exception {
		Element root = doc.getRootElement();
		if (root == null) {
			throw new Exception("û�еõ������ļ���Ԫ��config!");
		}
		List list = root.getChildren();
		if (list == null) {
			throw new Exception("�����ļ�û������Ԫ��!");
		}
		Iterator it = list.iterator();
		Element e = null;
		String name = null;
		String tx = null;
		while (it.hasNext()) {
			e = (Element) it.next();
			if (e == null) {
				throw new Exception("�õ������ļ���Ԫ�����ó���!");
			}
			name = e.getName();
			if (configs.get(name) != null) {
				throw new Exception("�����ļ���Ԫ��" + name + "������");
			}
			tx = e.getText();
			if (tx == null || tx.trim().equals("")) {
				throw new Exception("�����ļ���" + name + "��������Ϊ�գ�");
			}
			
			configs.put(name, tx);	
		}
	}
}
