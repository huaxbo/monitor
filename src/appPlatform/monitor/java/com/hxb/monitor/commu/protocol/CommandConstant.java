/**
 * 
 */
package com.hxb.monitor.commu.protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


/**
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class CommandConstant {
	/**命令常量***/
	public static final String COMMAND_READ = "_commandRead";//“遥测”命令类型
	public static final String COMMAND_SET = "_commandSet";//”设置“命令类型
	public static final String COMMAND_FUNC = "_commandFunc";//“命令”命令类型
	
	public static final String ONLINE_STATUS_ON = "_on";//在线状态:在线
	public static final String ONLINE_STATUS_UN = "_un";//在线状态:不在线
	
	public static ArrayList<Protocol> protocolList;//命令集
	private static Object[][] protocols_command = null;//命令集中协议集合
	public static ArrayList<KeyValue> protocols_monitor = new ArrayList<KeyValue>();//设备选择协议集合
	private static Hashtable<String,String> protocol_key_value = new Hashtable<String,String>(0);//协议键－值对
	private static Hashtable<String,String> protocol_key_driver = new Hashtable<String,String>(0);//协议键-驱动对
	
	
	/**
	 * 初始化协议列表
	 */
	public static void initProtocols(){
		//读取协议集配置protocols-cfg.xml
		SAXBuilder jdom = new SAXBuilder();
		try {
			Document doc = jdom.build(CommandConstant.class.getResource("/config/config-protocols.xml").getFile());
			Element root = doc.getRootElement();
			List<Element> lt = root.getChildren();
			if(lt != null){
				GeneralProtocol[] gcs = new GeneralProtocol[lt.size()];
				int gcLen = 0;//成功配置的协议数目
				for(int i = 0;i < lt.size();i++){
					Element et = lt.get(i);
					String key = et.getAttribute("key").getValue();
					key = (key != null ? key.trim() : "");
					String value = et.getAttribute("value").getValue();
					String driver = et.getAttribute("driver").getValue();
					if(driver != null){
						protocol_key_driver.put(key, driver);
					}
					String clazz = et.getText();
					try {
						//此处将新协议对象添加至gcs中即实现：协议集、命令集自动同步
						GeneralProtocol gc = (GeneralProtocol)Class.forName(clazz.trim()).newInstance();
						gc.setProtocolKey(key);
						gc.setProtocolValue(value);
						gcs[i] = gc;
						gcLen++;
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{;}
				}
				//自动同步命令集中协议集合
				protocols_command = new Object[gcLen][3];
				for(int i = 0;i <gcLen;i++){
					protocols_command[i][0] = gcs[i].getProtocolKey();
					protocols_command[i][1] = gcs[i].getClass();
					protocols_command[i][2] = gcs[i];
				}
				try {
					protocolList = Protocol.getAllCommands(protocols_command);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					protocolList = new ArrayList<Protocol>(0);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					protocolList = new ArrayList<Protocol>(0);
				}finally{;}
				//自动同步设备选择协议集合
				for(int i = 0;i < gcLen;i++){
					GeneralProtocol gc = gcs[i];
					KeyValue kv = new KeyValue();
					kv.setKey(gc.getProtocolKey());
					kv.setValue(gc.getProtocolValue());
					protocols_monitor.add(kv);
					//维护协议键－值表
					protocol_key_value.put(gc.getProtocolKey(), gc.getProtocolValue());
				}
			}
		} catch (JDOMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			protocolList = new ArrayList<Protocol>(0);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			protocolList = new ArrayList<Protocol>(0);
		}finally{;}
	}
	
	/**
	 * 获取协议key键对应的值
	 * @param key
	 * @return
	 */
	public static String getProtocolValue(String key){
		
		return protocol_key_value.get(key);
	}
	/**
	 * 获取协议驱动
	 * @param pro
	 * @return
	 */
	public static String getProtocolDriver(String pro){
		
		return protocol_key_driver.get(pro);
	}
			
}
/**
 * key:value 对象
 * @author Administrator
 *
 */
class KeyValue {

	private String key;
	private String value;
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}