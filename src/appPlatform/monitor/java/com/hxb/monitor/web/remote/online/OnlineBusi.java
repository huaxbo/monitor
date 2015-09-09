package com.hxb.monitor.web.remote.online;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hxb.commu.local.common.CommandAttach;
import com.hxb.commu.local.common.LocalCode;
import com.hxb.global.util.SerializeUtil;
import com.hxb.monitor.commu.localServer.LocalServerManager;
import com.hxb.monitor.commu.protocol.CommandConstant;
import com.hxb.monitor.web.manager.server.ServerVO;


@Transactional
@Service
public class OnlineBusi {
	
	@Autowired
	private OnlineDao dao;
	
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
	 * @param mlist
	 * @param slist
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Object> list(Hashtable<String,Object> params){
		List<Object> slist = dao.slist();
		List<Object> mlist = dao.mlist(params);
		
		CommandAttach ca = new CommandAttach();
		ca.setFuncCode(LocalCode.local_onLines);
		Vector<String> olvs = new Vector<String>(0);
		
		for(Object o:slist){
			ServerVO svo = (ServerVO)o;
			try {
				String onlines = LocalServerManager.sendCommand(svo.getId(),ca);
				if(onlines != null){
					CommandAttach car = (CommandAttach)SerializeUtil.getInstance().xml2Obj(onlines, new Class[]{CommandAttach.class});
					if(car != null){
						String[] ols = (String[])car.getReceiptObj();	
						for(String mid:ols){
							olvs.add(mid);
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{}
		}		
		//设备状态处理
		for(Object o:mlist){
			OnlineVO ovo = (OnlineVO)o;
			if(olvs.contains(ovo.getMeterId())){
				ovo.setOnline(CommandConstant.ONLINE_STATUS_ON);
			}else{
				ovo.setOnline(CommandConstant.ONLINE_STATUS_UN);
			}
		}
		
		return mlist;
	}	
}
