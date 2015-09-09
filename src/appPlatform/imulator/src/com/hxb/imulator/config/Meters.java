package com.hxb.imulator.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Meters {

	public static HashMap<String,String[]> meters = new HashMap<String,String[]>(0);
	
	static{
		/**
		 * 协议：ver_test
		 * */
		meters.put("1001910001", new String[]{Drivers.ver_test[0],Centers.center_1[0]});
		meters.put("1001910002", new String[]{Drivers.ver_test[0],Centers.center_1[0]});
		/*meters.put("1001910003", new String[]{Drivers.ver_test[0],Centers.center_1[0]});
		meters.put("1001910004", new String[]{Drivers.ver_test[0],Centers.center_1[0]});
		meters.put("1001910005", new String[]{Drivers.ver_test[0],Centers.center_1[0]});
		meters.put("1001910006", new String[]{Drivers.ver_test[0],Centers.center_1[0]});*/
		
	}
	
	/**
	 * 获取全部设备:设备id|协议驱动|通讯服务id
	 * @return
	 */
	public static String[][] getAll(){
		String[][] all = new String[meters.size()][3];
		
		Iterator<Entry<String,String[]>> it = meters.entrySet().iterator();
		int idx = 0;
		while(it.hasNext()){
			Entry<String,String[]> e = it.next();
			all[idx][0] = e.getKey();
			all[idx][1] = Drivers.getDriver(e.getValue()[0]);
			all[idx][2] = e.getValue()[1];
			
			idx++;
		}
		
		
		return all;
	}
	
}
