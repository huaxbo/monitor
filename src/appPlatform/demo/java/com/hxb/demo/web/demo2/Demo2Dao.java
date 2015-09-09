/**
 * 
 */
package com.hxb.demo.web.demo2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.hxb.global.po.TbDemoDepartment;
import com.hxb.global.po.TbDemoStuAttachment;
import com.hxb.global.po.TbDemoStudent;
import com.hxb.util.DateTime;
import com.hxb.util.hibernate.HibernateAnnoBaseDao;
import com.hxb.util.hibernate.HibernateCallback;
import com.hxb.util.struts.PageActionSupport;


/**
 * @author Administrator
 *--Repository：设置spring annotation方式注入Dao，供Busi中annotation注入dao
 *注意：类型必须整个context中没有重复类型
 */
@Repository
public class Demo2Dao extends HibernateAnnoBaseDao<TbDemoStudent>{
	
	public Long getCount(Hashtable<String,Object> params,final String param){
		return (Long) getHibernateTemplate().execute(
				new HibernateCallback(params) {
					
					@Override
					public Object doInHibernate(Session s) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						StringBuilder sder = new StringBuilder("select count(*) from TbDemoStudent ds inner join ds.dept dt where 1=1 ");
						//日期比较
						sder.append(" and ds.dt >= :startDt and ds.dt <= :endDt ");
						sder.append(buildConditional(paramMap,"ds","dt"));
						Query qy = s.createQuery(sder.toString());
						qy.setDate("startDt", DateTime.yyyy_MM_dd((String)paramMap.get("con_startDt")));
						qy.setDate("endDt", DateTime.yyyy_MM_dd((String)paramMap.get("con_endDt")));
						
						return (Long)qy.list().get(0);
					}
				}
		);
	}
	/**
	 * 数据库中，测站-实时雨情信息查询demo
	 */
	@SuppressWarnings("unchecked")
	public List<Object> list(Hashtable<String,Object> params,final String param){
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
							StringBuilder sder = new StringBuilder(" from TbDemoStudent ds inner join ds.dept dpt where 1=1");
							sder.append(buildConditional(paramMap,"ds","dpt"));
							//日期比较
							sder.append(" and ds.dt >= :startDt and ds.dt <= :endDt ");
							sder.append(" order by ds.dept.name asc,ds.name asc");
							//生成Query、赋值参数
							Query qy = s.createQuery(sder.toString());
							//时间限制测试
							qy.setDate("startDt", DateTime.yyyy_MM_dd((String)paramMap.get("con_startDt")));
							qy.setDate("endDt", DateTime.yyyy_MM_dd((String)paramMap.get("con_endDt")));
							//指定分页,记录从0开始计数
							if(start != null && count != null){
								qy.setFirstResult(start);
								qy.setMaxResults(count);
							}
							//执行查询
							Iterator<Object[]> it = qy.list().iterator();
							List<Object> list = new ArrayList<Object>();
							while(it.hasNext()){
								Object[] oo = it.next();
								TbDemoStudent po = (TbDemoStudent)oo[0];
								po.fetch((TbDemoDepartment)oo[1]);//数据关联抓取。避免编辑操作完成后，执行list查询，导致数据延迟加载失效
								
								DemoVO vo = new DemoVO();
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
	* @Title: validateForm 
	* @Description: TODO(远程校验：学生编码是否重复) 
	* @return Boolean    返回类型 
	* @throws 
	*/ 
	public Boolean validateForm(Hashtable<String,Object> params){
		return (Boolean)getHibernateTemplate().execute(
				new HibernateCallback(params) {					
					@Override
					public Boolean doInHibernate(Session se) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						String id = (String) paramMap.get("id");
						String cd = (String) paramMap.get("cd");
						
						StringBuilder sder = new StringBuilder(" select count(*) from TbDemoStudent where 1=1 ");
						if(id != null && !id.equals("")){
							sder.append(" and id <> '" + id + "'");
						}
						if(cd != null && !cd.equals("")){
							sder.append(" and cd = '" + cd + "'");
						}
						
						return (Long)se.createQuery(sder.toString()).list().get(0) == 0;
					}
				}
		);
	}
	/** 
	* @Title: delete 
	* @Description: TODO(删除记录) 
	* @return void    返回类型 
	* @throws 
	*/ 
	public void delete(final String id){
		this.getHibernateTemplate().execute(
				new HibernateCallback() {					
					@Override
					public Object doInHibernate(Session se) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						se.createQuery("delete from TbDemoStudent where id = '" + id + "'").executeUpdate();
						return null;
					}
				}
		);
	}
	/** 
	* @Title: getAttas 
	* @Description: TODO(根据学生id获取附件记录) 
	* @return String[]    返回类型 :附件记录id数组
	* @throws 
	*/ 
	public String[] getAttas(final String stuId){
		return(String[])getHibernateTemplate().execute(
				new HibernateCallback() {					
					@SuppressWarnings("unchecked")
					@Override
					public Object doInHibernate(Session s) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						List<String> lt = (List<String>)s.createQuery(" select id from TbDemoStuAttachment where studentId = '" + stuId + "'").list();
						String[] arr = new String[lt.size()];
						int idx = 0;
						for(String str:lt){
							arr[idx++] = str;
						}
						return arr;
					}
				}
		);
	}
	/** 
	* @Title: delAttas 
	* @Description: TODO(删除附件记录) 
	* @return String[]    返回类型 ：删除附件记录的url数组
	* @throws 
	*/ 
	@SuppressWarnings("unchecked")
	public String[] delAttas(final String[] ids){
		return (String[])getHibernateTemplate().execute(
				new HibernateCallback() {					
					@Override
					public String[] doInHibernate(Session s) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						String ids_str = "";
						StringBuilder sder = new StringBuilder();
						for(String id:ids){
							if(id.contains(",")){
								for(String idg:id.split(",")){
									if(idg != null && !idg.equals("")){
										sder.append("'" + idg + "',");	
									}									
								}
							}else{
								if(id != null && !id.equals("")){
									sder.append("'" + id + "',");	
								}
							}						
						}
						if(sder.length()>1){
							ids_str = sder.substring(0, sder.length()-1);							
						}
						if(ids_str.equals("")){//无需删除记录
							return null;
						}
						
						//查询附件
						StringBuilder qsder = new StringBuilder();
						qsder.append(" select attaUrl from TbDemoStuAttachment atta where 1=1 ");
						qsder.append(" and id in (" + ids_str + ")");
						List<String> lt = s.createQuery(qsder.toString()).list();
						String[] arr = new String[lt.size()];
						int idx = 0;
						for(String str:lt){
							arr[idx++] = str;
						}
						//删除附件记录
						s.createQuery(" delete from TbDemoStuAttachment where id in (" + ids_str + ")").executeUpdate();
						s.flush();
						
						return arr;
					}
				}
		);
	}
	/** 
	* @Title: saveAttachement 
	* @Description: TODO(保存附件) 
	* @return String    返回类型 
	* @throws 
	*/ 
	public String saveAttachement(final TbDemoStuAttachment atta){
		return (String)getHibernateTemplate().execute(
				new HibernateCallback() {					
					@Override
					public String doInHibernate(Session s) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						String id = (String)s.save(atta);
						
						return id;
					}
				}
		);
	}
	/** 
	* @Title: buildConditional 
	* @Description: TODO(构建查询条件) 
	* @param @param params：：查询条件
	* @param @param tb：：表别名
	* @param @return     
	* @return String   查询条件HQL 
	* @throws 
	*/ 
	private String buildConditional(Hashtable<String,Object> params,String tb,String tb2){
		StringBuilder sder = new StringBuilder();
		String con_deptName = (String) params.get("con_deptName");
		String con_name = (String) params.get("con_name");
		if(con_deptName != null && !con_deptName.equals("")){
			sder.append(" and " + tb2 + ".name like '%" + con_deptName + "%'");
		}
		if(con_name != null && !con_name.equals("")){
			sder.append(" and " + tb + ".name like '%" + con_name + "%'");
		}
		
		return sder.toString();
	} 
}
