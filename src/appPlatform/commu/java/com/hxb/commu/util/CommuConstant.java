package com.hxb.commu.util;

public class CommuConstant {


	/**
	 * Զ������ά������������ʧ�ܶϿ����ӣ�������ʧ�����������
	 * -1����ʾ�����д˲���
	 */
	public static int continueFailCmds_times = 2;
	/**
	 * Զ������ά�������������ݽ��ռ������������Ͽ����ӡ���λ������
	 * -1����ʾ���д˲���
	 */
	public static int noneReceive_interval = 5;
	
	
	/**
	 * ���������ִ״̬���ɹ�
	 */
	public static String receipt_succ = "receicpt_succ";
	/**
	 * ���������ִ״̬��ʧ��
	 */
	public static String receipt_fail = "receipt_fail";
	

	/**
	 * TCPԶ�����ã������������
	 */
	public static int remote_processors = 3;
	/**
	 * TCPԶ�̲������ã�Զ�����ӿ���ʱ������λ��s
	 */
	public static int remote_idle = 10;
	/**
	 * TCPԶ�̲������ã�Զ��������־�������
	 */
	public static boolean remote_logs = false;
	/**
	 * TCPԶ�̲������ã�Զ�̷���˿�
	 */
	public static int remote_port = 9014;
	/**
	 * TCPԶ�̲������ã�Զ������ά����ѵ�������λ��m
	 */
	public static int remote_sessionMgrInteval = 1;
	
	/**
	 * Զ������������������ʧ�ܴ���
	 */
	public static int command_maxFails = 3;
	/**
	 * Զ�������ÿ�������·������еȴ��������λ��s
	 */
	public static int command_perInterval = 10;
	/**
	 * Զ��������������ɨ��������λ��s
	 */
	public static int command_scanInterval = 10;
	
}
