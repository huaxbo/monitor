package com.hxb.util.struts ;

import java.io.* ;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Base Action class for the Tutorial package.
 */
public abstract class SysActionSupportPriv extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3515078111111117288L;
	
	public static InputStream  noPrivStream ;
	public static InputStream  jQueryNoPrivStream ;
	public static InputStream  flexNoPrivStream ;
	public static StringBuilder sb = new StringBuilder() ;
	public static StringBuilder sbJQuery = new StringBuilder() ;
	public static StringBuilder sbFlex = new StringBuilder() ;
	static{
		//jquery权限验证
		
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"../globalRes/js/jquery_easyui/themes/gray/easyui.css\"/>");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"../globalRes/js/jquery_easyui/themes/icon.css\"/>");
		sb.append("<script type=\"text/javascript\" src=\"../globalRes/js/jquery-1.7.2.min.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"../globalRes/js/jquery_easyui/jquery.easyui.min.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"../globalRes/js/jquery-automic-global.js\"></script>");
		sb.append("<script tepe=\"text/javascript\">");
		//常规权限验证
		sb.append("$j(document).ready(function(){$j.messager.alert('信息提示','无权访问！','warning',function(){window.history.go(-1);});})");
		sb.append("</script>");
		
		//jquery权限验证信息补全		
		sbJQuery.append("{\"noJQueryPriv\":\"无权访问！\"}");
			
		/**
		 * 此处<noFlexPriv></mess>,必须原样使用，直接可以写入信息，
		 * 供flex中FaultEvent调用，捕获异常事件及无权信息...
		*/
		sbFlex.append("<noFlexPriv>无权访问</noFlexPriv0>");
	}
	public static InputStream getNoPrivStream() {
		try{
			noPrivStream = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"), 0, sb.toString().getBytes("UTF-8").length);
			noPrivStream.close() ;
		}catch(Exception e ){e.printStackTrace() ;}
		return noPrivStream;
	}
	public static InputStream getJQueryNoPrivStream() {
		try{
			jQueryNoPrivStream = new ByteArrayInputStream(sbJQuery.toString().getBytes("UTF-8"), 0, sbJQuery.toString().getBytes("UTF-8").length);
			jQueryNoPrivStream.close() ;
		}catch(Exception e ){e.printStackTrace() ;}
		return jQueryNoPrivStream;
	}
	
	public static InputStream getFlexNoPrivStream() {
		try{
			flexNoPrivStream = new ByteArrayInputStream(sbFlex.toString().getBytes("UTF-8"), 0, sbFlex.toString().getBytes("UTF-8").length);
			flexNoPrivStream.close() ;
		}catch(Exception e ){e.printStackTrace() ;}
		return flexNoPrivStream;
	}

	
	public static void setNoPrivStream(InputStream noPrivStream) {
		SysActionSupportPriv.noPrivStream = noPrivStream;
	}
	public static void setJQueryNoPrivStream(InputStream jQueryNoPrivStream) {
		SysActionSupportPriv.jQueryNoPrivStream = jQueryNoPrivStream;
	}
	public static void setFlexNoPrivStream(InputStream flexNoPrivStream) {
		SysActionSupportPriv.flexNoPrivStream = flexNoPrivStream;
	}

}

