package com.hxb.common.secure.sso;

import java.io.*;
import java.net.*;
import org.apache.log4j.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;

import com.hxb.ACException;
import com.hxb.common.secure.SessionUserHolder;
import com.hxb.common.secure.config.SecureConfig;
import com.hxb.common.secure.sso.help.*;
import com.hxb.common.secure.sso.socket.*;
import com.hxb.common.sso.commu.udpServer.*;
import com.hxb.common.sso.login.SSOCookie;
import com.hxb.common.sso.login.SSOReferer;
import com.hxb.common.sso.session.*;


public class SecureSsoFilter implements Filter {

	private static Logger log = Logger.getLogger(SecureSsoFilter.class.getName());
	
	//为DWR框架应用，标识会话过期
	public static int  SESSIONTIMEOUT = 1000 ;

 	//等待登录返回的最大时长
	private static WaitLoginCach waitLoginCach ;

	/////////////////////////
	//非静态变量
	private String error ;
	
	
	public void destroy() {
	}
	/**
	 * 过滤器初始化
	 */
	public void init(FilterConfig config) throws ServletException {
		//启动等待登录成功数据缓存线程
		if(!SecureConfig.debug){
			WaitLoginCach.setMaxWaitLoginTime(SecureConfig.waitLoginTimeOut) ;
			waitLoginCach = new WaitLoginCach() ;
			waitLoginCach.start() ;
			
			this.initUdpSocket(0) ;
			
			System.out.println("单点登录客户端:发送数据(UDP)端口号:" + SecureConfig.udpSendPort) ;
			System.out.println("单点登录客户端:接收数据(UDP)端口号:" + SecureConfig.udpReceivePort) ;
			
			
			System.out.println("单点登录网络客户端已经启动");
		}
	}
	
	/**
	 * 启动单点登录的UDP网络连接
	 * @param count
	 */
	private void initUdpSocket(int count)throws ServletException{
		try{
			//启动发送数据UDP socket
			UdpSendManager.initFirst(SecureConfig.udpSendPort, SecureConfig.udpSendTimeOut) ;
			UdpSendManager sender = UdpSendManager.singleInstance() ;
			sender.start() ;
			//启动接收数据UDP socket
			UdpReceiveManager.initAndStartFirst(
					SecureConfig.udpReceivePort, 
					SecureConfig.udpReceiveTimeOut , 
					SecureConfig.udpReceiveDataLen) ;

		}catch(SocketException e){
			System.out.println("单点登录客户端第" + (count + 1) + "尝试创建UDP(端口号:" + SecureConfig.udpSendPort + "失败！！") ;
			if(count > SecureConfig.maxTryTimes){
				//尝试次数大于最大限制
				e.printStackTrace() ;
				throw new ServletException("安全过滤器:启动收发送数据UDP 单点登录通信服务失败！原因：" + e.getMessage()  , e) ;
			}else{
				count ++ ;
				SecureConfig.createUdpPort() ;
				this.initUdpSocket(count) ;
			}
		}catch(ACException e){
			e.printStackTrace() ;
			throw new ServletException("安全过滤器:启动接收数据UDP 单点登录服务失败！" , e) ;
		}
	}

