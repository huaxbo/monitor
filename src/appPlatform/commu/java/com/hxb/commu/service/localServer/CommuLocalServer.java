package com.hxb.commu.service.localServer;

import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService
public interface CommuLocalServer {

	/**
	 * 接收本地命令，以构造远程命令
	 * @param command
	 * @return
	 */
	@WebMethod
	public String sendCommand(String command);
	
	
}
