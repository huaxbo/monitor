package com.hxb.common.secure;

import com.hxb.common.sso.session.SessionUserInterface;

public class SessionUserHolder {
	//�ڵ�ǰ�߳��ǻ�Ĳ���ThreadLocal�����ǿɷ��ʵ�ʱ��
	//���߳̾ͳ���һ�������ֲ߳̾������������������ã�
	//�����߳����н����󣬸��߳�ӵ�е������ֲ߳̾������ĸ�������ʧЧ��
	//���ȴ������ռ����ռ���

	private static ThreadLocal<SessionUserInterface> holder = new ThreadLocal<SessionUserInterface>();

	/**
	 * �����û���Ϣ
	 * @param ud
	 */
	public static void setUserInfo(SessionUserInterface ud) {
		holder.set(ud);
	}

	/**
	 * ��ȡ�û���Ϣ
	 * @return
	 */
	public static SessionUserInterface getUserInfo() {
		return holder.get();
	}

	/**
	 * ����û���Ϣ
	 */
	public static void removeUserInfo(){
		holder.remove();
	}
}
