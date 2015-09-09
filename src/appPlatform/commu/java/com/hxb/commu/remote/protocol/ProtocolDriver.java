package com.hxb.commu.remote.protocol;


import com.hxb.commu.local.common.CommandAttach;

public abstract class ProtocolDriver {

	
	/**
	 * ��������������
	 */
	public ProtocolData centerData;
	
	/**
	 * Զ���·�����
	 */
	public byte[] remoteData;
	
	/**
	 * ��������
	 * @return
	 */
	public abstract byte[] createCommand(String meterId,String funcCode,CommandAttach atta);
	
	/**
	 * ��������
	 */
	public abstract Action analyzeCommand(String meterId,byte[] bts);
	
	/**
	 * ��ȡ������
	 * @param bts
	 * @return
	 */
	public abstract String getFuncCode(byte[] bts);
	
	/**
	 * �Ƿ�Ϊ����ע������
	 * @param funcCode
	 * @return
	 */
	public abstract boolean isRegister(String funcCode);
}
