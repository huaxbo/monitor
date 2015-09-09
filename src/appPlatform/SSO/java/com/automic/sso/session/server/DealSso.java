package com.automic.sso.session.server;

import org.apache.log4j.*;
import com.automic.sso.session.session.SessionManager;
import com.automic.sso.web.login.LoginSSODirect;
import com.automic.common.sso.session.* ;

public class DealSso {

	private static Logger log = Logger.getLogger(DealSso.class.getName() ) ;
	
	/**
	 * ������������
	 * @param o
	 * @return
	 */
	public Object dealReceiveData(Object o){
		SsoData d = null ;
		SsoData rd = null ;
		if(o == null){
			log.error("����û�еõ���������(SsoData����)" );
		}else
		if(!(o instanceof SsoData)){
			log.error("���������������Ͳ���ȷ(SsoData����)" ) ;
		}else{
			d = (SsoData)o ;
			if(d.order == null){
				log.error("������������������Ϊ�գ�") ;
			}else{
				SessionManager sm = new SessionManager() ;
				if(d.order.intValue() == SsoOrder.checkLogin.intValue()){
					//��֤�Ƿ��¼
					rd = new SsoData() ;
					if(d.sessionId == null){
						rd.order = SsoOrder.error ;
						rd.data = "��������û���¼���������лỰIDΪ�գ�" ;
					}else{
						//����¼������Ѿ���¼�����ػỰ�е��û�����
						//��֮�򷵻ؿ�
						rd.data = sm.checkLogin(d.sessionId) ;
						if(rd.data == null){
							rd.order = SsoOrder.noLogin ;
						}else{
							rd.order = SsoOrder.logined ;
						}
					}
				}else if(d.order.intValue() == SsoOrder.directLogin.intValue()){
					//ֱ�ӵ�¼
					rd = new SsoData() ;
					if(d.sessionId == null){
						rd.order = SsoOrder.error ;
						rd.data = "����ֱ�ӵ�¼���������лỰIDΪ�գ�" ;
					}else{
						//ֱ�ӵ�¼������Ѿ���¼�����ػỰ�е��û�����
						//��֮�򷵻ؿ�
						rd.data = this.directLogin(d.sessionId , (String)d.data) ;
						if(rd.data == null){
							rd.order = SsoOrder.noLogin ;
						}else{
							rd.order = SsoOrder.logined ;
						}
					}
				}else if(d.order.intValue() == SsoOrder.freshSession.intValue()){
					//ˢ�»Ự
					if(d.sessionId == null){
						log.error("��������û���¼���������лỰIDΪ�գ�") ;
					}else{
						//�û�������ϵͳ����ôˢ�»Ự����С����ʱ�䣬��ֹ�Ự��ʱ
						sm.freshSession(d.sessionId) ;
					}
				}else if(d.order.intValue() == SsoOrder.logout.intValue()){
					//�˳���¼
					if(d.sessionId == null){
						log.error("��������û��˳���¼���������лỰIDΪ�գ�") ;
					}else{
						//�˳���¼
						sm.logout(d.sessionId) ;
					}
				}else{
					log.error("�������������е������ʶ��������:" + d.order.intValue()) ;
				}
			}
		}
		
		return rd ;
	}
	/**
	 * ����Ự����
	 * @param sessionId
	 * @param se
	 */
	public void logined(Object o , String sessionId){
		SessionManager sm = new SessionManager() ;
		sm.logined(o, sessionId) ;
	}
	
	/**
	 * ֱ�ӵ�¼������Ѿ���¼�����ػỰ�е��û�����
	 * ��֮�򷵻ؿ�
	 * @param sessionId
	 * @return
	 */
	private Object directLogin(String sessionId , String userInfo){
		LoginSSODirect loginSSODirect = new LoginSSODirect() ;
		return loginSSODirect.directLogin(sessionId, userInfo) ;
	}
		
}
