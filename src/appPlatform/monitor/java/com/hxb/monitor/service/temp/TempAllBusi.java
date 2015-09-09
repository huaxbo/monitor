package com.hxb.monitor.service.temp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hxb.global.po.TbCsTempFailed;

@Service
@Transactional
public class TempAllBusi {

	@Autowired
	private TempAllDao dao;
	
	
	/**
	 * ��ȡ�������¼
	 * @param clazz
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Object> getSome(Class<?> clazz){
		
		return dao.getSome(clazz);
	}
	
	/**
	 * ɾ����¼
	 * @param clazz
	 * @param ids
	 */
	@Transactional(rollbackFor=Exception.class)
	public void delSome(Class<?> clazz,String[] ids){
		
		dao.delSome(clazz, ids);
	}
	
	/**
	 * ���ִ���ʧ���ϱ������¼��������/�����ϱ����ݣ�
	 * @param failed
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveFailed(TbCsTempFailed failed){
		
		dao.save(failed);
	}
}
