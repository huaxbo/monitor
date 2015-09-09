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
			throw new ACException("没有得到" + filePath + "配置!");
		}
		try {
			SAXBuilder sb = new SAXBuilder();
			this.doc = sb.build(configFileURL);
		} catch (Exception e) {
			throw new ACException(filePath + "语法有错误，不能生成DOM对象!");
		}
		if (this.doc == null) {
			throw new ACException(filePath + "语法有错误，不能生成DOM对象!");
		}
	}

	/**
	 * 分析服务器运行参数
	 * 
	 * @throws ACException
	 */
	@SuppressWarnings("unchecked")
	public void parseOptions(String filePath) throws ACException {
		Element root = this.doc.getRootElement();
		if (root == null) {
			throw new ACException(filePath + "语法有错误，不能得到根元素!");
		}

		HashMap<String, String> hm = (HashMap<String, String>) UdpServerContext
				.singleInstance().getObject(UdpServerConstance.UDPCONFIGS);
		List list = root.getChildren();
		Iterator it = list.iterator();
		Element e = null;
		String name = null ;
		String value = null ;
		boolean validData = false ;//是本子服务的配置数据
		Util u = new Util() ;
		while (it.hasNext()) {
			e = (Element) it.next();
			name = e.getName() ;
			value = e.getText() ;
			if(name.trim().equals(UdpServerConstance.UDPSENDPORT)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPSENDPORT + "配置数值必须是数字！");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPRECEIVEPORT)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPRECEIVEPORT + "配置数值必须是数字！");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPSENDTIMEOUT)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPSENDTIMEOUT + "配置数值必须是数字！");
				}
				if(Long.parseLong(value) < 1000){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPSENDTIMEOUT + "配置数值必须大于1000(毫秒)！");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPRECEIVETIMEOUT)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPRECEIVETIMEOUT + "配置数值必须是数字！");
				}
				if(Long.parseLong(value) < 1000){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPRECEIVETIMEOUT + "配置数值必须大于1000(毫秒)！");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPRECEIVEDATALEN)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPRECEIVEDATALEN + "配置数值必须是数字！");
				}
				if(Long.parseLong(value) > 65535 || Long.parseLong(value) < 256){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPRECEIVEDATALEN + "配置数值必须大于256且小于65535！");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPMAXTHREADNUM)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPMAXTHREADNUM + "配置数值必须是数字！");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPMINTHREADNUM)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPMINTHREADNUM + "配置数值必须是数字！");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPFREETIMEOUT)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPFREETIMEOUT + "配置数值必须是数字！");
				}
				if(Long.parseLong(value) < 1000){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPFREETIMEOUT + "配置数值必须大于1000毫秒！");
				}
				validData = true ;
			}else if(name.trim().equals(UdpServerConstance.UDPBUSYTIMEOUT)){
				if(!u.isIntNumber(value)){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPBUSYTIMEOUT + "配置数值必须是数字！");
				}
				if(Long.parseLong(value) < 1000){
					throw new ACException(filePath + "中元素"+ UdpServerConstance.UDPBUSYTIMEOUT + "配置数值必须大于1000毫秒！");
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
