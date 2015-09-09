package com.hxb.util.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author Administrator
 *
 */
public class HibernateAnnoBaseDao<T> extends DaoSupport implements BaseDao<T>{

	private HibernateTemplate hibernateTemplate;
	private Class<T> entityClass;

	/**
	 * 初始化泛型 T
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HibernateAnnoBaseDao() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass =  (Class)params[0];
	}
	
	/* (non-Javadoc)
	 * @see com.automic.util.hibernate.BaseDao#get(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return (T)getHibernateTemplate().load(entityClass, id);
	}
	 /* (non-Javadoc)
	 * @see com.automic.util.hibernate.BaseDao#getAll()
	 * 获取实体 T 全部记录
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}
	/* (non-Javadoc)
	 * @see com.automic.util.hibernate.BaseDao#save(java.lang.Object)
	 * 保存实体
	 */
	public Object save(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
		return o ;
	}
	/* (non-Javadoc)
	 * @see com.automic.util.hibernate.BaseDao#remove(java.lang.Object)
	 * 删除实体
	 */
	public Object remove(Object o) {
		getHibernateTemplate().delete(o);
		return o ;
	}
	/* (non-Javadoc)
	 * @see com.automic.util.hibernate.BaseDao#update(java.lang.Object)
	 * 更新实体
	 */
	public Object update(Object o) {
		getHibernateTemplate().update(o);		
		return o ;
	}

	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		if (this.hibernateTemplate == null) {
			throw new IllegalArgumentException(
					"'hibernateTemplate' is required");
		}
	}

	/**
	 * 通过spring annotation方式注入<bean id="hibernateTemplate">
	 * <bean id="hibernateTemplate">通过xml方式依赖注入
	 * @param hibernateTemplate
	 */
	@Resource(name="hibernateTemplate")
	public final void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public final HibernateTemplate getHibernateTemplate() {
		return this.hibernateTemplate;
	}
}
