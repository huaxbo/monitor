package com.hxb.monitor.web.manager.server;

import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hxb.global.po.TbCsServer;

@Transactional
@Service
public class ServerBusi {
	
	@Autowired
	private ServerDao dao;
	
	
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
	public TbCsServer getById(String id){
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
	public String save(TbCsServer po){
		po = (TbCsServer)dao.save(po);		
		
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
}
