/**   
* @Title: Deptartments.java 
* @Package com.hxb.demo.service.enumeration 
* @Description: TODO(用一句话描述该文件做什么) 
* @author huaXinbo   
* @date 2014-3-19 下午03:44:15 
* @version V1.0   
*/ 
package com.hxb.demo.service.enumeration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hxb.global.po.TbDemoDepartment;

/** 
 * @ClassName: EnumerationBusi 
 * @Description: TODO(查询枚举信息Busi) 
 * @author huaXinbo
 * @date 2014-3-19 下午03:44:15 
 *  
 */
@Transactional
@Service
public class DemoEnumBusi {

	@Autowired
	DemoEnumDao dao;	
	
	/** 
	* @Title: getDeptartments 
	* @Description: TODO(查询院系枚举信息) 
	* @return List<PomfTbDemoDepartment>    返回类型 
	* @throws 
	*/ 
	public List<TbDemoDepartment> getDeptartments(){
		
		return dao.getDepartments();
	}
	
}
