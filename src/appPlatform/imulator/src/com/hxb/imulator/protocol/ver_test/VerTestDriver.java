package com.hxb.imulator.protocol.ver_test;

import com.hxb.imulator.protocol.ProtocolDriver;
import com.hxb.imulator.protocol.util.UtilProtocol;

public class VerTestDriver implements ProtocolDriver {

	@Override
	public byte[] analyze(byte[] bts) {
		// TODO Auto-generated method stub
		String funcCode = analyzeFuncCode(bts);
		String meterId = analyzeMeterId(bts);
		
		if(funcCode.equals(VerTestCode.cd_11)){
			return VerTestCreate.create_11(meterId, funcCode);
		}
		
		return null;
	}
	
	@Override
	public byte[] createRegister(String meterId) {
		// TODO Auto-generated method stub
		return VerTestCreate.create_register(meterId);
	}

	@Override
	public byte[] createHeart(String meterId) {
		// TODO Auto-generated method stub
		return VerTestCreate.create_heart(meterId);
	}

	@Override
	public byte[] createReport(String meterId) {
		// TODO Auto-generated method stub
		
		return VerTestCreate.create_10(meterId, VerTestCode.cd_10);
	}
	
	@Override
	public String analyzeFuncCode(byte[] bts) {
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

	@Override
	public String analyzeMeterId(byte[] bts) {
		// TODO Auto-generated method stub
		UtilProtocol up = UtilProtocol.getSingle();
		try {
			return up.BCD2String(bts, VerTestConstant.HEAD_LEN, VerTestConstant.CODE_SITE-1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
