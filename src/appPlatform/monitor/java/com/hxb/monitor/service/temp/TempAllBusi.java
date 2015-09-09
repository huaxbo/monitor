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
	 * 获取待处理记录
	 * @param clazz
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Object> getSome(Class<?> clazz){
		
		return dao.getSome(clazz);
	}
	
	/**
	 * 删除记录
	 * @param clazz
	 * @param ids
	 */
	@Transactional(rollbackFor=Exception.class)
	public void delSome(Class<?> clazz,String[] ids){
		
		dao.delSome(clazz, ids);
	}
	
	/**
	 * 保持处理失败上报结果记录（命令结果/主动上报数据）
	 * @param failed
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveFailed(TbCsTempFailed failed){
		
		dao.save(failed);
	}
}
