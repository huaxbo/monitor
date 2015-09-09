package com.hxb.global.service.log;

import org.springframework.stereotype.Repository;

import com.hxb.global.po.TbSysLog;
import com.hxb.util.hibernate.HibernateAnnoBaseDao;
/**
 * @author Administrator
 *--Repository：设置spring annotation方式注入Dao，供Busi中annotation注入dao
 *注意：类型必须整个context中没有重复类型
 */
@Repository
public class LogDao  extends HibernateAnnoBaseDao<TbSysLog> {


}
