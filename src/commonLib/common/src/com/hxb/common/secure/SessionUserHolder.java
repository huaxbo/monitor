package com.hxb.common.secure;

import com.hxb.common.sso.session.SessionUserInterface;

public class SessionUserHolder {
	//在当前线程是活动的并且ThreadLocal对象是可访问的时候，
	//该线程就持有一个到该线程局部变量副本的隐含引用，
	//当该线程运行结束后，该线程拥有的所以线程局部变量的副本都将失效，
	//并等待垃圾收集器收集。

	private static ThreadLocal<SessionUserInterface> holder = new ThreadLocal<SessionUserInterface>();

	/**
	 * 设置用户信息
	 * @param ud
	 */
	public static void setUserInfo(SessionUserInterface ud) {
		holder.set(ud);
	}

	/**
	 * 获取用户信息
	 * @return
	 */
	public static SessionUserInterface getUserInfo() {
		return holder.get();
	}

	/**
	 * 清除用户信息
	 */
	public static void removeUserInfo(){
		holder.remove();
	}
}
