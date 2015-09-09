package com.hxb.common.secure.config;

import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

//import com.automic.common.secure.sso.SecureFilterSSO;
//import com.automic.common.secure.sso.help.WaitLoginCach;
import com.hxb.common.secure.config.help.CreateRadom;
import com.hxb.common.secure.log.LogInterf;

public class SecureConfig {
	
	//�Ƿ���ϵͳ���е��ԣ�����ǣ��򲻽��е�¼���ƣ�Ҳ������Ȩ����֤
	public static boolean debug ;
	
	///////////////////////
	//�����¼������
	public static String[] controlUrl;//��¼��Ȩ�޿��Ƶ�URL
	public static String[] releaseUrl;//�ڵ�¼��Ȩ�޿��Ƶ�URL�У�����Ҫ���Ƶ�URL
	public static String outterIp;//����ip
	public static String ssoInnerIp;//sso����ip
	public static String localInnerIp;//ҵ������ip
	public static String ssoAccess;//sso���ʵ�ַ
	public static String defaultAcc;//Ĭ�Ϸ�����ϵͳ������
	public static String ssoUdpIp ;//ssoϵͳIP 
	public static int ssoUdpPort ;// ssoϵͳUDP���ն˿ں�
	public static String localUdpIp ; //��ϵͳUDP�������ݸ���ϵͳ�����õ�IP(����������IP��Ҳ����������IP)
	public static int udpSendPort ;//��ϵͳUDP�������ݶ˿ں�
	public static int udpReceivePort ;//��ϵͳUDP�������ݶ˿ں�
	public static int udpSendTimeOut ;//��ϵͳUDP�������ݳ�ʱʱ��(����)
	public static int udpReceiveTimeOut ;//��ϵͳUDP�������ݳ�ʱʱ��(����)
	public static int udpReceiveDataLen ;//��ϵͳUDP���������ֽڳ�
	public static int waitReturnTimeOut ;//��ϵͳ������������ĳ�ʱʱ��(��)
	//�ȴ���¼���ر�ϵͳ�����ʱ��(��)��
	//���������ʱ�䣬��ʹ��¼�ɹ������ˣ�SSOҪ���仹Ҫ��¼ 
	//��Сȡֵ��60�����ȡֵ��120
	public static int waitLoginTimeOut ;
//	cookieMaxTime �����û�й̶�IP���û���
//	cookie���ʱ��(��),ȡֵ��Χ��-1�����36000 
//	ȡ-1����ر������cookie��ʧЧ
//	ȡ36000����10Сʱ����
	public static int cookieMaxTime ;
//	cookieMaxTimeForFixedIP ������й̶�IP���û���������������cookie
//	cookie���ʱ��(��)ȡֵ��Χ�Ǵ���31536000��(1��)
	public static int cookieMaxTimeForFixedIP ;
	//�Ƿ���Ȩ�޿��� ����Ȩ�޿���ȡֵΪtrue ,��֮ȡֵΪfalse
	public static boolean checkPower ;
	
	
	///////////////////////
	//��־������
	public static Boolean isLog = false ;
	public static String logClass = null ;

	///////////////////////
	//����
	//�ض�����ϵͳ��������
	public static final String automicSubSysPara = "automicSubSysPara";
	//��¼�ɹ����ص�URI(action ����)
	public static final String automicSecureSsoUrlKey = "automicSecureSso";
	//�ȴ���¼��������ID��KEY
	public static final String waitDataIdKey = "waitDataId";
	//�Ự�е��û�����KEY
	public static final String userKey = "lrywhq74041402D235UserDetail";

	//��ϵͳUDP�������ݶ˿ں�
	private static final int UDPMinPort = 11000;
	private static final int UDMaxPPort = 60000;
	public static final int maxTryTimes = 100 ;

	
	public static boolean inited = false ;
	
	static{
		if(!inited){
			try{
				initSsoConfig(getSsoConfigFileUrl()) ;
				initLogConfig(getLogConfigFileUrl()) ;
				if(udpSendPort == 0){
					udpSendPort = CreateRadom.create(UDPMinPort, UDMaxPPort) ;
					udpReceivePort = udpSendPort + 1 ;
				}
			}catch(Exception e){
				e.printStackTrace() ;
			}
		}
	}
	
	/**
	 * �����˿ں�
	 *
	 */
	/**
	 * 
	 */
	public static void createUdpPort(){
		udpSendPort = CreateRadom.create(UDPMinPort, UDMaxPPort) ;
		udpReceivePort = udpSendPort + 1 ;
	}
	
