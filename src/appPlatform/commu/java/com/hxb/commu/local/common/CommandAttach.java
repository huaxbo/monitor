package com.hxb.commu.local.common;

import java.util.Hashtable;

public class CommandAttach {

	private String commandId;//命令id
	private String meterId;//测控设备id
	private String funcCode;//命令功能码
	private Hashtable<String,Object> sendParams;//发送命令参数
	
	private String receiptStatus;//回执信息-命令状态码
	private String receiptMessage;//回执信息-文本
	private Object receiptObj;//回执信息-对象
	private boolean receiptOnline;//绘制信息-设备在线状态：true：在线、false：不在线
	
	private boolean isSucc;//命令结果是否回复。true：成果、false：失败
	
	public String getFuncCode() {
		return funcCode;
	}
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}
	public Hashtable<String, Object> getSendParams() {
		return sendParams;
	}
	public void setSendParams(Hashtable<String, Object> sendParams) {
		this.sendParams = sendParams;
	}
	public String getReceiptStatus() {
		return receiptStatus;
	}
	public void setReceiptStatus(String receiptStatus) {
		this.receiptStatus = receiptStatus;
	}
	public String getReceiptMessage() {
		return receiptMessage;
	}
	public void setReceiptMessage(String receiptMessage) {
		this.receiptMessage = receiptMessage;
	}
	public Object getReceiptObj() {
		return receiptObj;
	}
	public void setReceiptObj(Object receiptObj) {
		this.receiptObj = receiptObj;
	}
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}	
	public boolean isReceiptOnline() {
		return receiptOnline;
	}
	public void setReceiptOnline(boolean receiptOnline) {
		this.receiptOnline = receiptOnline;
	}
	public boolean isSucc() {
		return isSucc;
	}
	public void setSucc(boolean isSucc) {
		this.isSucc = isSucc;
	}
	
}
