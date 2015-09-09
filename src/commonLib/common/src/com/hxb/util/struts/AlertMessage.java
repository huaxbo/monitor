package com.hxb.util.struts;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.hxb.util.ACConstant;
import com.hxb.util.ChangeCode;
import com.hxb.util.struts.SysActionSupport;

public class AlertMessage extends SysActionSupport {

	private static final long serialVersionUID = -74041402235001L;
	
	public String execute(){
		HttpServletRequest request = ServletActionContext.getRequest() ;
		HttpServletResponse response = ServletActionContext.getResponse() ;
		String message = ChangeCode.changeISO2UTF8(request.getParameter(ACConstant.message)) ;
		
		try {
			if(message == null){
				message="系统后台发生错误。" ;
			}
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			StringBuilder sder = new StringBuilder();
			sder.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"../globalRes/js/jquery_easyui/themes/gray/easyui.css\"/>");
			sder.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"../globalRes/js/jquery_easyui/themes/icon.css\"/>");
			sder.append("<script type=\"text/javascript\" src=\"../globalRes/js/jquery-1.7.2.min.js\"></script>");
			sder.append("<script type=\"text/javascript\" src=\"../globalRes/js/jquery_easyui/jquery.easyui.min.js\"></script>");
			sder.append("<script type=\"text/javascript\" src=\"../globalRes/js/jquery-automic-global.js\"></script>");
			sder.append("<script tepe='text/javascript'>");
			sder.append("$j(document).ready(function(){$j.messager.alert('信息提示','" + message + "','warning',function(){window.history.go(-1);});})");
			sder.append("</script>");
			out.print(sder.toString());
			out.close();
			return null ;
		} catch (Exception e) {
		}
		return null ;
	}
}
