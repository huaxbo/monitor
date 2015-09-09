package com.hxb.commu.service.meter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hxb.global.po.TbCsMeter;

/**
 * @author Administrator
 * 业务层：测控设备信息操作
 */
@Service
@Transactional
public class TbCsMeterBusi {

	@Autowired
	private TbCsMeterDao dao;
	
	/**
	 * 获取全部设备列表
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<TbCsMeter> getAll(){
		
		return dao.getAll();
	}
	
}
