/**   
* @Title: Deptartments.java 
* @Package com.hxb.demo.service.enumeration 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author huaXinbo   
* @date 2014-3-19 ����03:44:15 
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
 * @Description: TODO(��ѯö����ϢBusi) 
 * @author huaXinbo
 * @date 2014-3-19 ����03:44:15 
 *  
 */
@Transactional
@Service
public class DemoEnumBusi {

	@Autowired
	DemoEnumDao dao;	
	
	/** 
	* @Title: getDeptartments 
	* @Description: TODO(��ѯԺϵö����Ϣ) 
	* @return List<PomfTbDemoDepartment>    �������� 
	* @throws 
	*/ 
	public List<TbDemoDepartment> getDeptartments(){
		
		return dao.getDepartments();
	}
	
}
