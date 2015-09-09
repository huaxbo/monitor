/**   
* @Title: EnumerationService.java 
* @Package com.hxb.demo.service.enumeration 
* @Description: TODO(用一句话描述该文件做什么) 
* @author huaXinbo   
* @date 2014-3-19 下午04:26:56 
* @version V1.0   
*/ 
package com.hxb.demo.service.enumeration;

import java.util.List;

import com.hxb.global.po.TbDemoDepartment;
import com.hxb.global.service.util.SpringContextUtil;

/** 
 * @ClassName: EnumerationService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author huaXinbo
 * @date 2014-3-19 下午04:26:56 
 *  
 */
public class DemoEnumService {

	/** 
	* @Title: getDepartments 
	* @Description: TODO(获取学院枚举信息) 
	* @return List<PomfTbDemoDepartment>    返回类型 
	* @throws 
	*/ 
	public static List<TbDemoDepartment> getDepartments(){
		
		return getBusiBean().getDeptartments();
	}
	
	
	
	
	
	
	
	/** 
	* @Title: getBusiBean 
	* @Description: TODO(获取业务spring bean) 
	* @return EnumerationBusi    返回类型 
	* @throws 
	*/ 
	private static DemoEnumBusi getBusiBean(){
		return (DemoEnumBusi)SpringContextUtil.getSpringBean("demoEnumBusi");
	}
}
