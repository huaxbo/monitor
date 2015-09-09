package com.hxb.global.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hxb.global.po.TbSysLog;
import com.hxb.util.DateTime;

/**
 * @author Administrator
 *--Transactional������������
 *--Service������spring annotation��ʽע��Busi����strutsAction��annotationע��busi
 *ע�⣺���ͱ�������context��û���ظ�����
 */
@Transactional
@Service
public class LogBusi {
	/**
	 * ����dao�����
	 * --Autowired��spring annotation��ʽע�����
	 * �����Բ�������setter��getter������annotation��ʽע���Ѿ�������
	 * ע�⣺�˶���������ע��ѡ��@Repository����ע�롣�Ҵ����ͱ�������context��û���ظ�����
	 */
	@Autowired
	private LogDao dao ;

	/**
	 * @param roleId
	 * @param userName
	 * @param dt
	 * @param operate
	 * @param currIp
	 * --Transactional����������������͡��洢�����¡�ɾ����������ֵΪ��rollbackFor=Exception.class
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
