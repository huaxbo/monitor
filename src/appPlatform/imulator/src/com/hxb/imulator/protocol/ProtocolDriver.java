package com.hxb.imulator.protocol;


public interface ProtocolDriver {

	
	/**
	 * ��������
	 * @param bts
	 */
	public byte[] analyze(byte[] bts);
	
	/**
	 * ����������
	 * @param bts
	 * @return
	 */
	public String analyzeFuncCode(byte[] bts);
	
	/**
	 * �����豸id
	 * @param bts
	 * @return
	 */
	public String analyzeMeterId(byte[] bts);
	/**
	 * ����ע�ᱨ
	 * @param meterId
	 * @return
	 */
	public byte[] createRegister(String meterId);
	
	/**
	 * ����������
	 * @param meterId
	 * @return
	 */
	public byte[] createHeart(String meterId);
	
	/**
	 * ������ʱ��
	 * @param meterId
	 * @return
	 */
	public byte[] createReport(String meterId);
}
