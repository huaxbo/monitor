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
 * ���ݲ㣺����豸��Ϣ����
 */
@Repository
public class TbCsMeterDao extends HibernateAnnoBaseDao<TbCsMeter> {
	
	/* (non-Javadoc)
	 * @see com.hxb.util.hibernate.HibernateAnnoBaseDao#getAll()
	 * ��ѯȫ������豸�б�
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
