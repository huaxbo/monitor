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
	
	//是否是系统进行调试，如果是，则不进行登录限制，也不进行权限验证
	public static boolean debug ;
	
	///////////////////////
	//单点登录配置量
	public static String[] controlUrl;//登录及权限控制的URL
	public static String[] releaseUrl;//在登录及权限控制的URL中，不需要控制的URL
	public static String outterIp;//外网ip
	public static String ssoInnerIp;//sso内网ip
	public static String localInnerIp;//业务内网ip
	public static String ssoAccess;//sso访问地址
	public static String defaultAcc;//默认访问子系统上下文
	public static String ssoUdpIp ;//sso系统IP 
	public static int ssoUdpPort ;// sso系统UDP接收端口号
	public static String localUdpIp ; //本系统UDP返回数据给本系统，所用的IP(可以是内网IP，也可以是外网IP)
	public static int udpSendPort ;//本系统UDP发送数据端口号
	public static int udpReceivePort ;//本系统UDP接收数据端口号
	public static int udpSendTimeOut ;//本系统UDP发送数据超时时长(毫秒)
	public static int udpReceiveTimeOut ;//本系统UDP接收数据超时时长(毫秒)
	public static int udpReceiveDataLen ;//本系统UDP接收数据字节长
	public static int waitReturnTimeOut ;//本系统接收命令回数的超时时长(秒)
	//等待登录返回本系统的最大时长(秒)，
	//如果超过个时间，即使登录成功返回了，SSO要求其还要登录 
	//最小取值是60，最大取值是120
	public static int waitLoginTimeOut ;
//	cookieMaxTime 是针对没有固定IP的用户，
//	cookie存活时长(秒),取值范围是-1或大于36000 
//	取-1代表关闭浏览器cookie即失效
//	取36000代表10小时以上
	public static int cookieMaxTime ;
