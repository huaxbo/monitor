package com.hxb.global.service.log;

import org.springframework.stereotype.Repository;

import com.hxb.global.po.TbSysLog;
import com.hxb.util.hibernate.HibernateAnnoBaseDao;
/**
 * @author Administrator
 *--Repository������spring annotation��ʽע��Dao����Busi��annotationע��dao
 *ע�⣺���ͱ�������context��û���ظ�����
 */
@Repository
public class LogDao  extends HibernateAnnoBaseDao<TbSysLog> {


}
