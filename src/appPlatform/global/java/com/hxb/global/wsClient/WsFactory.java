package com.hxb.global.wsClient;

import com.hxb.global.wsClient.imp.WsDataSenderImp;

public class WsFactory {

	/**
	 * �õ����ݷ�����ʵ��
	 * @return
	 */
	public static WsDataSender instanceWsDataSender(){
		return new WsDataSenderImp() ;
	}
}
