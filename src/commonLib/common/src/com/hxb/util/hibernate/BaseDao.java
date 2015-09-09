package com.hxb.util.hibernate ;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {
	T get(Serializable id);
	List<T> getAll();
//	List<T> find(String hql,);
	Object save(Object o);
	Object remove(Object o);
	Object update(Object o);
}
