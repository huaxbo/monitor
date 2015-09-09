package com.hxb.util.hibernate ;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateBaseDao<T> extends HibernateDaoSupport implements BaseDao<T>{
	private Class<T> entityClass;
	@SuppressWarnings("unchecked")
	public HibernateBaseDao() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass =  (Class)params[0];
	}
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return (T)getHibernateTemplate().load(entityClass, id);
	}
	 @SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}
	public Object save(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
		return o ;
	}
	public Object remove(Object o) {
		getHibernateTemplate().delete(o);
		return o ;
	}
	public Object update(Object o) {
		getHibernateTemplate().update(o);		
		return o ;
	}
}