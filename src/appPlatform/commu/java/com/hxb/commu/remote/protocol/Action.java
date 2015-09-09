package com.hxb.commu.remote.protocol;


public class Action {
	private int op;

	private static int idx = 0;//基数
	/**
	 * 没有动作
	 */
	public static final Action nullAction = new Action(0);
	/**
	 * 没有动作
	 */
	public static final Action noAction = new Action(caculateOpt(idx++));
	/**
	 * 向测控端回复确认
	 */
	public static final Action remoteConfirm = new Action(caculateOpt(idx++));
	
	/**
	 * 向测控端发命令
	 */
	public static final Action remoteCommand = new Action(caculateOpt(idx++));
	/**
	 * 命令结果
	 */
	public static final Action commandResult = new Action(caculateOpt(idx++));
	/**
	 * 命令结果
	 */
	public static final Action commandWait = new Action(caculateOpt(idx++));
	/**
	 * 主动上报数据
	 */
	public static final Action autoReport = new Action(caculateOpt(idx++));
	/**
	 * 修改 ID
	 */
	public static final Action changeId = new Action(caculateOpt(idx++));
	/**
	 * 同步测控器与通信服务器时钟，即对测控器较时
	 */
	public static final Action synchronizeClock = new Action(caculateOpt(idx++));
	/**
	 * RTU网络注消 , 关闭网络连接
	 */
	public static final Action closeConnect = new Action(caculateOpt(idx++));
	/**
	 * 未知的命令
	 */
	public static final Action unknown = new Action(caculateOpt(idx++));
	/**
	 * 出错
	 */
	public static final Action error = new Action(caculateOpt(idx++));
	/**
	 * 卫星主动上报确认
	 */
	public static final Action stateReportConfirm = new Action(caculateOpt(idx++));

	/**
	 * @param op
	 */
	private Action(int op) {
		this.op = op;
	}

	//当前Action中，是否存在上面预定义的某一个action
	public boolean has(Action action) {
		return (this.op & action.op) != 0 ;
	}

	//在一个action中加入另外一个action,
	public Action add(Action addAction , Action addedAction) {
		return new Action(addAction.op | addedAction.op);
	}

	/**
	 * 比较两个action是否相同
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
	 * 计算2的pow1次幂
	 * @param pow1
	 * @return
	 */
	private static int caculateOpt(int pow1){
		
		return (int)Math.pow(2,pow1);
	}
}

