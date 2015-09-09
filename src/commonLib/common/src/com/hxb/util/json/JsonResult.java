package com.hxb.util.json;

import net.sf.json.*;

public class JsonResult {
	
	public static final String CODE_KEY = "code" ;
	public static final String MESSAGE_KEY = "message" ;
	public static final String RESULT_KEY = "result" ;
	public static final String CODE_SUCC = "200" ;
	public static final String CODE_FAIL = "400" ;
	
	private boolean succ;
	private String message;
	private JSONArray object;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSucc() {
		return succ;
	}
	public void setSucc(boolean succ) {
		this.succ = succ;
	}
	public JSONArray getObject() {
		return object;
	}
	public void setObject(JSONArray object) {
		this.object = object;
	}

}
