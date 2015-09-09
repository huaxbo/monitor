package com.hxb.util.json;

import java.io.IOException;

import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

public class JsonActionSupport extends ActionSupport {

	private static final long serialVersionUID = -9002600231579247535L;
	
	public String writeJsonData(
			JsonResult result,
			HashMap<String , String> params){
		HttpServletResponse response = ServletActionContext.getResponse() ;

		JSONObject jobj = new JSONObject();
		// isSucc,获取成功(如果结果为空,说明本来就不存在),返回200
		if (result.isSucc()) {
			jobj.put(JsonResult.CODE_KEY, JsonResult.CODE_SUCC);// code.value, for example "200"
			jobj.put(JsonResult.MESSAGE_KEY, "");		// code.message, for example "SUCCESS"
			jobj.put(JsonResult.RESULT_KEY, result.getObject());	// result
		} else {
			jobj.put(JsonResult.CODE_KEY, JsonResult.CODE_FAIL);  // code.value, for example "200"
			jobj.put(JsonResult.MESSAGE_KEY, result.getMessage());// code.message, for example "SUCCESS"
		}
		if(params != null){
			Set<Map.Entry<String , String>> set = params.entrySet() ;
			Iterator<Map.Entry<String , String>> it = set.iterator() ;
			Map.Entry<String , String> entry = null ;
			while(it.hasNext()){
				entry = it.next() ;
				jobj.put(entry.getKey() , entry.getValue()) ;
			}
		}

		response.setContentType("text/html; charset=GBK");
		try{
			jobj.write(response.getWriter());
		}catch(IOException e){
			;
		}
		return null ;
	}


}