	/**
	 * 过滤
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	
		if(!SecureConfig.debug){//非调试模式-校验
			log.info("进入SuecureFilterSSO过滤器。") ;

			HttpServletRequest hr = (HttpServletRequest) request;
			HttpServletResponse hrs = (HttpServletResponse) response;
			String path = hr.getServletPath();
			if (this.isNeedFilter(path)) {//需要校验url处理
				log.info("需要过滤当前路径：" + path) ;
				if (this.toFilter(hr, hrs, path)) {//url权限验证过滤
					chain.doFilter(request, response);
				}
			} else {//无需校验url处理
				log.info("不需要过滤当前路径：" + path) ;
				chain.doFilter(request, response);
				//刷新SSO中的会话
				this.freshSession(request) ;
			}
		}else{//调试模式-放行
			chain.doFilter(request, response);
		}
	}

	/**
	 * 判断是否需要验证(会话验证和权限验证)
	 * @param path
	 * @return
	 */
	private boolean isNeedFilter(String path) {
		String conUrl = null ;
		for (int i = 0; SecureConfig.releaseUrl != null && i < SecureConfig.releaseUrl.length; i++) {
			
			conUrl = SecureConfig.releaseUrl[i] ;
			
			if(conUrl.startsWith("*") || conUrl.endsWith("*")){
				conUrl = conUrl.replaceAll("\\*", "") ;
				if(!conUrl.equals(".js") && path.contains(conUrl)){//非js文件且包含conUrl字符的url，无需过滤
					return false ;
				}
			}else{
				if (path.endsWith(conUrl)) {//以conUrl结尾的url，无需过滤
					return false;
				}
			}
		}

		for (int i = 0; SecureConfig.controlUrl != null && i < SecureConfig.controlUrl.length; i++) {//属于controlUrl中的url进行过滤
			if (path.endsWith(SecureConfig.controlUrl[i])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 进行验证
	 * @param hr
	 * @param hrs
	 * @param path
	 */
	private boolean toFilter(HttpServletRequest hr, HttpServletResponse hrs,
			String path) throws IOException, ServletException {
		boolean nextFilter = true;
		//判断是否为注销操作
		if(hr.getServletPath() != null && hr.getServletPath().equals("/glo/logoutAction.action")){
			closeSession(hr);//注销
		}	
		
		//首先尝试从会话中得到用户信息
		//如果得到了，说明用户已经成功登录系统了
		SessionUserVO uvo = (SessionUserVO) hr.getSession().getAttribute(SecureConfig.userKey);
	    if(uvo != null){
			//用户登录后的正常访问
			//把用户信息放入线程级的安全控制器中，以备进行权限验证
        	if(SecureConfig.checkPower){
        		SessionUserHolder.setUserInfo(uvo);
        	}
			return nextFilter ;
		}
	    
		hr.setAttribute("serverName", hr.getServerName());//回执服务ip或域名
		hr.setAttribute("contextName", hr.getContextPath().equals("") ? "/" : hr.getContextPath());//回执服务上下文名称
		
		String requestUrl = hr.getRequestURL().toString() ;
		/////////////////////////////////////
		//到此，判断当前访问是否为登录返回
		//如果是成功登录返回，进行成功登录返回的相关操作
		if(requestUrl.indexOf(SecureConfig.automicSecureSsoUrlKey) > 0){
			//登录成功，返回创建的cookie
			log.info("登录成功返回。") ;
			String waitDataId = hr.getParameter(SecureConfig.waitDataIdKey) ;
			String sessionId = hr.getParameter(SSOReferer.sessionId) ;
			if(waitDataId == null 
					|| sessionId == null  
					|| waitDataId.trim().equals("") 
					|| sessionId.trim().equals("") ){
				//没有参数，可能人为设置的automicSecureSsoUrlKey访问路径进行登录
				log.info("登录成功返回，但URL中无waitDataId和sessionId，重新登录！");
				this.toSsoLogin(hr , hrs) ;
				return nextFilter = false ;
			}else{
				WaitLoginData wd = WaitLoginCach.getWaitLoginData(waitDataId) ;
				if(wd == null){
					//说明等待登录超时了,或者用户登录成功后，在按浏览器的回退按钮到登录页面，
					//进行再次登录，这时对应waitDataId的数据已经在第一次登录时清空了.
					//System.out.println("登录成功返回，但等待数据已经清空，重新登录！") ;
					log.info("登录成功返回，但等待数据已经清空，重新登录！") ;
					this.toSsoLogin(hr, hrs) ;
					return nextFilter = false ;
				}else{
					//登录成功，转向最初访问的页面URL
					log.info("登录成功返回，创建cookie并转向原访问URL！") ;
					//从SSO得到用户信息
					uvo = this.getUserInfoFromSSO(sessionId , hr ,  hrs) ;
					error = null ;
					this.creatCookieAndRedirectReffer(hr, hrs , sessionId ,wd.refererUrl , uvo) ; 
					return nextFilter = false ;
				}
			}
		}
		
		//////////////////////////////////////////////
		//到此，会话中没有用户信息，访问也不是登录返回,
		//得到cookie的信息
		Cookie[] cookies = hr.getCookies() ;
		if(cookies == null){
			//说明用户请求没有cookie，表示用户的当前从未登录，转向SSO登录
			log.info("当前访问无cookie，转向登录！") ;
			this.toSsoLogin(hr, hrs) ;
			return nextFilter = false ;
		}
		String sessionId = null ;
		String fixedIpUserInfo = null ;
		for(int i = 0 ; i < cookies.length ; i++){
			if(cookies[i].getName().equals(SSOCookie.cookieForever_NameKey)){
				fixedIpUserInfo = cookies[i].getValue() ;
				break ;
			}
			if(cookies[i].getName().equals(SSOCookie.cookieInstant_NameKey)){
				sessionId = cookies[i].getValue() ;
				break ;
			}
			if(cookies[i].getName().equals(SSOCookie.cookieLocal_NameKey)){
				sessionId = cookies[i].getValue() ;
				break ;
			}
		}
		if(sessionId == null && fixedIpUserInfo == null ){
			//说明不能在cookie中得到会话ID 
			//表示用户在本系统从未登录，转向SSO登录
			log.info("当前访问的cookie中不存会话sessionId也不存在永久IP的用户信息，重新登录！") ;
			this.toSsoLogin(hr, hrs) ;
			return nextFilter = false ;
		}
		
		
		
		//////////////////////////////////////////
		//到此，用户请求中存在cookie，并且cookie有效
		//cookie中存在有固定IP的用户信息
		//cookie是永不过期的(实际为10年，参考下面生成cookie的部分)
		if(fixedIpUserInfo != null ){
			//当前系统会话中没有用户信息，但cookie有效，
			//可能存在的情况：永久cookie但用户从未登录系统 或者是 用户已经成功登录其他子系统，
			//首先尝试从SSO系统中得到用户信息
			//如果从SSO中得到了用户信息，说明用户已经登录了其他子系统了，在本子系统会话中还没有用户信息，那么把用户信息存在本子系统会话中
			//如果从SSO中不能得到用户信息，说明用户在所有子系统中都未登录，从用户cookie中得到用户信息，通过SSO直接登录。
			//这里可能会产生error信息
			String ip = hr.getRemoteAddr() ;
			uvo = this.getUserInfoFromSSO(ip , hr , hrs) ;
			if(error == null){
				if(uvo == null){
					String[] namePassIp = null ;
					String fixedIpUserInfoStr = null ;
					try{
						Crypt cry = new Crypt() ;
						byte[] key = cry.getEasyKey() ;
						byte[] enBin = cry.hex2Byte(fixedIpUserInfo) ;
						byte[] de = cry.decode(enBin , key) ;
						fixedIpUserInfoStr = new String(de) ;
						namePassIp = fixedIpUserInfoStr.split(";") ;
					}catch(Exception e){
						//cookie受到破坏
						log.info("当前访问的cookie中存在固定IP用户的信息，但cookie受到破坏，重新登录！") ;
						this.toSsoLogin(hr, hrs) ;
						return nextFilter = false ;
					}
					if(namePassIp == null || namePassIp.length != 3){
						//cookie受到破坏
						log.info("当前访问的cookie中存在固定IP用户的信息，但用户的信息受到破坏，重新登录！") ;
						this.toSsoLogin(hr, hrs) ;
						return nextFilter = false ;
					}
					if(!ip.equals(namePassIp[2])){
						//偷的cookie，或者是用户实际IP已经变化了
						log.info("当前访问的cookie中存在固定IP用户的信息，但当前访问IP与cookie中的用户固定IP不相同，重新登录！") ;
						this.toSsoLogin(hr, hrs) ;
						return nextFilter = false ;
					}else{
						//用户从未登录系统，
						//用户直接登录
						uvo = this.directLoginBySSO(namePassIp[2] , hr , hrs , fixedIpUserInfoStr) ;
						if(error == null){
							if(uvo == null){
								//从SSO系统中未得到信息，
								//正常原因是SSO系统中 ,当前用户登录已经会话过期,或重新启动了服务器；
								//其他原因就发生了错误
								log.info("当前访问存在cookie，但从SSO服务器得不到登录用户对象，重新登录！") ;
								this.toSsoLogin(hr, hrs) ;
								nextFilter = false;
							}else{
								//从sso中得到了用户信息
								nextFilter = true;
							}
						}
					}
				}else{
					//从sso中得到了用户信息
					nextFilter = true;
				}
			}
		}
		
		////////////////////////////////////////////
		//到此，非固定IP用户，用户请求中存在cookie，这个cookie是即时cookie，
		//cookie中存在会话ID(sessionId)，说明用户客户端仍是有效登录状态
		if(sessionId != null && fixedIpUserInfo == null){
			//当前系统会话中没有用户信息，但cookie有效，原因：会话过期 或者是 用户已经成功登录其他子系统，
			//重新从SSO系统中得到用户信息
			//这里可能会产生error信息
			uvo = this.getUserInfoFromSSO(sessionId , hr , hrs) ;
			if(error == null){
				if(uvo == null){
					//从SSO系统中未得到信息，
					//正常原因是SSO系统中 ,当前用户登录已经会话过期,或重新启动了服务器；
					//其他原因就发生了错误
					log.info("当前访问存在cookie，但从SSO服务器得不到登录用户对象，重新登录！") ;
					this.toSsoLogin(hr, hrs) ;
					nextFilter = false;
				}else{
					//从sso中得到了用户信息
					nextFilter = true;
				}
			}
		}
		
		
		///////////////////////
		//处理过程中产生错误
		if(error != null){
			this.redirectShowError(hrs , error) ;
			error = null ;
			nextFilter = false;
		}

		if(!nextFilter){
			return nextFilter ;
		}
		
		////////////////////////////
		//把用户信息放入线程级的安全控制器中，以备进行权限验证
        if(uvo != null){
        	if(SecureConfig.checkPower){
        		SessionUserHolder.setUserInfo(uvo);
        	}
			nextFilter = true;
		}
		return nextFilter;
	}
	
	/**
	 * 从SSO系统中得到登录后的会话用户信息
	 * @param sessionId
	 * @return
	 */
	@SuppressWarnings("finally")
	private SessionUserVO getUserInfoFromSSO(String sessionId , HttpServletRequest rq , HttpServletResponse hrs ){
		SessionUserVO vo = null ;
		try{
			UdpSendManager send = UdpSendManager.singleInstance() ;
			
			SsoData sd = new SsoData() ;
			sd.order = SsoOrder.checkLogin ;
			sd.sessionId = sessionId ;
			sd.data = null ;
			
			UdpData ud = new UdpData() ;
			ud.id = this.IDCreate() ;
			ud.timeStamp = System.currentTimeMillis() ;
			ud.receiveIp = SecureConfig.ssoUdpIp ;
			ud.receivePort = SecureConfig.ssoUdpPort ;
			ud.returnIp = SecureConfig.localUdpIp ;
			ud.returnPort = SecureConfig.udpReceivePort ;
			ud.obj = sd ;
			
			send.setSendObject(ud) ;
			
			
			UdpReceiveManager receive = UdpReceiveManager.newInstance() ;
			UdpData rud = receive.getReturnData(ud.id , SecureConfig.waitReturnTimeOut) ;
			if(rud != null){
				SsoData rsd = (SsoData)rud.obj ;
				if(rsd.order.intValue() == SsoOrder.error){
					//SSO系统发现错误
				    error = (String)rsd.data ;
				    //System.out.println(error) ;///////////////////////////
				}else if(rsd.order.intValue() == SsoOrder.noLogin){
					//当前用户的客户端有cookie,但SSO中未有当前用户登录;
					//原因是用户登录后，服务重新启动，而用户的浏览器没有关闭，
				    //在且在没有关闭的页面中进行系统访问
					//但这里设置vo为null ,使请求转向登录
					vo = null ;
				}else if(rsd.order.intValue() == SsoOrder.logined){
					vo = (SessionUserVO)rsd.data ;
					//把用户信息存入当前系统会话中
					rq.getSession().setAttribute(SecureConfig.userKey , vo) ;
				}
			}
		   
		}catch(ACException e){
			e.printStackTrace() ;
			log.error(e.getMessage() , e) ;
		}finally{
			return vo ;
		}
	}
	/**
	 * 从SSO系统中得到登录后的会话用户信息
	 * @param sessionId
	 * @return
	 */
	@SuppressWarnings("finally")
	private SessionUserVO directLoginBySSO(
			String sessionId , 
			HttpServletRequest rq , 
			HttpServletResponse hrs ,
			String fixedIpUserInfo){
		SessionUserVO vo = null ;
		try{
			UdpSendManager send = UdpSendManager.singleInstance() ;
			
			SsoData sd = new SsoData() ;
			sd.order = SsoOrder.directLogin ;
			sd.sessionId = sessionId ;
			sd.data = fixedIpUserInfo ;
			
			UdpData ud = new UdpData() ;
			ud.id = this.IDCreate() ;
			ud.timeStamp = System.currentTimeMillis() ;
			ud.receiveIp = SecureConfig.ssoUdpIp ;
			ud.receivePort = SecureConfig.ssoUdpPort ;
			ud.returnIp = SecureConfig.localUdpIp ;
			ud.returnPort = SecureConfig.udpReceivePort ;
			ud.obj = sd ;
			
			send.setSendObject(ud) ;
			
			
			UdpReceiveManager receive = UdpReceiveManager.newInstance() ;
			UdpData rud = receive.getReturnData(ud.id , SecureConfig.waitReturnTimeOut) ;
			if(rud != null){
				SsoData rsd = (SsoData)rud.obj ;
				if(rsd.order.intValue() == SsoOrder.error){
					//SSO系统发现错误
				    error = (String)rsd.data ;
				    //System.out.println(error) ;///////////////////////////
				}else if(rsd.order.intValue() == SsoOrder.noLogin){
					//当前用户的客户端有cookie,但SSO中未有当前用户登录;
					//原因是用户登录后，服务重新启动，而用户的浏览器没有关闭，
				    //在且在没有关闭的页面中进行系统访问
					//但这里设置vo为null ,使请求转向登录
				    //System.out.println("当前用户的客户端有cookie,但SSO中未有当前用户登录,原因是用户登录后，服务重新启动，而用户的浏览器没有关闭,在且在没有关闭的页面中进行系统访问,但这里设置vo为null ,使请求转向登录.") ;///////////////////////////
					vo = null ;
				}else if(rsd.order.intValue() == SsoOrder.logined){
					vo = (SessionUserVO)rsd.data ;
					//把用户信息存入当前系统会话中
					rq.getSession().setAttribute(SecureConfig.userKey , vo) ;
				}
			}
		   
		}catch(ACException e){
			e.printStackTrace() ;
			log.error(e.getMessage() , e) ;
		}finally{
			return vo ;
		}
	}
	
	/**
	 * 转向SSO系统，进行登录
	 *
	 */
	private void toSsoLogin(HttpServletRequest hr, HttpServletResponse hrs)throws ServletException{
		
		String requestUrl = hr.getRequestURL().toString() ;
		String servletPath = hr.getServletPath();
		String contextPath = hr.getContextPath() ;
		String referer = null  ;
		if(contextPath != null && !contextPath.trim().equals("")){
			referer = requestUrl.substring(0 , requestUrl.indexOf(contextPath)) + contextPath + "/";
		}else if(contextPath.trim().equals("")){
			//根系统部署在ROOT目录中，形成的上下文为""
			if(requestUrl.indexOf(SecureConfig.automicSecureSsoUrlKey) > 0){
				//说明是打开登录页面，但没有及时登录，使这边的WaitLoginData被清空，再次要求登录时，就构造了这样的URL
				referer = requestUrl.substring(0 , requestUrl.indexOf(SecureConfig.automicSecureSsoUrlKey))  ;
			}else{
				referer = requestUrl ;
			}
			referer += "/";
		}else{
			referer = requestUrl ;
		}
		
		WaitLoginData wd = new WaitLoginData() ;
		wd.id = this.IDCreate() ;
		/**
		 * wd.refererUrl 一定要取 referer值，不能取requestUrl值，
		 * 虽然取了referer值，会登录成功重新定向到web系统根，而取requestUrl值时，会登录成功返回到最初访问页面
		 * 原因是：当用户转向登录页面，而没有及时登录，而等待超时，使得WaitLoginCach
		 * 中的等待数据初清空，这时用户登录成功转回到这里，系统会要求用户再去登录，而这时的
		 * requestUrl将会是用户登录成功能的URL，而不是用户最初的URL，如果这果采用了用户登录成功能的URL
		 * 那么系统会死循环登录。
		 */
		wd.refererUrl = referer;
		wd.stamp = System.currentTimeMillis() ;
		
		WaitLoginCach.putWaitLoginData(wd.id, wd) ;
		
		referer += SecureConfig.automicSecureSsoUrlKey + ".action?" + SecureConfig.waitDataIdKey + "=" + wd.id;
		String redirectUrl = null;
		/**
		 * 获取客户端ip地址。当服务器所在网络使用代理上网是，过滤机制存在bug。获取访问url的ip地址
		 * 故采用获取访问url的ip代替
		 */
		String ip = hr.getServerName();
		if(ip.equals(SecureConfig.outterIp)){//外网ip置换
			redirectUrl = SecureConfig.ssoAccess.replaceAll("\\{ip\\}", SecureConfig.outterIp) ;
		}else{//内网访问ip置换
			redirectUrl = SecureConfig.ssoAccess.replaceAll("\\{ip\\}", ip) ;
		}
		redirectUrl +=  ("?" + SSOReferer.ParameterReferer + "=" + referer);
		
		/**
		 * 判断请求框架类型，不同的请求框架类型，生成不同的后期处理
		 */
		try {
			//struts2以后缀.jQuery请求，说明是用jQuery框架无刷新请求页面
			if(servletPath.endsWith(".jQuery")){
					hrs.setContentType("text/html;charset=UTF-8");
					PrintWriter out = hrs.getWriter();
					out.print("<script language=\"JavaScript\">" +
								"window.location='" + redirectUrl + "' ;" +
								"</script>");
					out.close();
					return;
			}else{
				//重定向到登录页
				hrs.setContentType("text/html;charset=UTF-8");
				PrintWriter out = hrs.getWriter();
				out.print("<script language=\"JavaScript\">" +
							"window.top.location='" + redirectUrl + "' ;" +
							"</script>");
				out.close();
			}
		} catch (IOException e) {
			log.error("转向单点登录SSO系统时出错!", e);
		}
	}
	
	/**
	 * 刚登录成功，创建cookie并返回到最初访问URL
	 * 
	 */
	private void creatCookieAndRedirectReffer(
			HttpServletRequest hr, 
			HttpServletResponse hrs , 
			String sessionId , 
			String referer,
			SessionUserVO uvo) {
		//重定向处理
		String reUrl = referer;
		int cxtIdx = referer.substring(0,referer.length()-1).lastIndexOf("/");
		String cxt = referer.substring(cxtIdx+1,referer.length()-1);
		if(!cxt.equals(SecureConfig.defaultAcc)){
			reUrl = referer.substring(0,cxtIdx+1) + SecureConfig.defaultAcc;
			reUrl += "?" + SecureConfig.automicSubSysPara + "=" + cxt;
		}else if(SecureConfig.defaultAcc.trim().equals("")){
			reUrl = reUrl.substring(0,reUrl.length()-1);//context为""时，去掉重复的/
		}
		Cookie cookieLocal = new Cookie(SSOCookie.cookieLocal_NameKey , SSOCookie.cookieLocal_NameValue) ;
		cookieLocal.setDomain("127.0.0.1") ;
		cookieLocal.setMaxAge(SecureConfig.cookieMaxTime) ;
		cookieLocal.setPath("/");
		cookieLocal.setValue(sessionId) ;
		hrs.addCookie(cookieLocal);
		
		String ip = hr.getRemoteAddr();//在应用服务所在服务器上访问时，获取为ip为：127.0.0.1，其他内网客户端可以正常获取ip地址
		String fixedIp = (uvo== null ? null : (uvo.getFixedIP())) ;
		boolean createInstantCookie = true ;
		if(uvo != null 
				&& fixedIp!= null 
				&& !fixedIp.trim().equals("")
				&& ip.equals(fixedIp)){
			//用户有固定IP，并且当前用户访问系统所用的IP与数据库中用户实体的固定IP相同
			try{
				String s = uvo.getName() + ";" + uvo.getPassword() + ";" + uvo.getFixedIP() ;
				Crypt cry = new Crypt() ;
				byte[] key = cry.getEasyKey() ;
				byte[] en = cry.encode(s.getBytes(), key) ;
				Cookie cookie = new Cookie(SSOCookie.cookieForever_NameKey , SSOCookie.cookieForever_NameValue) ;
				if(!hr.getServerName().equals("localhost")){//localhost无法设置到cookie的domain中
					cookie.setDomain(hr.getServerName());
				}
				cookie.setMaxAge(SecureConfig.cookieMaxTimeForFixedIP) ; 
				cookie.setPath("/");
				cookie.setValue(cry.byte2hex(en)) ;
				hrs.addCookie(cookie);
				createInstantCookie = false ;	
			}catch(Exception e){
				createInstantCookie = true ;	
			}
		}
		if(createInstantCookie){
			Cookie cookie = new Cookie(SSOCookie.cookieInstant_NameKey , SSOCookie.cookieInstant_NameValue) ;
			if(!hr.getServerName().equals("localhost")){
				cookie.setDomain(hr.getServerName());
			}
			cookie.setMaxAge(SecureConfig.cookieMaxTime) ;
			cookie.setPath("/");
			cookie.setValue(sessionId) ;
			hrs.addCookie(cookie);
		}
		try{
			//登录成功，重定向到访问的子系统
			hrs.sendRedirect(reUrl);
		}catch(IOException e){
			log.error("转向原始访问页面时出错!" , e) ;
		}
	}
	/**
	 * 转向显示出错信息
	 *
	 */
	private void redirectShowError(HttpServletResponse hrs, String info) {
		info = "服务器出错，请联系管理员!" + (info == null ? "" : ("出错信息:" + info)) ;
		try {
			hrs.setContentType("text/html;charset=UTF-8");
			PrintWriter out = hrs.getWriter();
			out.print("<meta   http-equiv='Content-Type'   content='text/html;   charset=UTF-8'>");
			out.print("<body onload=\"showInfo()\"></body><script language=\"JavaScript\">function showInfo(){alert('"
							+ info + "')}</script>");
			out.close();
			return;
		} catch (Exception e) {
			log.error("显示出错信息出错！" , e);
		}
	}
	
	
	/**
	 * 刷新会话
	 * 
	 * @param sessionId
	 */
	private void freshSession(ServletRequest request){
		HttpServletRequest hr = (HttpServletRequest) request;
		Cookie[] cookies = hr.getCookies() ;//获取系统本地cookie
		if(cookies != null){
			String sessionId = null ;
			String fixedIpUserInfo = null ;
			for(int i = 0 ; i < cookies.length ; i++){
				if(cookies[i].getName().equals(SSOCookie.cookieForever_NameKey)){
					fixedIpUserInfo = cookies[i].getValue() ;
					break ;
				}
				if(cookies[i].getName().equals(SSOCookie.cookieInstant_NameKey)){
					sessionId = cookies[i].getValue() ;
					break ;
				}
				if(cookies[i].getName().equals(SSOCookie.cookieLocal_NameKey)){
					sessionId = cookies[i].getValue() ;
					break ;
				}
			}
			
			if(fixedIpUserInfo != null){//固定ip用户，获取sessionId
				String[] namePassIp = null ;
				String fixedIpUserInfoStr = null ;
				try{
					Crypt cry = new Crypt() ;
					byte[] key = cry.getEasyKey() ;
					byte[] enBin = cry.hex2Byte(fixedIpUserInfo) ;
					byte[] de = cry.decode(enBin , key) ;
					fixedIpUserInfoStr = new String(de) ;
					namePassIp = fixedIpUserInfoStr.split(";") ;
					sessionId = namePassIp[2] ;
				}catch(Exception e){
					 ;
				}
			}
			if(sessionId != null){//通过sessionId进行UDP通信，发送SSO申请session刷新
				try{
					UdpSendManager send = UdpSendManager.singleInstance() ;
					
					SsoData sd = new SsoData() ;
					sd.order = SsoOrder.freshSession ;
					sd.sessionId = sessionId ;
					sd.data = null ;
					
					UdpData ud = new UdpData() ;
					ud.id = this.IDCreate() ;
					ud.timeStamp = System.currentTimeMillis() ;
					ud.receiveIp = SecureConfig.ssoUdpIp ;
					ud.receivePort = SecureConfig.ssoUdpPort ;
					ud.obj = sd ;
					
					send.setSendObject(ud) ;
				   
				}catch(ACException e){
					log.error(e.getMessage() , e) ;
				}
			}
		}
	}
	
	/** 
	* @Title: closeSession 
	* @Description: TODO(注销sso中session信息) 
	* @return void    返回类型 
	* @throws 
	*/ 
	private void closeSession(ServletRequest request){
		HttpServletRequest hr = (HttpServletRequest) request;
		Cookie[] cookies = hr.getCookies() ;//获取系统本地cookie
		if(cookies != null){
			String sessionId = null ;
			String fixedIpUserInfo = null ;
			for(int i = 0 ; i < cookies.length ; i++){
				if(cookies[i].getName().equals(SSOCookie.cookieForever_NameKey)){
					fixedIpUserInfo = cookies[i].getValue() ;
					break ;
				}
				if(cookies[i].getName().equals(SSOCookie.cookieInstant_NameKey)){
					sessionId = cookies[i].getValue() ;
					break ;
				}
				if(cookies[i].getName().equals(SSOCookie.cookieLocal_NameKey)){
					sessionId = cookies[i].getValue() ;
					break ;
				}
			}
			
			if(fixedIpUserInfo != null){//固定ip用户，获取sessionId
				String[] namePassIp = null ;
				String fixedIpUserInfoStr = null ;
				try{
					Crypt cry = new Crypt() ;
					byte[] key = cry.getEasyKey() ;
					byte[] enBin = cry.hex2Byte(fixedIpUserInfo) ;
					byte[] de = cry.decode(enBin , key) ;
					fixedIpUserInfoStr = new String(de) ;
					namePassIp = fixedIpUserInfoStr.split(";") ;
					sessionId = namePassIp[2] ;
				}catch(Exception e){
					 ;
				}
			}
			if(sessionId != null){//通过sessionId进行UDP通信，发送SSO申请session刷新
				try{
					UdpSendManager send = UdpSendManager.singleInstance() ;
					
					SsoData sd = new SsoData() ;
					sd.order = SsoOrder.logout ;
					sd.sessionId = sessionId ;
					sd.data = null ;
					
					UdpData ud = new UdpData() ;
					ud.id = this.IDCreate() ;
					ud.timeStamp = System.currentTimeMillis() ;
					ud.receiveIp = SecureConfig.ssoUdpIp ;
					ud.receivePort = SecureConfig.ssoUdpPort ;
					ud.obj = sd ;
					
					send.setSendObject(ud) ;
				   
				}catch(ACException e){
					log.error(e.getMessage() , e) ;
				}
			}
		}
	}
	 	
	/**
	 * 产生ID
	 * @return
	 */
	private synchronized String IDCreate(){
		return "" + System.nanoTime() ;
	}

}
