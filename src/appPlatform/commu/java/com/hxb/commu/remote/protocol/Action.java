package com.hxb.commu.remote.protocol;


public class Action {
	private int op;

	private static int idx = 0;//����
	/**
	 * û�ж���
	 */
	public static final Action nullAction = new Action(0);
	/**
	 * û�ж���
	 */
	public static final Action noAction = new Action(caculateOpt(idx++));
	/**
	 * ���ض˻ظ�ȷ��
	 */
	public static final Action remoteConfirm = new Action(caculateOpt(idx++));
	
	/**
	 * ���ض˷�����
	 */
	public static final Action remoteCommand = new Action(caculateOpt(idx++));
	/**
	 * ������
	 */
	public static final Action commandResult = new Action(caculateOpt(idx++));
	/**
	 * ������
	 */
	public static final Action commandWait = new Action(caculateOpt(idx++));
	/**
	 * �����ϱ�����
	 */
	public static final Action autoReport = new Action(caculateOpt(idx++));
	/**
	 * �޸� ID
	 */
	public static final Action changeId = new Action(caculateOpt(idx++));
	/**
	 * ͬ���������ͨ�ŷ�����ʱ�ӣ����Բ������ʱ
	 */
	public static final Action synchronizeClock = new Action(caculateOpt(idx++));
	/**
	 * RTU����ע�� , �ر���������
	 */
	public static final Action closeConnect = new Action(caculateOpt(idx++));
	/**
	 * δ֪������
	 */
	public static final Action unknown = new Action(caculateOpt(idx++));
	/**
	 * ����
	 */
	public static final Action error = new Action(caculateOpt(idx++));
	/**
	 * ���������ϱ�ȷ��
	 */
	public static final Action stateReportConfirm = new Action(caculateOpt(idx++));

	/**
	 * @param op
	 */
	private Action(int op) {
		this.op = op;
	}

	//��ǰAction�У��Ƿ��������Ԥ�����ĳһ��action
	public boolean has(Action action) {
		return (this.op & action.op) != 0 ;
	}

	//��һ��action�м�������һ��action,
	public Action add(Action addAction , Action addedAction) {
		return new Action(addAction.op | addedAction.op);
	}

	/**
	 * �Ƚ�����action�Ƿ���ͬ
	 * @param action Action
	 * @return boolean
	 */
	public boolean equals(Action action) {
		return this.op == action.op;
	}
	
	public String toString(){
		return this.op + "" ;
	}
	
	/**
	 * ����2��pow1����
	 * @param pow1
	 * @return
	 */
	private static int caculateOpt(int pow1){
		
		return (int)Math.pow(2,pow1);
	}
}

