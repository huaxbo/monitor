package com.hxb.common.threadPool;

public abstract interface ACThreadPool {
	public abstract void SetThreadJob(ACThreadJob paramACThreadJob)
			throws Exception;
}