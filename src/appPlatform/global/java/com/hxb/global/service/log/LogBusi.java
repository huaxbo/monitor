package com.hxb.global.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hxb.global.po.TbSysLog;
import com.hxb.util.DateTime;

/**
 * @author Administrator
 *--Transactional：声明事物监控
 *--Service：设置spring annotation方式注入Busi，供strutsAction中annotation注入busi
 *注意：类型必须整个context中没有重复类型
 */
@Transactional
@Service
public class LogBusi {
	/**
	 * 声明dao层对象
	 * --Autowired：spring annotation方式注入对象。
	 * 此属性不必生成setter、getter方法，annotation方式注入已经做处理
	 * 注意：此对象必须添加注释选项@Repository进行注入。且此类型必须整个context中没有重复类型
	 */
	@Autowired
	private LogDao dao ;

	/**
	 * @param roleId
	 * @param userName
	 * @param dt
	 * @param operate
	 * @param currIp
	 * --Transactional：设置事物控制类型。存储、更新、删除操作设置值为：rollbackFor=Exception.class
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveLog(String roleId , String userName , String dt , String operate , String currIp){
		try{
			TbSysLog po = new TbSysLog() ;
			po.setRoleId(roleId) ;
			po.setUserName(userName) ;
			po.setOptTm(DateTime.dateFrom_yyyy_MM_dd_HH_mm_ss(dt)) ;
			po.setOptCnt(operate) ;
			po.setOptIp(currIp) ;
			this.dao.save(po) ;
		}catch(Exception e){
			
		}finally{}
	}

	public LogDao getDao() {
		return dao;
	}

	public void setDao(LogDao dao) {
		this.dao = dao;
	}

}
