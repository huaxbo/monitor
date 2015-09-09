/**   
* @Title: DepartmentsDao.java 
* @Package com.hxb.demo.service.enumeration 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author huaXinbo   
* @date 2014-3-19 ����03:46:45 
* @version V1.0   
*/ 
package com.hxb.demo.service.enumeration;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.hxb.global.po.TbDemoDepartment;
import com.hxb.util.hibernate.HibernateAnnoBaseDao;
import com.hxb.util.hibernate.HibernateCallback;

/** 
 * @ClassName: DepartmentsDao 
 * @Description: TODO(��ѯö����ϢDao) 
 * @author huaXinbo
 * @date 2014-3-19 ����03:46:45 
 *  
 */
@Repository
public class DemoEnumDao extends HibernateAnnoBaseDao<TbDemoDepartment>{

	/** 
	* @Title: getDepartments 
	* @Description: TODO(��ѯԺϵö����Ϣ) 
	* @return List<PomfTbDemoDepartment>    �������ͣ�Ժϵö����Ϣ 
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public List<TbDemoDepartment> getDepartments(){
		return getHibernateTemplate().executeFind(
				new HibernateCallback() {
					@Override
					public List<TbDemoDepartment> doInHibernate(Session se) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						return se.createQuery("from PomfTbDemoDepartment order by name desc ").list();
					}
				}
		);
	}
	
}
