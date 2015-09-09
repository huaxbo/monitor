package com.hxb.commu.remote.protocol;


import com.hxb.commu.local.common.CommandAttach;

public abstract class ProtocolDriver {

	
	/**
	 * 解析命令结果数据
	 */
	public ProtocolData centerData;
	
	/**
	 * 远程下发数据
	 */
	public byte[] remoteData;
	
	/**
	 * 构建命令
	 * @return
	 */
	public abstract byte[] createCommand(String meterId,String funcCode,CommandAttach atta);
	
	/**
	 * 解析命令
	 */
	public abstract Action analyzeCommand(String meterId,byte[] bts);
	
	/**
	 * 获取功能码
	 * @param bts
	 * @return
	 */
	public abstract String getFuncCode(byte[] bts);
	
	/**
	 * 是否为上线注册命令
	 * @param funcCode
	 * @return
	 */
	public abstract boolean isRegister(String funcCode);
}
