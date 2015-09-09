package com.hxb.commu.service.meter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hxb.global.po.TbCsMeter;

/**
 * @author Administrator
 * ҵ��㣺����豸��Ϣ����
 */
@Service
@Transactional
public class TbCsMeterBusi {

	@Autowired
	private TbCsMeterDao dao;
	
	/**
	 * ��ȡȫ���豸�б�
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<TbCsMeter> getAll(){
		
		return dao.getAll();
	}
	
}