	/**
	 * @return
	 */
	private static URL getSsoConfigFileUrl(){
		URL configFileURL = SecureConfig.class.getResource("/secure.sso.xml");
		if(configFileURL == null){
			configFileURL = SecureConfig.class.getResource("/config/secure.sso.xml");
		}
		return configFileURL ;
	}
	/**
	 * @return
	 */
	private static URL getLogConfigFileUrl(){
		URL configFileURL = SecureConfig.class.getResource("/secure.log.xml");
		if(configFileURL == null){
			configFileURL = SecureConfig.class.getResource("/config/secure.log.xml");
		}
		return configFileURL ;
	}

	
	/**
	 * @param configFileURL
	 * @throws ServletException
	 */
	private static void initSsoConfig(URL configFileURL)throws ServletException {
		if(configFileURL == null){
			throw new ServletException("secure.sso.xml�����ļ���ŵ�·������ȷ!", null);
		}
		Document doc = null ;
		try {
			SAXBuilder sb = new SAXBuilder();
			doc = sb.build(configFileURL);
			if (doc == null) {
				throw new Exception("û������SSO�����ļ���DOM����!", null);
			}
		} catch (Exception e) {
			throw new ServletException("����SSO�����ļ���DOM����ʧ��!", e);
		}
		Element root = doc.getRootElement();
		if (root == null) {
			throw new ServletException("û�еõ�SSO�����ļ���Ԫ��config!");
		}
		List list = root.getChildren();
		if (list == null) {
			throw new ServletException("SSO�����ļ�û������Ԫ��!");
		}

		Element e = null ;
		String temp = null ;
		
		e = root.getChild("debug") ;
		if(e == null){
			debug = false ;
		}else{
			temp = e.getText().trim() ;
			if (temp == null || temp.equals("") || (!temp.equals("true") && !temp.equals("false"))) {
				throw new ServletException("��ȫ���ò���:�Ƿ�debug״̬���ò���ȷ!");
			}
			debug = Boolean.parseBoolean(temp) ;
		}
		
		e = root.getChild("controlUrl") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:���Ƶ�URL��������!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("")) {
				throw new ServletException("��ȫ���ò���:���Ƶ�URL���ò���ȷ!");
			}
			temp = temp.trim() ;
			while(true){
				if(temp.endsWith(",")){
					temp = temp.substring(0 , temp.length()-1) ;
					temp = temp.trim() ;
				}else{
					break ;
				}
			}
			controlUrl = temp.split(",");
			for(int i = 0 ; i < controlUrl.length ; i++){
				controlUrl[i] = controlUrl[i].trim() ;
			}
		}
		
		e = root.getChild("releaseUrl") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:��������֤��URL��������!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("")) {
				throw new ServletException("��ȫ���ò���:��������֤��URL���ò���ȷ!");
			}
			temp = temp.trim() ;
			while(true){
				if(temp.endsWith(",")){
					temp = temp.substring(0 , temp.length()-1) ;
					temp = temp.trim() ;
				}else{
					break ;
				}
			}
			releaseUrl = temp.split(",");
			for(int i = 0 ; i < releaseUrl.length ; i++){
				releaseUrl[i] = releaseUrl[i].trim() ;
			}
		}
		
		e = root.getChild("outterIp") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:��ϵͳ�����������磬��������IP��������!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("")) {
				throw new ServletException("��ȫ���ò���:��ϵͳ�����������磬��������IP��������!");
			}
			outterIp = temp.trim() ;
		}
		
		e = root.getChild("ssoInnerIp") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:SSOϵͳ��¼����IP����!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("")) {
				throw new ServletException("��ȫ���ò���:SSOϵͳ��¼����IP���ò���ȷ!");
			}
			ssoInnerIp = temp.trim() ;
			ssoUdpIp = ssoInnerIp;
		}
		e = root.getChild("localInnerIp") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:����ϵͳ��¼����IP����!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("")) {
				throw new ServletException("��ȫ���ò���:����ϵͳ��¼����IP���ò���ȷ!");
			}
			localInnerIp = temp.trim() ;
			localUdpIp = localInnerIp;
		}
		e = root.getChild("ssoAccess") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:SSOϵͳ��¼URL��������!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("")) {
				throw new ServletException("��ȫ���ò���:SSOϵͳ��¼URL���ò���ȷ!");
			}
			if(!temp.startsWith("http://")){
				temp = "http://" + temp ;
			}
			ssoAccess = temp.trim() ;
		}
		e = root.getChild("defaultAcc") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:ϵͳĬ�Ϸ���ϵͳ�����ı�������!");
		}else{
			temp = e.getText() ;
			if (temp == null ) {
				throw new ServletException("��ȫ���ò���:ϵͳĬ�Ϸ���ϵͳ���������ò���ȷ!");
			}
			defaultAcc = temp.trim();
		}
		e = root.getChild("ssoUdpPort") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:ssoϵͳUDP���ն˿ںű�������!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("") || !isIntNumber(temp.trim())) {
				throw new ServletException("��ȫ���ò���:ssoϵͳUDP���ն˿ں����ò���ȷ!");
			}
			ssoUdpPort = Integer.parseInt(temp.trim()) ;
		}
		
		e = root.getChild("udpSendTimeOut") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:��ϵͳUDP�������ݳ�ʱʱ��(����)��������!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("") || !isIntNumber(temp.trim())) {
				throw new ServletException("��ȫ���ò���:��ϵͳUDP�������ݳ�ʱʱ��(����)���ò���ȷ!");
			}
			udpSendTimeOut = Integer.parseInt(temp.trim()) ;
		}
		
		e = root.getChild("udpReceiveTimeOut") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:��ϵͳUDP�������ݳ�ʱʱ��(����)��������!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("") || !isIntNumber(temp.trim())) {
				throw new ServletException("��ȫ���ò���:��ϵͳUDP�������ݳ�ʱʱ��(����)���ò���ȷ!");
			}
			udpReceiveTimeOut = Integer.parseInt(temp.trim()) ;
		}
		
		e = root.getChild("udpReceiveDataLen") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:��ϵͳUDP���������ֽڳ���������!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("") || !isIntNumber(temp.trim()) || Integer.parseInt(temp.trim()) < 1024) {
				throw new ServletException("��ȫ���ò���:��ϵͳUDP���������ֽڳ����ò���ȷ!");
			}
			udpReceiveDataLen = Integer.parseInt(temp.trim()) ;
		}
		
		e = root.getChild("waitReturnTimeOut") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:��ϵͳ������������ĳ�ʱʱ��(��)��������!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("") || !isIntNumber(temp.trim()) || Integer.parseInt(temp.trim()) < 1) {
				throw new ServletException("��ȫ���ò���:��ϵͳ������������ĳ�ʱʱ��(��)���ò���ȷ!");
			}
			waitReturnTimeOut = (Integer.parseInt(temp.trim())) * 1000 ;
		}
		
		e = root.getChild("waitLoginTimeOut") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:��ϵͳ������������ĳ�ʱʱ��(��)��������!");
		}else{
			temp = e.getText().trim() ;
			if (temp == null || temp.equals("") || !isIntNumber(temp) || Integer.parseInt(temp.trim()) < 1) {
				throw new ServletException("��ȫ���ò���:��ϵͳ������������ĳ�ʱʱ��(��)���ò���ȷ!");
			}
			waitLoginTimeOut = (Integer.parseInt(temp)) * 1000 ;
			if(waitLoginTimeOut < 60 * 1000){
				waitLoginTimeOut = 60 * 1000 ;
			}
			if(waitLoginTimeOut > 120 * 1000){
				waitLoginTimeOut = 120 * 1000 ;
			}
		}
		
		e = root.getChild("cookieMaxTime") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:�޹̶�IP���û�cookie���ʱ��(��)��������!");
		}else{
			temp = e.getText().trim() ;
			if (temp == null || temp.equals("") || !isIntNumber(temp)) {
				throw new ServletException("��ȫ���ò���:�޹̶�IP���û�cookie���ʱ��(��)���ò���ȷ!");
			}
			if (Integer.parseInt(temp.trim()) != -1 && Integer.parseInt(temp.trim()) < 36000) {
				throw new ServletException("��ȫ���ò���:�޹̶�IP���û�cookie���ʱ��(��)���ò���ȷ!");
			}
			cookieMaxTime = (Integer.parseInt(temp)) ;
		}
		e = root.getChild("cookieMaxTimeForFixedIP") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:�й̶�IP���û�cookie���ʱ��(��)��������!");
		}else{
			temp = e.getText().trim() ;
			if (temp == null || temp.equals("") || !isIntNumber(temp)) {
				throw new ServletException("��ȫ���ò���:�й̶�IP���û�cookie���ʱ��(��)���ò���ȷ!");
			}
			if (Integer.parseInt(temp.trim()) < 31536000) {
				throw new ServletException("��ȫ���ò���:�й̶�IP���û�cookie���ʱ��(��)���ò���ȷ!");
			}
			cookieMaxTimeForFixedIP = (Integer.parseInt(temp)) ;
		}
		
		
		e = root.getChild("checkPower") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:�Ƿ���Ȩ�޿��Ʊ�������!");
		}else{
			temp = e.getText().trim() ;
			if (temp == null || temp.equals("") || (!temp.equals("true") && !temp.equals("false"))) {
				throw new ServletException("��ȫ���ò���:�Ƿ���Ȩ�޿������ò���ȷ!");
			}
			checkPower = Boolean.parseBoolean(temp) ;
		}
		
	}
	
	/**
	 * @param configFileURL
	 * @throws ServletException
	 */
	private static void initLogConfig(URL configFileURL)throws ServletException {
		if(configFileURL == null){
			throw new ServletException("secure.log.xml�����ļ���ŵ�·������ȷ!", null);
		}
		Document doc = null ;
		try {
			SAXBuilder sb = new SAXBuilder();
			doc = sb.build(configFileURL);
			if (doc == null) {
				throw new Exception("û������SSO�����ļ���DOM����!", null);
			}
		} catch (Exception e) {
			throw new ServletException("����SSO�����ļ���DOM����ʧ��!", e);
		}
		Element root = doc.getRootElement();
		if (root == null) {
			throw new ServletException("û�еõ�SSO�����ļ���Ԫ��config!");
		}
		List list = root.getChildren();
		if (list == null) {
			throw new ServletException("SSO�����ļ�û������Ԫ��!");
		}

		Element e = null ;
		String temp = null ;
		
		
		e = root.getChild("isLog") ;
		if(e == null){
			throw new ServletException("��ȫ���ò���:��־��¼�Ƿ�ִ������isLog��������!");
		}else{
			temp = e.getText().trim() ; ;
			if (temp == null || temp.equals("") 
					|| (!temp.equals("true") && !temp.equals("false"))) {
				throw new ServletException("��ȫ���ò���:��־��¼�Ƿ�ִ������isLog������ȷ����(true��false)!");
			}
			isLog = Boolean.parseBoolean(temp.trim());
		}
		
		if(isLog){
			e = root.getChild("logClass") ;
			if(e == null){
				throw new ServletException("��ȫ���ò���:��־��¼��ִ�����������!");
			}else{
				temp = e.getText().trim() ; ;
				if (temp == null || temp.equals("")) {
					throw new ServletException("��ȫ���ò���:��־��¼��ִ�����������!");
				}
				logClass = temp;
				try{
					Class clazz = Class.forName(logClass) ;
					Class[] interf = clazz.getInterfaces() ;
					boolean flag = false ;
					if(interf == null || interf.length == 0){
						throw new ServletException("��ȫ���ò���:��־��¼��ִ�������ʵ��com.automic.common.secureLog.LogInterf�ӿ�!");
					}else{
						for(int i = 0 ; i < interf.length ; i++){
							if(interf[i].getName().equals(LogInterf.class.getName())){
								flag = true ;
								break ;
							}
						}
					}
					if(!flag){
						throw new ServletException("��ȫ���ò���:��־��¼��ִ�������ʵ��" + LogInterf.class.getName() + "�ӿ�!");
					}
				}catch(Exception ee){
					throw new ServletException("��ȫ���ò���:��־��¼��ִ���������ȷ���ã����Ҹ���ʵ��" + LogInterf.class.getName() + "�ӿ�!");
				}
			}
		}
	}
	
	

	/**
	 * �ж��Ƿ�������
	 * 
	 * @param str String
	 * @return boolean
	 */
	private static boolean isIntNumber(String str) {
		// �ж��Ƿ�������
		if(str == null || str.trim().equals(""))
			return false ;
		
		if(str.startsWith("-")){
			str = str.substring(1 , str.length()) ;
		}
		for (int i = 0; i < str.length(); i++) {
			if (new String("9876543210").indexOf(str.substring(i, i + 1)) == -1) {
				return false;
			}
		}
		return true;
	}

}
