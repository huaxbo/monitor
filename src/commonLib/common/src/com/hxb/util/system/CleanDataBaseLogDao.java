package com.hxb.util.system;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hxb.util.hibernate.HibernateCallback;

public class CleanDataBaseLogDao extends HibernateDaoSupport{
	
	public void clean1( final String dataBaseName) {
		getHibernateTemplate().execute(
				  new HibernateCallback() {
					public Object doInHibernate(final Session s) throws HibernateException, SQLException {
						String sql = "DUMP   TRANSACTION   " + dataBaseName + "   WITH   NO_LOG  " ;
						SQLQuery q = s.createSQLQuery(sql);
						q.executeUpdate() ;
						
						return null  ;
					}
				});

	}
	public void clean2( final String dataBaseName) {
		getHibernateTemplate().execute(
				  new HibernateCallback() {
					public Object doInHibernate(final Session s) throws HibernateException, SQLException {
						String sql = " BACKUP   LOG   " + dataBaseName + "   WITH   NO_LOG  "  ;
						
						SQLQuery q = s.createSQLQuery(sql);
						q.executeUpdate() ;
						
						return null  ;
					}
				});

	}
	public void clean3( final String dataBaseName) {
		getHibernateTemplate().execute(
				  new HibernateCallback() {
					public Object doInHibernate(final Session s) throws HibernateException, SQLException {
						String sql = "DBCC   SHRINKDATABASE(" + dataBaseName + ")  "  ;
						
						SQLQuery q = s.createSQLQuery(sql);
						q.executeUpdate() ;
						
						return null  ;
					}
				});

	}
}
