package com.hxb.monitor.web.manager.meter;

import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hxb.global.po.TbCsMeter;
import com.hxb.monitor.commu.protocol.CommandConstant;
import com.hxb.monitor.web.manager.server.ServerDao;

@Transactional
@Service
public class MeterBusi {
	
	@Autowired
	private MeterDao dao;
	@Autowired
	private ServerDao sdao;
	
	/**
	 * 获取记录总数
	 * @param params
	 * @param param
	 * @return
	 */
	@Transactional(readOnly=true)
	public Long getCount(Hashtable<String,Object> params){
		return dao.getCount(params);
	}
	
	/**
	 * 获取记录集合
	 * @param params
	 * @param param
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Object> list(Hashtable<String,Object> params){
		return dao.list(params);
	}
	
	/**
	 * 获取记录信息
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public TbCsMeter getById(String id){
		return dao.get(id);
	}
	
	/**
	 * 保存/更新记录信息
	 * @param po
	 * @param files
	 * @param fileNames
	 * @param del_fileIds
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	public String save(TbCsMeter po){
		if(po.getMeterPro() != null){
			po.setMeterDriver(CommandConstant.getProtocolDriver(po.getMeterPro()));
		}
		po = (TbCsMeter)dao.save(po);		
		
		return po.getId();
	}	
	
	/**
	 * 删除记录信息
	 * @param ids
	 */
	@Transactional(rollbackFor=Exception.class)
	public void delete(String[] ids){		
		//删除关联附件记录及文件
		for(String id:ids){
			//删除记录
			dao.delete(id);
		}
	}
	/**
	 * 查询通讯服务列表
	 * @return
	 */
	public List<Object> getServers(){
		
		return sdao.list(new Hashtable<String,Object>(0));
	}
}
