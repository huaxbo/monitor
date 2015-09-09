package com.hxb.common.timerTask;

import com.hxb.common.imp.timerTask.TaskJob;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class ACTaskJob extends TaskJob {
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		try {
			execute();
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}

	public abstract void execute() throws Exception;
}