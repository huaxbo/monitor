package com.hxb.util.flushOracleDB;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("flushOrcalDbBusi")
public class FlushBusi {

	@Autowired
	private FlushDao dao ;
	/**
	 * ˢ���ݿ����ӣ���ֹoracle͵͵�ر�����
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
