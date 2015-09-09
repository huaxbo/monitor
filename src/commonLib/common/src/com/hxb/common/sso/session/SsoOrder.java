package com.hxb.common.sso.session;

/**
 * ע: ���������1��ͷ
 *     ���������2��ͷ
 *     ���л����ж���������9��ͷ
 * @author Administrator
 *
 */
public class SsoOrder {
	/**
	 * ��ϵͳ��鷢�����й̶�IP���û�������ֱ�ӵ�¼
	 * ����������Ҫ��������cookie�е�"userName;password;ip"
	 * ��������Ҫ��SSO���е�¼����¼�ɹ��򷵻��û�����������ɹ������ؿ�
	 */
	public static final Integer directLogin = 1000 ;
	/**
	 * ��ϵͳ��鵱ǰ����ͻ����Ƿ��ڵ�¼״̬
	 * ����������Ҫ��������cookie�е�sessionIdֵ
	 * ��������Ҫ������Ѿ���¼���򷵻��û��������δ��¼�����ؿ�
	 */
	public static final Integer checkLogin = 1001 ;
	
	/**
	 * ˢ�»Ự
	 * ����������Ҫ��������cookie�е�sessionIdֵ
	 * ��������Ҫ�󣺽�ֹ��������
	 */
	public static final Integer freshSession = 1002 ;
	
	/**
	 * ��ǰ����ͻ����Ѿ����ڵ�¼�ɹ�״̬
	 * ����������Ҫ���û��Ự�е�����
	 * ��������Ҫ�󣺽�ֹ��������
	 */
	public static final Integer logined = 2001 ;
	/**
	 * ��ǰ����ͻ���δ��¼��Ự����
	 * ����������Ҫ������Ϊ��
	 * ��������Ҫ�󣺽�ֹ��������
	 */
	public static final Integer noLogin = 2002 ;
	/**
	 * �˳���¼
	 * ����������Ҫ��������cookie�е�sessionIdֵ
	 * ��������Ҫ�󣺽�ֹ��������
	 */
	public static final Integer logout = 2003 ;
	
	
	/**
	 * ������
	 * ����������Ҫ������Ϊ������Ϣ
	 * ��������Ҫ�󣺽�ֹ��������
	 */
	public static final Integer error = 9001 ;
	
}
