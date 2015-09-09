package com.automic.sso.commu.udpServer.config;

import com.automic.ACException;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import com.automic.sso.util.* ;
import com.automic.sso.commu.udpServer.server.* ;

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

		HashMap<String, String> hm = (HashMap<String, String>) UdpServerContext
				.singleInstance().getObject(UdpServerConstance.UDPCONFIGS);
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
			if(name.trim().equals(UdpServerConstance.UDPSENDPORT)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPSENDPORT + "������ֵ���������֣�");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPRECEIVEPORT)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPRECEIVEPORT + "������ֵ���������֣�");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPSENDTIMEOUT)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPSENDTIMEOUT + "������ֵ���������֣�");
				}
				if(Long.parseLong(value) < 1000){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPSENDTIMEOUT + "������ֵ�������1000(����)��");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPRECEIVETIMEOUT)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPRECEIVETIMEOUT + "������ֵ���������֣�");
				}
				if(Long.parseLong(value) < 1000){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPRECEIVETIMEOUT + "������ֵ�������1000(����)��");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPRECEIVEDATALEN)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPRECEIVEDATALEN + "������ֵ���������֣�");
				}
				if(Long.parseLong(value) > 65535 || Long.parseLong(value) < 256){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPRECEIVEDATALEN + "������ֵ�������256��С��65535��");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPMAXTHREADNUM)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPMAXTHREADNUM + "������ֵ���������֣�");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPMINTHREADNUM)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPMINTHREADNUM + "������ֵ���������֣�");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPFREETIMEOUT)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPFREETIMEOUT + "������ֵ���������֣�");
				}
				if(Long.parseLong(value) < 1000){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPFREETIMEOUT + "������ֵ�������1000���룡");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPBUSYTIMEOUT)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPBUSYTIMEOUT + "������ֵ���������֣�");
				}
				if(Long.parseLong(value) < 1000){
					throw new ACException(filePath + "��Ԫ��"+ UdpServerConstance.UDPBUSYTIMEOUT + "������ֵ�������1000���룡");
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
