package com.automic.sso.session.config;

import com.automic.ACException;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import com.automic.sso.util.* ;
import com.automic.sso.session.server.* ;


public class Config {
	private Document doc;

	public void createDom(String filePath) throws ACException {
		URL configFileURL = Config.class.getResource(filePath);
		if (configFileURL == null) {
			throw new ACException("û�еõ�" + filePath + "����!");
		}
		try {
			SAXBuilder sb = new SAXBuilder();
			this.doc = sb.build(configFileURL);
		} catch (Exception e) {
			throw new ACException(filePath + "�﷨�д��󣬲�������DOM����!");
		}
		if (this.doc == null) {
			throw new ACException(filePath + "�﷨�д��󣬲�������DOM����!");
		}
	}

	/**
	 * �������������в���
	 * 
	 * @throws ACException
	 */
	@SuppressWarnings("unchecked")
	public void parseOptions(String filePath) throws ACException {
		Element root = this.doc.getRootElement();
		if (root == null) {
			throw new ACException(filePath + "�﷨�д��󣬲��ܵõ���Ԫ��!");
		}

		HashMap<String, String> hm = (HashMap<String, String>) SeServerContext
				.singleInstance().getObject(SeConstance.SSOCONFIGS);
		List list = root.getChildren();
		Iterator it = list.iterator();
		Element e = null;
		String name = null ;
		String value = null ;
		boolean validData = false ;//�Ǳ��ӷ������������
		Util u = new Util() ;
		while (it.hasNext()) {
			e = (Element) it.next();
			name = e.getName() ;
			value = e.getText() ;
			if(name.trim().equals(SeConstance.SESSIONTIME)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "��Ԫ��"+ SeConstance.SESSIONTIME + "������ֵ���������֣�");
				}
				validData = true ;
			}
			if(validData){
				validData = false ;
				hm.put(name, value);
			}
		}
	}
}
