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
	
	//ΪDWR���Ӧ�ã���ʶ�Ự����
	public static int  SESSIONTIMEOUT = 1000 ;

 	//�ȴ���¼���ص����ʱ��
	private static WaitLoginCach waitLoginCach ;

	/////////////////////////
	//�Ǿ�̬����
	private String error ;
	
	
	public void destroy() {
	}
	/**
	 * ��������ʼ��
	 */
	public void init(FilterConfig config) throws ServletException {
		//�����ȴ���¼�ɹ����ݻ����߳�
		if(!SecureConfig.debug){
			WaitLoginCach.setMaxWaitLoginTime(SecureConfig.waitLoginTimeOut) ;
			waitLoginCach = new WaitLoginCach() ;
			waitLoginCach.start() ;
			
			this.initUdpSocket(0) ;
			
			System.out.println("�����¼�ͻ���:��������(UDP)�˿ں�:" + SecureConfig.udpSendPort) ;
			System.out.println("�����¼�ͻ���:��������(UDP)�˿ں�:" + SecureConfig.udpReceivePort) ;
			
			
			System.out.println("�����¼����ͻ����Ѿ�����");
		}
	}
	
	/**
	 * ���������¼��UDP��������
	 * @param count
	 */
	private void initUdpSocket(int count)throws ServletException{
		try{
			//������������UDP socket
			UdpSendManager.initFirst(SecureConfig.udpSendPort, SecureConfig.udpSendTimeOut) ;
			UdpSendManager sender = UdpSendManager.singleInstance() ;
			sender.start() ;
			//������������UDP socket
			UdpReceiveManager.initAndStartFirst(
					SecureConfig.udpReceivePort, 
					SecureConfig.udpReceiveTimeOut , 
					SecureConfig.udpReceiveDataLen) ;

		}catch(SocketException e){
			System.out.println("�����¼�ͻ��˵�" + (count + 1) + "���Դ���UDP(�˿ں�:" + SecureConfig.udpSendPort + "ʧ�ܣ���") ;
			if(count > SecureConfig.maxTryTimes){
				//���Դ��������������
				e.printStackTrace() ;
				throw new ServletException("��ȫ������:�����շ�������UDP �����¼ͨ�ŷ���ʧ�ܣ�ԭ��" + e.getMessage()  , e) ;
			}else{
				count ++ ;
				SecureConfig.createUdpPort() ;
				this.initUdpSocket(count) ;
			}
		}catch(ACException e){
			e.printStackTrace() ;
			throw new ServletException("��ȫ������:������������UDP �����¼����ʧ�ܣ�" , e) ;
		}
	}

	/**
	 * ����
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	
		if(!SecureConfig.debug){//�ǵ���ģʽ-У��
			log.info("����SuecureFilterSSO��������") ;

			HttpServletRequest hr = (HttpServletRequest) request;
			HttpServletResponse hrs = (HttpServletResponse) response;
			String path = hr.getServletPath();
			if (this.isNeedFilter(path)) {//��ҪУ��url����
				log.info("��Ҫ���˵�ǰ·����" + path) ;
				if (this.toFilter(hr, hrs, path)) {//urlȨ����֤����
					chain.doFilter(request, response);
				}
			} else {//����У��url����
				log.info("����Ҫ���˵�ǰ·����" + path) ;
				chain.doFilter(request, response);
				//ˢ��SSO�еĻỰ
				this.freshSession(request) ;
			}
		}else{//����ģʽ-����
			chain.doFilter(request, response);
		}
	}

	/**
	 * �ж��Ƿ���Ҫ��֤(�Ự��֤��Ȩ����֤)
	 * @param path
	 * @return
	 */
	private boolean isNeedFilter(String path) {
		String conUrl = null ;
		for (int i = 0; SecureConfig.releaseUrl != null && i < SecureConfig.releaseUrl.length; i++) {
			
			conUrl = SecureConfig.releaseUrl[i] ;
			
			if(conUrl.startsWith("*") || conUrl.endsWith("*")){
				conUrl = conUrl.replaceAll("\\*", "") ;
				if(!conUrl.equals(".js") && path.contains(conUrl)){//��js�ļ��Ұ���conUrl�ַ���url���������
					return false ;
				}
			}else{
				if (path.endsWith(conUrl)) {//��conUrl��β��url���������
					return false;
				}
			}
		}

		for (int i = 0; SecureConfig.controlUrl != null && i < SecureConfig.controlUrl.length; i++) {//����controlUrl�е�url���й���
			if (path.endsWith(SecureConfig.controlUrl[i])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * ������֤
	 * @param hr
	 * @param hrs
	 * @param path
	 */
	private boolean toFilter(HttpServletRequest hr, HttpServletResponse hrs,
			String path) throws IOException, ServletException {
		boolean nextFilter = true;
		//�ж��Ƿ�Ϊע������
		if(hr.getServletPath() != null && hr.getServletPath().equals("/glo/logoutAction.action")){
			closeSession(hr);//ע��
		}	
		
		//���ȳ��ԴӻỰ�еõ��û���Ϣ
		//����õ��ˣ�˵���û��Ѿ��ɹ���¼ϵͳ��
		SessionUserVO uvo = (SessionUserVO) hr.getSession().getAttribute(SecureConfig.userKey);
	    if(uvo != null){
			//�û���¼�����������
			//���û���Ϣ�����̼߳��İ�ȫ�������У��Ա�����Ȩ����֤
        	if(SecureConfig.checkPower){
        		SessionUserHolder.setUserInfo(uvo);
        	}
			return nextFilter ;
		}
	    
		hr.setAttribute("serverName", hr.getServerName());//��ִ����ip������
		hr.setAttribute("contextName", hr.getContextPath().equals("") ? "/" : hr.getContextPath());//��ִ��������������
		
		String requestUrl = hr.getRequestURL().toString() ;
		/////////////////////////////////////
		//���ˣ��жϵ�ǰ�����Ƿ�Ϊ��¼����
		//����ǳɹ���¼���أ����гɹ���¼���ص���ز���
		if(requestUrl.indexOf(SecureConfig.automicSecureSsoUrlKey) > 0){
			//��¼�ɹ������ش�����cookie
			log.info("��¼�ɹ����ء�") ;
			String waitDataId = hr.getParameter(SecureConfig.waitDataIdKey) ;
			String sessionId = hr.getParameter(SSOReferer.sessionId) ;
			if(waitDataId == null 
					|| sessionId == null  
					|| waitDataId.trim().equals("") 
					|| sessionId.trim().equals("") ){
				//û�в�����������Ϊ���õ�automicSecureSsoUrlKey����·�����е�¼
				log.info("��¼�ɹ����أ���URL����waitDataId��sessionId�����µ�¼��");
				this.toSsoLogin(hr , hrs) ;
				return nextFilter = false ;
			}else{
				WaitLoginData wd = WaitLoginCach.getWaitLoginData(waitDataId) ;
				if(wd == null){
					//˵���ȴ���¼��ʱ��,�����û���¼�ɹ����ڰ�������Ļ��˰�ť����¼ҳ�棬
					//�����ٴε�¼����ʱ��ӦwaitDataId�������Ѿ��ڵ�һ�ε�¼ʱ�����.
					//System.out.println("��¼�ɹ����أ����ȴ������Ѿ���գ����µ�¼��") ;
					log.info("��¼�ɹ����أ����ȴ������Ѿ���գ����µ�¼��") ;
					this.toSsoLogin(hr, hrs) ;
					return nextFilter = false ;
				}else{
					//��¼�ɹ���ת��������ʵ�ҳ��URL
					log.info("��¼�ɹ����أ�����cookie��ת��ԭ����URL��") ;
					//��SSO�õ��û���Ϣ
					uvo = this.getUserInfoFromSSO(sessionId , hr ,  hrs) ;
					error = null ;
					this.creatCookieAndRedirectReffer(hr, hrs , sessionId ,wd.refererUrl , uvo) ; 
					return nextFilter = false ;
				}
			}
		}
		
		//////////////////////////////////////////////
		//���ˣ��Ự��û���û���Ϣ������Ҳ���ǵ�¼����,
		//�õ�cookie����Ϣ
		Cookie[] cookies = hr.getCookies() ;
		if(cookies == null){
			//˵���û�����û��cookie����ʾ�û��ĵ�ǰ��δ��¼��ת��SSO��¼
			log.info("��ǰ������cookie��ת���¼��") ;
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
			//˵��������cookie�еõ��ỰID 
			//��ʾ�û��ڱ�ϵͳ��δ��¼��ת��SSO��¼
			log.info("��ǰ���ʵ�cookie�в���ỰsessionIdҲ����������IP���û���Ϣ�����µ�¼��") ;
			this.toSsoLogin(hr, hrs) ;
			return nextFilter = false ;
		}
		
		
		
		//////////////////////////////////////////
		//���ˣ��û������д���cookie������cookie��Ч
		//cookie�д����й̶�IP���û���Ϣ
		//cookie���������ڵ�(ʵ��Ϊ10�꣬�ο���������cookie�Ĳ���)
		if(fixedIpUserInfo != null ){
			//��ǰϵͳ�Ự��û���û���Ϣ����cookie��Ч��
			//���ܴ��ڵ����������cookie���û���δ��¼ϵͳ ������ �û��Ѿ��ɹ���¼������ϵͳ��
			//���ȳ��Դ�SSOϵͳ�еõ��û���Ϣ
			//�����SSO�еõ����û���Ϣ��˵���û��Ѿ���¼��������ϵͳ�ˣ��ڱ���ϵͳ�Ự�л�û���û���Ϣ����ô���û���Ϣ���ڱ���ϵͳ�Ự��
			//�����SSO�в��ܵõ��û���Ϣ��˵���û���������ϵͳ�ж�δ��¼�����û�cookie�еõ��û���Ϣ��ͨ��SSOֱ�ӵ�¼��
			//������ܻ����error��Ϣ
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
						//cookie�ܵ��ƻ�
						log.info("��ǰ���ʵ�cookie�д��ڹ̶�IP�û�����Ϣ����cookie�ܵ��ƻ������µ�¼��") ;
						this.toSsoLogin(hr, hrs) ;
						return nextFilter = false ;
					}
					if(namePassIp == null || namePassIp.length != 3){
						//cookie�ܵ��ƻ�
						log.info("��ǰ���ʵ�cookie�д��ڹ̶�IP�û�����Ϣ�����û�����Ϣ�ܵ��ƻ������µ�¼��") ;
						this.toSsoLogin(hr, hrs) ;
						return nextFilter = false ;
					}
					if(!ip.equals(namePassIp[2])){
						//͵��cookie���������û�ʵ��IP�Ѿ��仯��
						log.info("��ǰ���ʵ�cookie�д��ڹ̶�IP�û�����Ϣ������ǰ����IP��cookie�е��û��̶�IP����ͬ�����µ�¼��") ;
						this.toSsoLogin(hr, hrs) ;
						return nextFilter = false ;
					}else{
						//�û���δ��¼ϵͳ��
						//�û�ֱ�ӵ�¼
						uvo = this.directLoginBySSO(namePassIp[2] , hr , hrs , fixedIpUserInfoStr) ;
						if(error == null){
							if(uvo == null){
								//��SSOϵͳ��δ�õ���Ϣ��
								//����ԭ����SSOϵͳ�� ,��ǰ�û���¼�Ѿ��Ự����,�����������˷�������
								//����ԭ��ͷ����˴���
								log.info("��ǰ���ʴ���cookie������SSO�������ò�����¼�û��������µ�¼��") ;
								this.toSsoLogin(hr, hrs) ;
								nextFilter = false;
							}else{
								//��sso�еõ����û���Ϣ
								nextFilter = true;
							}
						}
					}
				}else{
					//��sso�еõ����û���Ϣ
					nextFilter = true;
				}
			}
		}
		
		////////////////////////////////////////////
		//���ˣ��ǹ̶�IP�û����û������д���cookie�����cookie�Ǽ�ʱcookie��
		//cookie�д��ڻỰID(sessionId)��˵���û��ͻ���������Ч��¼״̬
		if(sessionId != null && fixedIpUserInfo == null){
			//��ǰϵͳ�Ự��û���û���Ϣ����cookie��Ч��ԭ�򣺻Ự���� ������ �û��Ѿ��ɹ���¼������ϵͳ��
			//���´�SSOϵͳ�еõ��û���Ϣ
			//������ܻ����error��Ϣ
			uvo = this.getUserInfoFromSSO(sessionId , hr , hrs) ;
			if(error == null){
				if(uvo == null){
					//��SSOϵͳ��δ�õ���Ϣ��
					//����ԭ����SSOϵͳ�� ,��ǰ�û���¼�Ѿ��Ự����,�����������˷�������
					//����ԭ��ͷ����˴���
					log.info("��ǰ���ʴ���cookie������SSO�������ò�����¼�û��������µ�¼��") ;
					this.toSsoLogin(hr, hrs) ;
					nextFilter = false;
				}else{
					//��sso�еõ����û���Ϣ
					nextFilter = true;
				}
			}
		}
		
		
		///////////////////////
		//��������в�������
		if(error != null){
			this.redirectShowError(hrs , error) ;
			error = null ;
			nextFilter = false;
		}

		if(!nextFilter){
			return nextFilter ;
		}
		
		////////////////////////////
		//���û���Ϣ�����̼߳��İ�ȫ�������У��Ա�����Ȩ����֤
        if(uvo != null){
        	if(SecureConfig.checkPower){
        		SessionUserHolder.setUserInfo(uvo);
        	}
			nextFilter = true;
		}
		return nextFilter;
	}
	
	/**
	 * ��SSOϵͳ�еõ���¼��ĻỰ�û���Ϣ
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
					//SSOϵͳ���ִ���
				    error = (String)rsd.data ;
				    //System.out.println(error) ;///////////////////////////
				}else if(rsd.order.intValue() == SsoOrder.noLogin){
					//��ǰ�û��Ŀͻ�����cookie,��SSO��δ�е�ǰ�û���¼;
					//ԭ�����û���¼�󣬷����������������û��������û�йرգ�
				    //������û�йرյ�ҳ���н���ϵͳ����
					//����������voΪnull ,ʹ����ת���¼
					vo = null ;
				}else if(rsd.order.intValue() == SsoOrder.logined){
					vo = (SessionUserVO)rsd.data ;
					//���û���Ϣ���뵱ǰϵͳ�Ự��
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
	 * ��SSOϵͳ�еõ���¼��ĻỰ�û���Ϣ
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
					//SSOϵͳ���ִ���
				    error = (String)rsd.data ;
				    //System.out.println(error) ;///////////////////////////
				}else if(rsd.order.intValue() == SsoOrder.noLogin){
					//��ǰ�û��Ŀͻ�����cookie,��SSO��δ�е�ǰ�û���¼;
					//ԭ�����û���¼�󣬷����������������û��������û�йرգ�
				    //������û�йرյ�ҳ���н���ϵͳ����
					//����������voΪnull ,ʹ����ת���¼
				    //System.out.println("��ǰ�û��Ŀͻ�����cookie,��SSO��δ�е�ǰ�û���¼,ԭ�����û���¼�󣬷����������������û��������û�йر�,������û�йرյ�ҳ���н���ϵͳ����,����������voΪnull ,ʹ����ת���¼.") ;///////////////////////////
					vo = null ;
				}else if(rsd.order.intValue() == SsoOrder.logined){
					vo = (SessionUserVO)rsd.data ;
					//���û���Ϣ���뵱ǰϵͳ�Ự��
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
	 * ת��SSOϵͳ�����е�¼
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
			//��ϵͳ������ROOTĿ¼�У��γɵ�������Ϊ""
			if(requestUrl.indexOf(SecureConfig.automicSecureSsoUrlKey) > 0){
				//˵���Ǵ򿪵�¼ҳ�棬��û�м�ʱ��¼��ʹ��ߵ�WaitLoginData����գ��ٴ�Ҫ���¼ʱ���͹�����������URL
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
		 * wd.refererUrl һ��Ҫȡ refererֵ������ȡrequestUrlֵ��
		 * ��Ȼȡ��refererֵ�����¼�ɹ����¶���webϵͳ������ȡrequestUrlֵʱ�����¼�ɹ����ص��������ҳ��
		 * ԭ���ǣ����û�ת���¼ҳ�棬��û�м�ʱ��¼�����ȴ���ʱ��ʹ��WaitLoginCach
		 * �еĵȴ����ݳ���գ���ʱ�û���¼�ɹ�ת�ص����ϵͳ��Ҫ���û���ȥ��¼������ʱ��
		 * requestUrl�������û���¼�ɹ��ܵ�URL���������û������URL���������������û���¼�ɹ��ܵ�URL
		 * ��ôϵͳ����ѭ����¼��
		 */
		wd.refererUrl = referer;
		wd.stamp = System.currentTimeMillis() ;
		
		WaitLoginCach.putWaitLoginData(wd.id, wd) ;
		
		referer += SecureConfig.automicSecureSsoUrlKey + ".action?" + SecureConfig.waitDataIdKey + "=" + wd.id;
		String redirectUrl = null;
		/**
		 * ��ȡ�ͻ���ip��ַ������������������ʹ�ô��������ǣ����˻��ƴ���bug����ȡ����url��ip��ַ
		 * �ʲ��û�ȡ����url��ip����
		 */
		String ip = hr.getServerName();
		if(ip.equals(SecureConfig.outterIp)){//����ip�û�
			redirectUrl = SecureConfig.ssoAccess.replaceAll("\\{ip\\}", SecureConfig.outterIp) ;
		}else{//��������ip�û�
			redirectUrl = SecureConfig.ssoAccess.replaceAll("\\{ip\\}", ip) ;
		}
		redirectUrl +=  ("?" + SSOReferer.ParameterReferer + "=" + referer);
		
		/**
		 * �ж����������ͣ���ͬ�����������ͣ����ɲ�ͬ�ĺ��ڴ���
		 */
		try {
			//struts2�Ժ�׺.jQuery����˵������jQuery�����ˢ������ҳ��
			if(servletPath.endsWith(".jQuery")){
					hrs.setContentType("text/html;charset=UTF-8");
					PrintWriter out = hrs.getWriter();
					out.print("<script language=\"JavaScript\">" +
								"window.location='" + redirectUrl + "' ;" +
								"</script>");
					out.close();
					return;
			}else{
				//�ض��򵽵�¼ҳ
				hrs.setContentType("text/html;charset=UTF-8");
				PrintWriter out = hrs.getWriter();
				out.print("<script language=\"JavaScript\">" +
							"window.top.location='" + redirectUrl + "' ;" +
							"</script>");
				out.close();
			}
		} catch (IOException e) {
			log.error("ת�򵥵��¼SSOϵͳʱ����!", e);
		}
	}
	
	/**
	 * �յ�¼�ɹ�������cookie�����ص��������URL
	 * 
	 */
	private void creatCookieAndRedirectReffer(
			HttpServletRequest hr, 
			HttpServletResponse hrs , 
			String sessionId , 
			String referer,
			SessionUserVO uvo) {
		//�ض�����
		String reUrl = referer;
		int cxtIdx = referer.substring(0,referer.length()-1).lastIndexOf("/");
		String cxt = referer.substring(cxtIdx+1,referer.length()-1);
		if(!cxt.equals(SecureConfig.defaultAcc)){
			reUrl = referer.substring(0,cxtIdx+1) + SecureConfig.defaultAcc;
			reUrl += "?" + SecureConfig.automicSubSysPara + "=" + cxt;
		}else if(SecureConfig.defaultAcc.trim().equals("")){
			reUrl = reUrl.substring(0,reUrl.length()-1);//contextΪ""ʱ��ȥ���ظ���/
		}
		Cookie cookieLocal = new Cookie(SSOCookie.cookieLocal_NameKey , SSOCookie.cookieLocal_NameValue) ;
		cookieLocal.setDomain("127.0.0.1") ;
		cookieLocal.setMaxAge(SecureConfig.cookieMaxTime) ;
		cookieLocal.setPath("/");
		cookieLocal.setValue(sessionId) ;
		hrs.addCookie(cookieLocal);
		
		String ip = hr.getRemoteAddr();//��Ӧ�÷������ڷ������Ϸ���ʱ����ȡΪipΪ��127.0.0.1�����������ͻ��˿���������ȡip��ַ
		String fixedIp = (uvo== null ? null : (uvo.getFixedIP())) ;
		boolean createInstantCookie = true ;
		if(uvo != null 
				&& fixedIp!= null 
				&& !fixedIp.trim().equals("")
				&& ip.equals(fixedIp)){
			//�û��й̶�IP�����ҵ�ǰ�û�����ϵͳ���õ�IP�����ݿ����û�ʵ��Ĺ̶�IP��ͬ
			try{
				String s = uvo.getName() + ";" + uvo.getPassword() + ";" + uvo.getFixedIP() ;
				Crypt cry = new Crypt() ;
				byte[] key = cry.getEasyKey() ;
				byte[] en = cry.encode(s.getBytes(), key) ;
				Cookie cookie = new Cookie(SSOCookie.cookieForever_NameKey , SSOCookie.cookieForever_NameValue) ;
				if(!hr.getServerName().equals("localhost")){//localhost�޷����õ�cookie��domain��
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
			//��¼�ɹ����ض��򵽷��ʵ���ϵͳ
			hrs.sendRedirect(reUrl);
		}catch(IOException e){
			log.error("ת��ԭʼ����ҳ��ʱ����!" , e) ;
		}
	}
	/**
	 * ת����ʾ������Ϣ
	 *
	 */
	private void redirectShowError(HttpServletResponse hrs, String info) {
		info = "��������������ϵ����Ա!" + (info == null ? "" : ("������Ϣ:" + info)) ;
		try {
			hrs.setContentType("text/html;charset=UTF-8");
			PrintWriter out = hrs.getWriter();
			out.print("<meta   http-equiv='Content-Type'   content='text/html;   charset=UTF-8'>");
			out.print("<body onload=\"showInfo()\"></body><script language=\"JavaScript\">function showInfo(){alert('"
							+ info + "')}</script>");
			out.close();
			return;
		} catch (Exception e) {
			log.error("��ʾ������Ϣ����" , e);
		}
	}
	
	
	/**
	 * ˢ�»Ự
	 * 
	 * @param sessionId
	 */
	private void freshSession(ServletRequest request){
		HttpServletRequest hr = (HttpServletRequest) request;
		Cookie[] cookies = hr.getCookies() ;//��ȡϵͳ����cookie
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
			
			if(fixedIpUserInfo != null){//�̶�ip�û�����ȡsessionId
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
			if(sessionId != null){//ͨ��sessionId����UDPͨ�ţ�����SSO����sessionˢ��
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
	* @Description: TODO(ע��sso��session��Ϣ) 
	* @return void    �������� 
	* @throws 
	*/ 
	private void closeSession(ServletRequest request){
		HttpServletRequest hr = (HttpServletRequest) request;
		Cookie[] cookies = hr.getCookies() ;//��ȡϵͳ����cookie
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
			
			if(fixedIpUserInfo != null){//�̶�ip�û�����ȡsessionId
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
			if(sessionId != null){//ͨ��sessionId����UDPͨ�ţ�����SSO����sessionˢ��
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
	 * ����ID
	 * @return
	 */
	private synchronized String IDCreate(){
		return "" + System.nanoTime() ;
	}

}
