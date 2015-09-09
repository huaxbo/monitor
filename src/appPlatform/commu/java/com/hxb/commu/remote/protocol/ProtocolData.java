package com.hxb.commu.remote.protocol;

public class ProtocolData {

	/**
	 * 命令id
	 */
	private String commandId;	
	/**
	 * 测控设备id
	 */
	private String meterId;	
	/**
	 * 功能码
	 */
	private String funcCode;	
	/**
	 * 结果数据
	 */
	private Object data;
	
	
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public String getFuncCode() {
		return funcCode;
	}
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
