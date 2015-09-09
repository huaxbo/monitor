package com.hxb.imulator.protocol;


public interface ProtocolDriver {

	
	/**
	 * 解析命令
	 * @param bts
	 */
	public byte[] analyze(byte[] bts);
	
	/**
	 * 解析功能码
	 * @param bts
	 * @return
	 */
	public String analyzeFuncCode(byte[] bts);
	
	/**
	 * 解析设备id
	 * @param bts
	 * @return
	 */
	public String analyzeMeterId(byte[] bts);
	/**
	 * 构建注册报
	 * @param meterId
	 * @return
	 */
	public byte[] createRegister(String meterId);
	
	/**
	 * 构建心跳报
	 * @param meterId
	 * @return
	 */
	public byte[] createHeart(String meterId);
	
	/**
	 * 构建定时报
	 * @param meterId
	 * @return
	 */
	public byte[] createReport(String meterId);
}
