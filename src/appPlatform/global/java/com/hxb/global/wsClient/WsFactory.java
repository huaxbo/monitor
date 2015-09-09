package com.hxb.global.wsClient;

import com.hxb.global.wsClient.imp.WsDataSenderImp;

public class WsFactory {

	/**
	 * 得到数据发送者实例
	 * @return
	 */
	public static WsDataSender instanceWsDataSender(){
		return new WsDataSenderImp() ;
	}
}
