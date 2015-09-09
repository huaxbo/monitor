package com.hxb.global.wsClient;

/**
 * 数据发送者
 *
 */
public interface WsDataSender {
	
	/**
	 * 把数据传给客户端，进而使客户端可以把数据上报到上级
	 * @param command
	 * @param data
	 */
	public void sendData(String command , StringBuffer data) ;

}
