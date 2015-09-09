package com.hxb.common.imp.threadPool;

import com.hxb.ACException;
import com.hxb.common.threadPool.ACThreadJob;

import org.apache.log4j.Logger;

public class Threader extends Thread {
	private boolean living = true;
	protected long time;
	private String threadName;
	private ThreadPoolImp pool;
	private ACThreadJob job;
	private Boolean canJob;

	protected Threader(String threadName, ThreadPoolImp pool) {
		this.threadName = threadName;
		this.pool = pool;
		this.time = 0L;
		this.canJob = Boolean.valueOf(false);
	}

	protected void setJob(ACThreadJob job) throws Exception {
		if (job == null)
			this.job = new ACThreadJob() {
				public void execute() {
				}
			};
		synchronized (this) {
			this.job = job;
			this.canJob = Boolean.valueOf(true);

			this.time = System.currentTimeMillis();
			notify();
		}
	}

	public void run() {
		while (this.living)
			synchronized (this) {
				while (!this.canJob.booleanValue()) {
					try {
						wait();
					} catch (Exception e) {
						Logger log = Logger.getLogger(Threader.class.getName());
						log.error("�����ӷ���(ID+" + this.threadName
								+ ")�Ĺ����̵߳ȴ��ɹ����ź�ʱ�����ȴ��쳣", e);
						this.canJob = Boolean.valueOf(false);
					}
				}
				try {
					this.job.execute();
				} catch (ACException ee) {
					Logger log = Logger.getLogger(Threader.class.getName());
					log.error("�����ӷ���(ID=" + this.threadName
							+ ")�Ĺ����߳���ִ�й���ʱ�����쳣��" + ee.getMessage(), ee);
				} catch (Exception e) {
					Logger log = Logger.getLogger(Threader.class.getName());
					log.error("�����ӷ���(ID=" + this.threadName
							+ ")�Ĺ����߳���ִ�й���ʱ�����쳣��", e);
				} finally {
					this.canJob = Boolean.valueOf(false);
				}
				this.job = null;
				free();
			}
	}

	public void free() {
		this.pool.freeThread(this);

		this.time = System.currentTimeMillis();

		this.canJob = Boolean.valueOf(false);
	}

	public void destroy() {
		this.living = false;
		this.job = null;
	}
}