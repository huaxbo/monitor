package com.hxb.commu.local.common;

import java.util.Hashtable;

public class CommandAttach {

	private String commandId;//����id
	private String meterId;//����豸id
	private String funcCode;//�������
	private Hashtable<String,Object> sendParams;//�����������
	
	private String receiptStatus;//��ִ��Ϣ-����״̬��
	private String receiptMessage;//��ִ��Ϣ-�ı�
	private Object receiptObj;//��ִ��Ϣ-����
	private boolean receiptOnline;//������Ϣ-�豸����״̬��true�����ߡ�false��������
	
	private boolean isSucc;//�������Ƿ�ظ���true���ɹ���false��ʧ��
	
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
