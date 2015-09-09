/**   
* @Title: SpringContextUtil.java 
* @Package com.hxb.demo.service.util 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author huaXinbo   
* @date 2014-3-19 ����04:21:03 
* @version V1.0   
*/ 
package com.hxb.global.service.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/** 
 * @ClassName: SpringContextUtil 
 * @Description: TODO(��ȡspring bean����) 
 * @author huaXinbo
 * @date 2014-3-19 ����04:21:03 
 *  
 */
@Service
@Scope("singleton")
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext spContext; 
	
	/* (�� Javadoc) 
	 * <p>Title: setApplicationContext</p> 
	 * <p>Description: </p> 
	 * @param arg0
	 * @throws BeansException 
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext) 
	 */
	@Override
	public void setApplicationContext(ApplicationContext spContext)
			throws BeansException {
		// TODO Auto-generated method stub
		SpringContextUtil.spContext = spContext;
	}
	
	/** 
	* @Title: getSpringBean 
	* @Description: TODO(��ȡspring bean) 
	* @return Object    �������� 
	* @throws 
	*/ 
	public static Object getSpringBean(String beanId){
		
		return spContext.getBean(beanId);
	}
}
