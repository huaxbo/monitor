package com.hxb.commu.service.localServer;


import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.hxb.commu.local.common.CommandAttach;
import com.hxb.commu.local.handler.LocalHandler;
import com.hxb.global.util.SerializeUtil;


@Component
@WebService(serviceName="commuLocalServer",
	endpointInterface="com.hxb.commu.service.localServer.CommuLocalServer")
public class CommuLocalServerImpl implements CommuLocalServer {

	/* (non-Javadoc)
	 * @see com.hxb.commu.local.server.CommuLocalServer#sendCommand(com.hxb.commu.local.common.CommandAttach)
	 */
	@Override
	public String sendCommand(String attaStr) {
		// TODO Auto-generated method stub		
		CommandAttach atta = (CommandAttach)SerializeUtil.getInstance()
				.xml2Obj(attaStr, new Class[]{CommandAttach.class});
		LocalHandler.getSingle().execute(atta);
		
		return SerializeUtil.getInstance().obj2Xml(atta, new Class[]{CommandAttach.class});
		
	}	

}
