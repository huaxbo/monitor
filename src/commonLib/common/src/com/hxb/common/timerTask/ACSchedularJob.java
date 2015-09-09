package com.hxb.common.timerTask;

import com.hxb.ACException;
import com.hxb.common.imp.timerTask.QuartzSchedular;

import java.util.HashMap;

public class ACSchedularJob {
	public void addSimpleJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap,
			long delayStart, long repeatInterval, int repeatCount)
			throws ACException {
		QuartzSchedular.start();
		QuartzSchedular.addSimpleJob(jobName, jobGroupName, jboClass,
				jobDataMap, delayStart, repeatInterval, repeatCount);
	}

	public void addSecondlyJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap,
			long delayStart, int intervalInSeconds, int repeatCount)
			throws ACException {
		QuartzSchedular.start();
		QuartzSchedular.addSecondlyJob(jobName, jobGroupName, jboClass,
				jobDataMap, delayStart, intervalInSeconds, repeatCount);
	}

	public void addMinutelyJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap,
			long delayStart, int intervalInMinute, int repeatCount)
			throws ACException {
		QuartzSchedular.start();
		QuartzSchedular.addMinutelyJob(jobName, jobGroupName, jboClass,
				jobDataMap, delayStart, intervalInMinute, repeatCount);
	}

	public void addHourlyJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap,
			long delayStart, int intervalInHour, int repeatCount)
			throws ACException {
		QuartzSchedular.start();
		QuartzSchedular.addHourlyJob(jobName, jobGroupName, jboClass,
				jobDataMap, delayStart, intervalInHour, repeatCount);
	}

	public void addDailyJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap, int hour,
			int minute) throws ACException {
		QuartzSchedular.start();
		QuartzSchedular.addDailyJob(jobName, jobGroupName, jboClass,
				jobDataMap, hour, minute);
	}

	public void addWeeklyJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap, int dayOfWeek,
			int hour, int minute) throws ACException {
		QuartzSchedular.start();
		QuartzSchedular.addWeeklyJob(jobName, jobGroupName, jboClass,
				jobDataMap, dayOfWeek, hour, minute);
	}

	public void addMonthlyJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap, int dayOfMonth,
			int hour, int minute) throws ACException {
		QuartzSchedular.start();
		QuartzSchedular.addMonthlyJob(jobName, jobGroupName, jboClass,
				jobDataMap, dayOfMonth, hour, minute);
	}

	public void addLastDateOfMonthJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap, int hour,
			int minute) throws ACException {
		QuartzSchedular.start();
		QuartzSchedular.addLastDateOfMonthJob(jobName, jobGroupName, jboClass,
				jobDataMap, hour, minute);
	}

	public void addWorkingDayInWeekJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap, int hour,
			int minute) throws ACException {
		QuartzSchedular.start();
		QuartzSchedular.addWorkingDayInWeekJob(jobName, jobGroupName, jboClass,
				jobDataMap, hour, minute);
	}
}