package com.hxb.commu.service.temp;

import org.apache.log4j.Logger;

import com.hxb.global.util.CommonUtil;
import com.hxb.global.util.HttpUtil;

public class TbCsTempService {

	private Logger log = Logger.getLogger(TbCsTempService.class);
	private static TbCsTempService service;
	/**
	 * 
	 */
	private TbCsTempService(){}
	/**
	 * ��ȡΨһʵ��
	 * @return
	 */
	public static TbCsTempService getSingle(){
		if(service == null){
			service = new TbCsTempService();
		}
		return service;
	}
	
	/**
	 * ����
	 * @return
	 */
	public String save(String commandId,String data){
		log.info("�洢���������⣺\n" + data);
		
		TbCsTempBusi busi = (TbCsTempBusi)HttpUtil.getSpringBean(CommonUtil.getClassName(TbCsTempBusi.class));
		
		if(busi != null){
			return busi.save(commandId, data);
		}else{
			log.error("��ȡͨѶ�ɹ���ʱ���ݴ���ҵ��[TbCsTempBusi]Ϊ�գ����ܽ��д洢����!");
		}
		
		return null;
	}
	
}
