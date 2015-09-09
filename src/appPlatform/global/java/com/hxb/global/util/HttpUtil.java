/**   
* @Title: HttpUtil.java 
* @Package com.hxb.global.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author huaXinbo   
* @date 2014-4-1 下午03:12:06 
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
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author huaXinbo
 * @date 2014-4-1 下午03:12:06 
 *  
 */
public class HttpUtil {

	private static ActionContext ctx = ActionContext.getContext();
	public static ApplicationContext springContext ;
	
	/** 
	* @Title: getHttpServletRequest 
	* @Description: TODO(获取httpServletRequest) 
	* @return HttpServletRequest    返回类型 
	* @throws 
	*/ 
	public static HttpServletRequest getHttpServletRequest(){
		  
		return (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	}

	/** 
	* @Title: getHttpServletResponse 
	* @Description: TODO(获取httpServletResponse) 
	* @return HttpServletResponse    返回类型 
	* @throws 
	*/ 
	public static HttpServletResponse getHttpServletResponse(){

		return (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
	}
	/** 
	* @Title: getHttpSesion 
	* @Description: TODO(获取httpSession) 
	* @return HttpSession    返回类型 
	* @throws 
	*/ 
	public static HttpSession getHttpSesion(){

		return (HttpSession)ctx.get(ServletActionContext.SESSION);
	}
	
	/** 
	* @Title: getRoot 
	* @Description: TODO(获取webroot绝对路径) 
	* @return String    返回类型 
	* @throws 
	*/ 
	public static String getRoot(){
		
		return ServletActionContext.getServletContext().getRealPath("/");
	}
	
	/**
	 * 获取spring bean
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
