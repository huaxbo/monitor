package com.hxb.commu.remote.tcp.manager.session;

import org.apache.mina.core.session.IoSession;

public class SessionVO {	
	
	
	/**
	 * @param id
	 * @param session
	 * @param online
	 */
	public SessionVO(String id,IoSession session){
		this.id = id;
		this.session = session;
		this.online = SessionConstant.METER_ONLINE;		
		this.timestamp = System.currentTimeMillis();
	}
	
	private String id;//�豸id��
	
	private String online;//����״̬
	
	private IoSession session;//�豸������Ϣ
	
	private String commandId;//����id��Ϊnull��ʾ������
	
	private int cmdFails;//����ʧ���������

	private long timestamp;//����յ��ϱ�����ʱ�������λ��ms
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}
	public int getCmdFails() {
		return cmdFails;
	}

	public void setCmdFails(int cmdFails) {
		this.cmdFails = cmdFails;
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
