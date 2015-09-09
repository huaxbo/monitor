package com.hxb.commu.remote.protocol.ver_test;

import org.apache.log4j.Logger;

import com.hxb.commu.local.common.CommandAttach;
import com.hxb.commu.remote.protocol.Action;
import com.hxb.commu.remote.protocol.ProtocolDriver;
import com.hxb.commu.remote.protocol.util.UtilProtocol;
import com.hxb.commu.remote.protocol.ver_test.cd_10.Answer_10;
import com.hxb.commu.remote.protocol.ver_test.cd_11.Answer_11;
import com.hxb.commu.remote.protocol.ver_test.cd_11.Create_11;
import com.hxb.commu.remote.protocol.ver_test.cd_confirm.Create_confirm;

public class VerTestDriver extends ProtocolDriver {

	private Logger log = Logger.getLogger(VerTestDriver.class);
	
	/* (non-Javadoc)
	 * @see com.hxb.commu.remote.protocol.ProtocolDriver#createCommand(java.lang.String, java.lang.String, com.hxb.commu.local.common.CommandAttach)
	 */
	@Override
	public byte[] createCommand(String meterId,String funcCode,CommandAttach atta) {
		// TODO Auto-generated method stub
		if(funcCode.equals(VerTestCode.cd_register)){//构建确认命令
			
			return Create_confirm.getSingle().create(meterId, funcCode);
		}else if(funcCode.equals(VerTestCode.cd_11)){//查询实时值
			
			return Create_11.getSingle().create(meterId,funcCode,atta);
		}else{
			
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hxb.commu.remote.protocol.ProtocolDriver#analyzeCommand(java.lang.String, byte[])
	 */
	@Override
	public Action analyzeCommand(String meterId, byte[] bts) {
		// TODO Auto-generated method stub
		Action ac = Action.nullAction;
		String funcCode = getFuncCode(bts);
		if(funcCode.equals(VerTestCode.cd_heart)){
			this.remoteData = Create_confirm.getSingle().create(meterId, funcCode);
			ac = ac.add(ac,Action.remoteConfirm);
		}else if(funcCode.equals(VerTestCode.cd_10)){//定时报
			this.centerData = Answer_10.getSingle().parseData(meterId, bts);
			this.remoteData = Create_confirm.getSingle().create(meterId, funcCode);
			ac = ac.add(ac,Action.autoReport);
			ac = ac.add(ac,Action.remoteConfirm);
		}else if(funcCode.equals(VerTestCode.cd_11)){//召测实时值
			this.centerData = Answer_11.getSingle().parseData(meterId, bts);
			this.remoteData = Create_confirm.getSingle().create(meterId, funcCode);
			ac = ac.add(ac, Action.commandResult);
			ac = ac.add(ac,Action.remoteConfirm);
			
		}else{
			log.warn("暂时不支持功能码：" + funcCode);
		}
		
		return ac;
	}

	/* (non-Javadoc)
	 * @see com.hxb.commu.remote.protocol.ProtocolDriver#getFuncCode(byte[])
	 */
	@Override
	public String getFuncCode(byte[] bts) {
		// TODO Auto-generated method stub
		UtilProtocol up = UtilProtocol.getSingle();
		try {
			return up.byte2Hex(new byte[]{bts[VerTestConstant.CODE_SITE]}, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hxb.commu.remote.protocol.ProtocolDriver#isRegister(java.lang.String)
	 * 是否为上线注册命令
	 */
	@Override
	public boolean isRegister(String funcCode) {
		// TODO Auto-generated method stub
		if(funcCode != null && funcCode.equals(VerTestCode.cd_register)){
			return true;
		}
		return false;
	}

}
