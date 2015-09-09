package com.hxb.common.threadPool;

public abstract interface ACThreadJob {
	public abstract void execute() throws Exception;
}