/**   
* @Title: EnumerationService.java 
* @Package com.hxb.demo.service.enumeration 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author huaXinbo   
* @date 2014-3-19 ����04:26:56 
* @version V1.0   
*/ 
package com.hxb.demo.service.enumeration;

import java.util.List;

import com.hxb.global.po.TbDemoDepartment;
import com.hxb.global.service.util.SpringContextUtil;

/** 
 * @ClassName: EnumerationService 
 * @Description: TODO(������һ�仰��������������) 
 * @author huaXinbo
 * @date 2014-3-19 ����04:26:56 
 *  
 */
public class DemoEnumService {

	/** 
	* @Title: getDepartments 
	* @Description: TODO(��ȡѧԺö����Ϣ) 
	* @return List<PomfTbDemoDepartment>    �������� 
	* @throws 
	*/ 
	public static List<TbDemoDepartment> getDepartments(){
		
		return getBusiBean().getDeptartments();
	}
	
	
	
	
	
	
	
	/** 
	* @Title: getBusiBean 
	* @Description: TODO(��ȡҵ��spring bean) 
	* @return EnumerationBusi    �������� 
	* @throws 
	*/ 
	private static DemoEnumBusi getBusiBean(){
		return (DemoEnumBusi)SpringContextUtil.getSpringBean("demoEnumBusi");
	}
}
