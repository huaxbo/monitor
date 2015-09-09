package com.hxb.util.flushOracleDB;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hxb.util.hibernate.HibernateCallback;

@Repository("flushOrcalDbDao")
public class FlushDao  extends HibernateDaoSupport {
	/**
	 * 刷数据库连接，防止oracle偷偷关闭连接
	 * @return
	 */
	public void flush() {
		getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public List<Object> doInHibernate(final Session s) throws HibernateException,SQLException {
						SQLQuery query = s.createSQLQuery("select 1 from dual ");
						query.list();
						return null ;
					}
				}
		) ;
		return  ;
	}

}
