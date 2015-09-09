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
	/**�����***/
	public static final String COMMAND_READ = "_commandRead";//��ң�⡱��������
	public static final String COMMAND_SET = "_commandSet";//�����á���������
	public static final String COMMAND_FUNC = "_commandFunc";//�������������
	
	public static final String ONLINE_STATUS_ON = "_on";//����״̬:����
	public static final String ONLINE_STATUS_UN = "_un";//����״̬:������
	
	public static ArrayList<Protocol> protocolList;//���
	private static Object[][] protocols_command = null;//�����Э�鼯��
	public static ArrayList<KeyValue> protocols_monitor = new ArrayList<KeyValue>();//�豸ѡ��Э�鼯��
	private static Hashtable<String,String> protocol_key_value = new Hashtable<String,String>(0);//Э�����ֵ��
	private static Hashtable<String,String> protocol_key_driver = new Hashtable<String,String>(0);//Э���-������
	
	
	/**
	 * ��ʼ��Э���б�
	 */
	public static void initProtocols(){
		//��ȡЭ�鼯����protocols-cfg.xml
		SAXBuilder jdom = new SAXBuilder();
		try {
			Document doc = jdom.build(CommandConstant.class.getResource("/config/config-protocols.xml").getFile());
			Element root = doc.getRootElement();
			List<Element> lt = root.getChildren();
			if(lt != null){
				GeneralProtocol[] gcs = new GeneralProtocol[lt.size()];
				int gcLen = 0;//�ɹ����õ�Э����Ŀ
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
						//�˴�����Э����������gcs�м�ʵ�֣�Э�鼯������Զ�ͬ��
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
				//�Զ�ͬ�������Э�鼯��
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
				//�Զ�ͬ���豸ѡ��Э�鼯��
				for(int i = 0;i < gcLen;i++){
					GeneralProtocol gc = gcs[i];
					KeyValue kv = new KeyValue();
					kv.setKey(gc.getProtocolKey());
					kv.setValue(gc.getProtocolValue());
					protocols_monitor.add(kv);
					//ά��Э�����ֵ��
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
	 * ��ȡЭ��key����Ӧ��ֵ
	 * @param key
	 * @return
	 */
	public static String getProtocolValue(String key){
		
		return protocol_key_value.get(key);
	}
	/**
	 * ��ȡЭ������
	 * @param pro
	 * @return
	 */
	public static String getProtocolDriver(String pro){
		
		return protocol_key_driver.get(pro);
	}
			
}
/**
 * key:value ����
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