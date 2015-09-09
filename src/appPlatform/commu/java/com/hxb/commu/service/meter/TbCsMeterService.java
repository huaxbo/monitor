package com.hxb.commu.service.meter;

import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.hxb.global.po.TbCsMeter;
import com.hxb.global.util.HttpUtil;
import com.hxb.global.util.CommonUtil;

public class TbCsMeterService {

	private Logger log = Logger.getLogger(TbCsMeterService.class);
	private static TbCsMeterService service;
	private Hashtable<String,MeterVO> meters;
	/**
	 * 
	 */
	private TbCsMeterService(){}
	/**
	 * 获取唯一实例
	 * @return
	 */
	public static TbCsMeterService getSingle(){
		if(service == null){
			service = new TbCsMeterService();
			service.getAll(true);
		}
		return service;
	}
	
	/**
	 * 根据设备id获取测控设备信息
	 * @param meterId
	 * @return
	 */
	public MeterVO getMeter(String meterId){
		MeterVO vo = meters.get(meterId);
		if(vo == null){//重新加载测控设备配置信息
			getAll(true);
		}
		vo = meters.get(meterId);
		
		return vo;
	}
	
	/**
	 * 获取全部设备
	 * @return
	 */
	public Hashtable<String,MeterVO> getAll(boolean refresh){
		if(meters == null){
			meters = new Hashtable<String,MeterVO>(0);
		}		
		if(meters.size() == 0 || refresh){
			TbCsMeterBusi busi = (TbCsMeterBusi)HttpUtil.getSpringBean(CommonUtil.getClassName(TbCsMeterBusi.class));
			if(busi != null){
				List<TbCsMeter> lt = busi.getAll();
				for(TbCsMeter m : lt){
					MeterVO vo = new MeterVO();
					vo.build(m);
					meters.put(m.getMeterId(), vo);
				}
			}else{
				log.error("获取测控设备操作业务层[TbCsMeterBusi]失败！");
			}
		}
		
		return meters;
	}
}
