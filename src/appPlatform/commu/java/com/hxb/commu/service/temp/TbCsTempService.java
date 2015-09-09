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
	 * 获取唯一实例
	 * @return
	 */
	public static TbCsTempService getSingle(){
		if(service == null){
			service = new TbCsTempService();
		}
		return service;
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(String commandId,String data){
		log.info("存储结果数据入库：\n" + data);
		
		TbCsTempBusi busi = (TbCsTempBusi)HttpUtil.getSpringBean(CommonUtil.getClassName(TbCsTempBusi.class));
		
		if(busi != null){
			return busi.save(commandId, data);
		}else{
			log.error("获取通讯成果临时数据处理业务[TbCsTempBusi]为空，不能进行存储操作!");
		}
		
		return null;
	}
	
}
