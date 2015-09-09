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
	 * ��ȡ��¼����
	 * @param params
	 * @param param
	 * @return
	 */
	@Transactional(readOnly=true)
	public Long getCount(Hashtable<String,Object> params){
		return dao.getCount(params);
	}
	
	/**
	 * ��ȡ��¼����
	 * @param params
	 * @param param
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Object> list(Hashtable<String,Object> params){
		return dao.list(params);
	}
	
	/**
	 * ��ȡ��¼��Ϣ
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public TbCsMeter getById(String id){
		return dao.get(id);
	}
	
	/**
	 * ����/���¼�¼��Ϣ
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
	 * ɾ����¼��Ϣ
	 * @param ids
	 */
	@Transactional(rollbackFor=Exception.class)
	public void delete(String[] ids){		
		//ɾ������������¼���ļ�
		for(String id:ids){
			//ɾ����¼
			dao.delete(id);
		}
	}
	/**
	 * ��ѯͨѶ�����б�
	 * @return
	 */
	public List<Object> getServers(){
		
		return sdao.list(new Hashtable<String,Object>(0));
	}
}
