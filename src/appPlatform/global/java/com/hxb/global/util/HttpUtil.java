/**   
* @Title: HttpUtil.java 
* @Package com.hxb.global.util 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author huaXinbo   
* @date 2014-4-1 ����03:12:06 
* @version V1.0   
*/ 
package com.hxb.global.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionContext;

/** 
 * @ClassName: HttpUtil 
 * @Description: TODO(������һ�仰��������������) 
 * @author huaXinbo
 * @date 2014-4-1 ����03:12:06 
 *  
 */
public class HttpUtil {

	private static ActionContext ctx = ActionContext.getContext();
	public static ApplicationContext springContext ;
	
	/** 
	* @Title: getHttpServletRequest 
	* @Description: TODO(��ȡhttpServletRequest) 
	* @return HttpServletRequest    �������� 
	* @throws 
	*/ 
	public static HttpServletRequest getHttpServletRequest(){
		  
		return (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	}

	/** 
	* @Title: getHttpServletResponse 
	* @Description: TODO(��ȡhttpServletResponse) 
	* @return HttpServletResponse    �������� 
	* @throws 
	*/ 
	public static HttpServletResponse getHttpServletResponse(){

		return (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
	}
	/** 
	* @Title: getHttpSesion 
	* @Description: TODO(��ȡhttpSession) 
	* @return HttpSession    �������� 
	* @throws 
	*/ 
	public static HttpSession getHttpSesion(){

		return (HttpSession)ctx.get(ServletActionContext.SESSION);
	}
	
	/** 
	* @Title: getRoot 
	* @Description: TODO(��ȡwebroot����·��) 
	* @return String    �������� 
	* @throws 
	*/ 
	public static String getRoot(){
		
		return ServletActionContext.getServletContext().getRealPath("/");
	}
	
	/**
	 * ��ȡspring bean
	 * @param bean
	 * @return
	 */
	public static Object getSpringBean(String bean){
		if(springContext != null){
			
			return springContext.getBean(bean);
		}
		
		return null;
	}
}
