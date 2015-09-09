package com.hxb.commu.service.meter;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.hxb.global.po.TbCsMeter;
import com.hxb.util.hibernate.HibernateAnnoBaseDao;
import com.hxb.util.hibernate.HibernateCallback;

/**
 * @author Administrator
 * 数据层：测控设备信息操作
 */
@Repository
public class TbCsMeterDao extends HibernateAnnoBaseDao<TbCsMeter> {
	
	/* (non-Javadoc)
	 * @see com.hxb.util.hibernate.HibernateAnnoBaseDao#getAll()
	 * 查询全部测控设备列表
	 */
	@SuppressWarnings("unchecked")
	public List<TbCsMeter> getAll(){
		return (List<TbCsMeter>)getHibernateTemplate().execute(
				new HibernateCallback() {					
					@Override
					public List<TbCsMeter> doInHibernate(Session s) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						
						return s.createQuery("from TbCsMeter order by meterPro asc ").list();
					}
				}
				
		);
	}
}
