/**
 * 
 */
package com.hxb.monitor.web.manager.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.hxb.global.po.TbCsServer;
import com.hxb.util.hibernate.HibernateAnnoBaseDao;
import com.hxb.util.hibernate.HibernateCallback;
import com.hxb.util.struts.PageActionSupport;


@Repository
public class ServerDao extends HibernateAnnoBaseDao<TbCsServer>{
	
	public Long getCount(Hashtable<String,Object> params){
		return (Long) getHibernateTemplate().execute(
				new HibernateCallback(params) {
					
					@Override
					public Object doInHibernate(Session s) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						StringBuilder sder = new StringBuilder("select count(id) from TbCsServer where 1=1 ");
						sder.append(buildCon(paramMap));
						Query qy = s.createQuery(sder.toString());
						
						return (Long)qy.list().get(0);
					}
				}
		);
	}
	/**
	 * 查询列表信息
	 */
	@SuppressWarnings("unchecked")
	public List<Object> list(Hashtable<String,Object> params){
		return (List<Object>)getHibernateTemplate().execute(
				new HibernateCallback(params) {
					@Override
					public List<Object> doInHibernate(Session s) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						Integer start = (Integer) paramMap.get(PageActionSupport.queryStartKey);
						Integer count = (Integer) paramMap.get(PageActionSupport.queryCountKey);
						//search
						try{
							//构造HQL
							StringBuilder sder = new StringBuilder(" from TbCsServer where 1=1 ");
							sder.append(buildCon(paramMap));
							sder.append(" order by name asc ");
							//生成Query、赋值参数
							Query qy = s.createQuery(sder.toString());
							//指定分页,记录从0开始计数
							if(start != null && count != null){
								qy.setFirstResult(start);
								qy.setMaxResults(count);
							}
							//执行查询
							List<?> lt = qy.list();
							List<Object> list = new ArrayList<Object>();							
							for(int i=0;i<lt.size();i++){
								TbCsServer po = (TbCsServer)lt.get(i);
								ServerVO vo = new ServerVO();
								vo.build(po);
								list.add(vo);
							}
																					
							return list;
							
						}catch(Exception e){
							e.printStackTrace();
						}finally{}
						
						return null;
					}
				}
		);
	}
	
	/**
	 * 删除记录
	 * @param id
	 */
	public void delete(final String id){
		this.getHibernateTemplate().execute(
				new HibernateCallback() {					
					@Override
					public Object doInHibernate(Session se) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						se.createQuery("delete from TbCsServer where id = '" + id + "'").executeUpdate();
						return null;
					}
				}
		);
	}
	
	/**
	 * 构建查询条件
	 * @param params
	 * @return
	 */
	private String buildCon(Hashtable<String,Object> params){
		StringBuilder sder = new StringBuilder();
		if(params == null){
			return sder.toString();
		}
		String con_name = (String)params.get("con_name");
		if(con_name != null && !con_name.equals("")){
			sder.append(" and name like '%" + con_name + "%'");	
		}
		
		return sder.toString();
	}
}
