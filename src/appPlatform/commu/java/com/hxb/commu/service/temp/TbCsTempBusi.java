package com.hxb.commu.service.temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hxb.global.po.TbCsTempAuto;
import com.hxb.global.po.TbCsTempCmd;

/**
 * @author Administrator
 * 业务层：通讯成果临时信息操作
 */
@Service
@Transactional
public class TbCsTempBusi {

	@Autowired
	private TbCsTempDao dao;
	
	/**
	 * 保存临时成果数据
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public String save(String commandId,String data){
		if(commandId != null){
			TbCsTempCmd po = new TbCsTempCmd(commandId,data);
			dao.save(po);
			return po.getId();
		}else{
			TbCsTempAuto po = new TbCsTempAuto(data);
			dao.save(po);
			return po.getId();
		}
	}
	
}
