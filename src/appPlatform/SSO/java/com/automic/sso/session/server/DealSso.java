package com.automic.sso.session.server;

import org.apache.log4j.*;
import com.automic.sso.session.session.SessionManager;
import com.automic.sso.web.login.LoginSSODirect;
import com.automic.common.sso.session.* ;

public class DealSso {

	private static Logger log = Logger.getLogger(DealSso.class.getName() ) ;
	
	/**
	 * 处理请求数据
	 * @param o
	 * @return
	 */
	public Object dealReceiveData(Object o){
		SsoData d = null ;
		SsoData rd = null ;
		if(o == null){
			log.error("出错，没有得到请求数据(SsoData对象)" );
		}else
		if(!(o instanceof SsoData)){
			log.error("出错，请求数据类型不正确(SsoData对象)" ) ;
		}else{
			d = (SsoData)o ;
			if(d.order == null){
				log.error("出错，请求数据中命令为空！") ;
			}else{
				SessionManager sm = new SessionManager() ;
				if(d.order.intValue() == SsoOrder.checkLogin.intValue()){
					//验证是否登录
					rd = new SsoData() ;
					if(d.sessionId == null){
						rd.order = SsoOrder.error ;
						rd.data = "出错，检查用户登录请求数据中会话ID为空！" ;
					}else{
						//检查登录，如果已经登录，返回会话中的用户数据
						//反之则返回空
						rd.data = sm.checkLogin(d.sessionId) ;
						if(rd.data == null){
							rd.order = SsoOrder.noLogin ;
						}else{
							rd.order = SsoOrder.logined ;
						}
					}
				}else if(d.order.intValue() == SsoOrder.directLogin.intValue()){
					//直接登录
					rd = new SsoData() ;
					if(d.sessionId == null){
						rd.order = SsoOrder.error ;
						rd.data = "出错，直接登录请求数据中会话ID为空！" ;
					}else{
						//直接登录，如果已经登录，返回会话中的用户数据
						//反之则返回空
						rd.data = this.directLogin(d.sessionId , (String)d.data) ;
						if(rd.data == null){
							rd.order = SsoOrder.noLogin ;
						}else{
							rd.order = SsoOrder.logined ;
						}
					}
				}else if(d.order.intValue() == SsoOrder.freshSession.intValue()){
					//刷新会话
					if(d.sessionId == null){
						log.error("出错，检查用户登录请求数据中会话ID为空！") ;
					}else{
						//用户访问了系统，那么刷新会话，减小发呆时间，防止会话过时
						sm.freshSession(d.sessionId) ;
					}
				}else if(d.order.intValue() == SsoOrder.logout.intValue()){
					//退出登录
					if(d.sessionId == null){
						log.error("出错，检查用户退出登录请求数据中会话ID为空！") ;
					}else{
						//退出登录
						sm.logout(d.sessionId) ;
					}
				}else{
					log.error("出错，请求数据中的命令不能识别！命令是:" + d.order.intValue()) ;
				}
			}
		}
		
		return rd ;
	}
	/**
	 * 存入会话数据
	 * @param sessionId
	 * @param se
	 */
	public void logined(Object o , String sessionId){
		SessionManager sm = new SessionManager() ;
		sm.logined(o, sessionId) ;
	}
	
	/**
	 * 直接登录，如果已经登录，返回会话中的用户数据
	 * 反之则返回空
	 * @param sessionId
	 * @return
	 */
	private Object directLogin(String sessionId , String userInfo){
		LoginSSODirect loginSSODirect = new LoginSSODirect() ;
		return loginSSODirect.directLogin(sessionId, userInfo) ;
	}
		
}
