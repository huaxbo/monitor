package com.hxb.util.system;

import org.springframework.transaction.annotation.Transactional;


@Transactional
public class CleanDataBaseLogBusi {
	private CleanDataBaseLogDao dao ;
	public CleanDataBaseLogDao getDao() {
		return dao;
	}
	public void setDao(CleanDataBaseLogDao dao) {
		this.dao = dao;
	}
	public void clean(String dataBaseName){
		this.dao.clean1(dataBaseName) ;
		this.dao.clean2(dataBaseName) ;
		this.dao.clean3(dataBaseName) ;
	 }

}
