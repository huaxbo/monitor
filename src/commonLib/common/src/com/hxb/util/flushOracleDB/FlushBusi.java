package com.hxb.util.flushOracleDB;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("flushOrcalDbBusi")
public class FlushBusi {

	@Autowired
	private FlushDao dao ;
	/**
	 * 刷数据库连接，防止oracle偷偷关闭连接
	 * @return
	 */
	public void flush() {
		this.dao.flush()   ;
	}

	public FlushDao getDao() {
		return dao;
	}

	public void setDao(FlushDao dao) {
		this.dao = dao;
	}

}
