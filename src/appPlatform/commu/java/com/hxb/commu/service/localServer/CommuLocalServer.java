package com.hxb.commu.service.localServer;

import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService
public interface CommuLocalServer {

	/**
	 * ���ձ�������Թ���Զ������
	 * @param command
	 * @return
	 */
	@WebMethod
	public String sendCommand(String command);
	
	
}