//	cookieMaxTimeForFixedIP 是针对有固定IP的用户，生成永不过期cookie
//	cookie存活时长(秒)取值范围是大于31536000秒(1年)
	public static int cookieMaxTimeForFixedIP ;
	//是否有权限控制 进行权限控制取值为true ,反之取值为false
	public static boolean checkPower ;
	
	
	///////////////////////
	//日志配置量
	public static Boolean isLog = false ;
	public static String logClass = null ;

	///////////////////////
	//常量
	//重定向子系统参数名称
	public static final String automicSubSysPara = "automicSubSysPara";
	//登录成功返回的URI(action 名称)
	public static final String automicSecureSsoUrlKey = "automicSecureSso";
	//等待登录缓存数据ID的KEY
	public static final String waitDataIdKey = "waitDataId";
	//会话中的用户对象KEY
	public static final String userKey = "lrywhq74041402D235UserDetail";

	//本系统UDP发送数据端口号
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
	 * 创建端口号
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
			throw new ServletException("secure.sso.xml配置文件存放的路径不正确!", null);
		}
		Document doc = null ;
		try {
			SAXBuilder sb = new SAXBuilder();
			doc = sb.build(configFileURL);
			if (doc == null) {
				throw new Exception("没有生成SSO配置文件的DOM对象!", null);
			}
		} catch (Exception e) {
			throw new ServletException("生成SSO配置文件的DOM对象失败!", e);
		}
		Element root = doc.getRootElement();
		if (root == null) {
			throw new ServletException("没有得到SSO配置文件根元素config!");
		}
		List list = root.getChildren();
		if (list == null) {
			throw new ServletException("SSO配置文件没有配置元素!");
		}

		Element e = null ;
		String temp = null ;
		
		e = root.getChild("debug") ;
		if(e == null){
			debug = false ;
		}else{
			temp = e.getText().trim() ;
			if (temp == null || temp.equals("") || (!temp.equals("true") && !temp.equals("false"))) {
				throw new ServletException("安全配置参数:是否debug状态配置不正确!");
			}
			debug = Boolean.parseBoolean(temp) ;
		}
		
		e = root.getChild("controlUrl") ;
		if(e == null){
			throw new ServletException("安全配置参数:控制的URL必须配置!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("")) {
				throw new ServletException("安全配置参数:控制的URL配置不正确!");
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
			throw new ServletException("安全配置参数:不进行验证的URL必须配置!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("")) {
				throw new ServletException("安全配置参数:不进行验证的URL配置不正确!");
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
			throw new ServletException("安全配置参数:本系统所在外网网络，外网网络IP必须配置!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("")) {
				throw new ServletException("安全配置参数:本系统所在外网网络，外网网络IP必须配置!");
			}
			outterIp = temp.trim() ;
		}
		
		e = root.getChild("ssoInnerIp") ;
		if(e == null){
			throw new ServletException("安全配置参数:SSO系统登录内网IP配置!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("")) {
				throw new ServletException("安全配置参数:SSO系统登录内网IP配置不正确!");
			}
			ssoInnerIp = temp.trim() ;
			ssoUdpIp = ssoInnerIp;
		}
		e = root.getChild("localInnerIp") ;
		if(e == null){
			throw new ServletException("安全配置参数:本地系统登录内网IP配置!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("")) {
				throw new ServletException("安全配置参数:本地系统登录内网IP配置不正确!");
			}
			localInnerIp = temp.trim() ;
			localUdpIp = localInnerIp;
		}
		e = root.getChild("ssoAccess") ;
		if(e == null){
			throw new ServletException("安全配置参数:SSO系统登录URL必须配置!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("")) {
				throw new ServletException("安全配置参数:SSO系统登录URL配置不正确!");
			}
			if(!temp.startsWith("http://")){
				temp = "http://" + temp ;
			}
			ssoAccess = temp.trim() ;
		}
		e = root.getChild("defaultAcc") ;
		if(e == null){
			throw new ServletException("安全配置参数:系统默认访问系统上下文必须配置!");
		}else{
			temp = e.getText() ;
			if (temp == null ) {
				throw new ServletException("安全配置参数:系统默认访问系统上下文配置不正确!");
			}
			defaultAcc = temp.trim();
		}
		e = root.getChild("ssoUdpPort") ;
		if(e == null){
			throw new ServletException("安全配置参数:sso系统UDP接收端口号必须配置!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("") || !isIntNumber(temp.trim())) {
				throw new ServletException("安全配置参数:sso系统UDP接收端口号配置不正确!");
			}
			ssoUdpPort = Integer.parseInt(temp.trim()) ;
		}
		
		e = root.getChild("udpSendTimeOut") ;
		if(e == null){
			throw new ServletException("安全配置参数:本系统UDP发送数据超时时长(毫秒)必须配置!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("") || !isIntNumber(temp.trim())) {
				throw new ServletException("安全配置参数:本系统UDP发送数据超时时长(毫秒)配置不正确!");
			}
			udpSendTimeOut = Integer.parseInt(temp.trim()) ;
		}
		
		e = root.getChild("udpReceiveTimeOut") ;
		if(e == null){
			throw new ServletException("安全配置参数:本系统UDP接收数据超时时长(毫秒)必须配置!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("") || !isIntNumber(temp.trim())) {
				throw new ServletException("安全配置参数:本系统UDP接收数据超时时长(毫秒)配置不正确!");
			}
			udpReceiveTimeOut = Integer.parseInt(temp.trim()) ;
		}
		
		e = root.getChild("udpReceiveDataLen") ;
		if(e == null){
			throw new ServletException("安全配置参数:本系统UDP接收数据字节长必须配置!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("") || !isIntNumber(temp.trim()) || Integer.parseInt(temp.trim()) < 1024) {
				throw new ServletException("安全配置参数:本系统UDP接收数据字节长配置不正确!");
			}
			udpReceiveDataLen = Integer.parseInt(temp.trim()) ;
		}
		
		e = root.getChild("waitReturnTimeOut") ;
		if(e == null){
			throw new ServletException("安全配置参数:本系统接收命令回数的超时时长(秒)必须配置!");
		}else{
			temp = e.getText() ;
			if (temp == null || temp.trim().equals("") || !isIntNumber(temp.trim()) || Integer.parseInt(temp.trim()) < 1) {
				throw new ServletException("安全配置参数:本系统接收命令回数的超时时长(秒)配置不正确!");
			}
			waitReturnTimeOut = (Integer.parseInt(temp.trim())) * 1000 ;
		}
		
		e = root.getChild("waitLoginTimeOut") ;
		if(e == null){
			throw new ServletException("安全配置参数:本系统接收命令回数的超时时长(秒)必须配置!");
		}else{
			temp = e.getText().trim() ;
			if (temp == null || temp.equals("") || !isIntNumber(temp) || Integer.parseInt(temp.trim()) < 1) {
				throw new ServletException("安全配置参数:本系统接收命令回数的超时时长(秒)配置不正确!");
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
			throw new ServletException("安全配置参数:无固定IP的用户cookie存活时长(秒)必须配置!");
		}else{
			temp = e.getText().trim() ;
			if (temp == null || temp.equals("") || !isIntNumber(temp)) {
				throw new ServletException("安全配置参数:无固定IP的用户cookie存活时长(秒)配置不正确!");
			}
			if (Integer.parseInt(temp.trim()) != -1 && Integer.parseInt(temp.trim()) < 36000) {
				throw new ServletException("安全配置参数:无固定IP的用户cookie存活时长(秒)配置不正确!");
			}
			cookieMaxTime = (Integer.parseInt(temp)) ;
		}
		e = root.getChild("cookieMaxTimeForFixedIP") ;
		if(e == null){
			throw new ServletException("安全配置参数:有固定IP的用户cookie存活时长(秒)必须配置!");
		}else{
			temp = e.getText().trim() ;
			if (temp == null || temp.equals("") || !isIntNumber(temp)) {
				throw new ServletException("安全配置参数:有固定IP的用户cookie存活时长(秒)配置不正确!");
			}
			if (Integer.parseInt(temp.trim()) < 31536000) {
				throw new ServletException("安全配置参数:有固定IP的用户cookie存活时长(秒)配置不正确!");
			}
			cookieMaxTimeForFixedIP = (Integer.parseInt(temp)) ;
		}
		
		
		e = root.getChild("checkPower") ;
		if(e == null){
			throw new ServletException("安全配置参数:是否有权限控制必须配置!");
		}else{
			temp = e.getText().trim() ;
			if (temp == null || temp.equals("") || (!temp.equals("true") && !temp.equals("false"))) {
				throw new ServletException("安全配置参数:是否有权限控制配置不正确!");
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
			throw new ServletException("secure.log.xml配置文件存放的路径不正确!", null);
		}
		Document doc = null ;
		try {
			SAXBuilder sb = new SAXBuilder();
			doc = sb.build(configFileURL);
			if (doc == null) {
				throw new Exception("没有生成SSO配置文件的DOM对象!", null);
			}
		} catch (Exception e) {
			throw new ServletException("生成SSO配置文件的DOM对象失败!", e);
		}
		Element root = doc.getRootElement();
		if (root == null) {
			throw new ServletException("没有得到SSO配置文件根元素config!");
		}
		List list = root.getChildren();
		if (list == null) {
			throw new ServletException("SSO配置文件没有配置元素!");
		}

		Element e = null ;
		String temp = null ;
		
		
		e = root.getChild("isLog") ;
		if(e == null){
			throw new ServletException("安全配置参数:日志记录是否执行配置isLog必须配置!");
		}else{
			temp = e.getText().trim() ; ;
			if (temp == null || temp.equals("") 
					|| (!temp.equals("true") && !temp.equals("false"))) {
				throw new ServletException("安全配置参数:日志记录是否执行配置isLog必须正确配置(true或false)!");
			}
			isLog = Boolean.parseBoolean(temp.trim());
		}
		
		if(isLog){
			e = root.getChild("logClass") ;
			if(e == null){
				throw new ServletException("安全配置参数:日志记录的执行类必须配置!");
			}else{
				temp = e.getText().trim() ; ;
				if (temp == null || temp.equals("")) {
					throw new ServletException("安全配置参数:日志记录的执行类必须配置!");
				}
				logClass = temp;
				try{
					Class clazz = Class.forName(logClass) ;
					Class[] interf = clazz.getInterfaces() ;
					boolean flag = false ;
					if(interf == null || interf.length == 0){
						throw new ServletException("安全配置参数:日志记录的执行类必须实现com.automic.common.secureLog.LogInterf接口!");
					}else{
						for(int i = 0 ; i < interf.length ; i++){
							if(interf[i].getName().equals(LogInterf.class.getName())){
								flag = true ;
								break ;
							}
						}
					}
					if(!flag){
						throw new ServletException("安全配置参数:日志记录的执行类必须实现" + LogInterf.class.getName() + "接口!");
					}
				}catch(Exception ee){
					throw new ServletException("安全配置参数:日志记录的执行类必须正确配置，并且该类实现" + LogInterf.class.getName() + "接口!");
				}
			}
		}
	}
	
	

	/**
	 * 判断是否是数字
	 * 
	 * @param str String
	 * @return boolean
	 */
	private static boolean isIntNumber(String str) {
		// 判断是否是数字
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
