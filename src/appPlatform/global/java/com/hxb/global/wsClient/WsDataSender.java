package com.hxb.global.wsClient;

/**
 * ���ݷ�����
 *
 */
public interface WsDataSender {
	
	/**
	 * �����ݴ����ͻ��ˣ�����ʹ�ͻ��˿��԰������ϱ����ϼ�
	 * @param command
	 * @param data
	 */
	public void sendData(String command , StringBuffer data) ;

}
