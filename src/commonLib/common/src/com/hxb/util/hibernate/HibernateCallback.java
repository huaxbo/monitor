package com.hxb.util.hibernate ;

import java.sql.SQLException;
import java.util.Hashtable;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public abstract class HibernateCallback implements
		org.springframework.orm.hibernate3.HibernateCallback {
	/**
	 * ²ÎÊý
	 */
	protected Object[] params;
	protected Hashtable<String , Object> paramMap;
	@SuppressWarnings("unchecked")
	protected HibernateBaseDao dao ;

	public HibernateCallback() {
	}
	@SuppressWarnings("unchecked")
	public HibernateCallback(HibernateBaseDao dao) {
		this.dao = dao ;
	}
	public HibernateCallback(Object[] params) {
		this.params = params;
	}
	public HibernateCallback(Hashtable<String , Object> paramMap) {
		this.paramMap = paramMap;
	}

	public abstract Object doInHibernate(Session s) throws HibernateException,
			SQLException;

}
