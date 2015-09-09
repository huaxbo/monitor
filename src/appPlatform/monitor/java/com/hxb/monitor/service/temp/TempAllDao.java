package com.hxb.monitor.service.temp;


import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.hxb.global.po.TbCsTempAuto;
import com.hxb.util.hibernate.HibernateAnnoBaseDao;
import com.hxb.util.hibernate.HibernateCallback;

@Repository
public class TempAllDao extends HibernateAnnoBaseDao<TbCsTempAuto> {

	
	/**
	 * 查询待处理记录
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getSome(final Class<?> clazz){
		return (List<Object>)getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session s) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						Query qy = s.createQuery("from " + clazz.getSimpleName() + " order by ts asc ");
						
						qy.setFirstResult(0);
						qy.setMaxResults(10);
						
						return qy.list();
					}
				}
		);
	}
	
	
	/**
	 * 删除指定记录
	 * @param ids
	 */
	public void delSome(final Class<?> clazz,final String[] ids){
		getHibernateTemplate().execute(
			new HibernateCallback() {
				@Override
				public Object doInHibernate(Session s) throws HibernateException,
						SQLException {
					// TODO Auto-generated method stub
					
					s.createQuery("delete " + clazz.getSimpleName() + " where id in (" + arr2Str(ids) + ")").executeUpdate();
					
					return null;
				}
			}
		
		);	
	}
	
	
	/**
	 * 构建条件
	 * @param ids
	 * @return
	 */
	private String arr2Str(String[] ids){
		StringBuilder sder = new StringBuilder("");
		if(ids != null){
			for(String id:ids){
				sder.append("'" + id + "'");
				sder.append(",");
			}
			if(sder.length() > 0){
				sder.delete(sder.length()-1, sder.length());
			}
		}
		
		return sder.toString();
	}
}
