package com.hxb.common.imp.timerTask;

import com.hxb.ACException;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzSchedular {
	private static QuartzSchedular instance;
	private static Scheduler scheduler;

	public static void start() {
		if (instance == null) {
			instance = new QuartzSchedular();
			try {
				SchedulerFactory factory = new StdSchedulerFactory();
				scheduler = factory.getScheduler();
				scheduler.start();
			} catch (SchedulerException e) {
				Logger log = Logger.getLogger(QuartzSchedular.class.getName());
				log.error("初始化quartz的Scheduler发生异常!", e);
			}
		}
	}

	public static void addSimpleJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap,
			long delayStart, long repeatInterval, int repeatCount)
			throws ACException {
		checkDelayStart(delayStart);
		if (repeatInterval < 1000L) {
			throw new ACException("重复工作间隔时长(单位毫秒)不能小于1000毫秒(即1秒)!");
		}
		repeatCount = checkRepeatCount(repeatCount);

		JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jboClass);
		if (jobDataMap != null) {
			JobDataMap map = jobDetail.getJobDataMap();
			Set set = jobDataMap.entrySet();
			Iterator it = set.iterator();
			Map.Entry entry = null;
			String key = null;
			Object value = null;
			while (it.hasNext()) {
				entry = (Map.Entry) it.next();
				key = (String) entry.getKey();
				value = entry.getValue();
				map.put(key, value);
			}
		}

		SimpleTrigger trigger = new SimpleTrigger(jobName + "trigger",
				jobGroupName);
		trigger.setRepeatInterval(repeatInterval);
		trigger.setRepeatCount(repeatCount);
		trigger.setStartTime(new Date(System.currentTimeMillis() + delayStart));
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			throw new ACException(e);
		}
	}

	public static void addSecondlyJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap,
			long delayStart, int intervalInSeconds, int repeatCount)
			throws ACException {
		checkDelayStart(delayStart);
		if (intervalInSeconds < 1) {
			throw new ACException("重复工作间隔时长(秒)不能小于1秒)!");
		}
		repeatCount = checkRepeatCount(repeatCount);

		Trigger trigger = TriggerUtils.makeSecondlyTrigger(jobName + "trigger",
				intervalInSeconds, repeatCount);
		addJob(jobName, jobGroupName, jboClass, jobDataMap, delayStart, trigger);
	}

	public static void addMinutelyJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap,
			long delayStart, int intervalInMinute, int repeatCount)
			throws ACException {
		checkDelayStart(delayStart);
		if (intervalInMinute < 1) {
			throw new ACException("重复工作间隔时长(单位分钟)不能小于1分钟)!");
		}
		repeatCount = checkRepeatCount(repeatCount);

		Trigger trigger = TriggerUtils.makeMinutelyTrigger(jobName + "trigger",
				intervalInMinute, repeatCount);
		addJob(jobName, jobGroupName, jboClass, jobDataMap, delayStart, trigger);
	}

	public static void addHourlyJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap,
			long delayStart, int intervalInHour, int repeatCount)
			throws ACException {
		checkDelayStart(delayStart);
		if (intervalInHour < 1) {
			throw new ACException("重复工作间隔时长(单位小时)不能小于1小时)!");
		}
		repeatCount = checkRepeatCount(repeatCount);

		Trigger trigger = TriggerUtils.makeHourlyTrigger(jobName + "trigger",
				intervalInHour, repeatCount);
		addJob(jobName, jobGroupName, jboClass, jobDataMap, delayStart, trigger);
	}

	public static void addDailyJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap, int hour,
			int minute) throws ACException {
		checkHour(hour);
		checkMinute(minute);

		Trigger trigger = TriggerUtils.makeDailyTrigger(jobName + "trigger",
				hour, minute);
		addJob2(jobName, jobGroupName, jboClass, jobDataMap, trigger);
	}

	public static void addWeeklyJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap, int dayOfWeek,
			int hour, int minute) throws ACException {
		checkHour(hour);
		checkMinute(minute);
		dayOfWeek = checkDayOfWeek(dayOfWeek);

		Trigger trigger = TriggerUtils.makeWeeklyTrigger(jobName + "trigger",
				dayOfWeek, hour, minute);
		addJob2(jobName, jobGroupName, jboClass, jobDataMap, trigger);
	}

	public static void addMonthlyJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap, int dayOfMonth,
			int hour, int minute) throws ACException {
		checkHour(hour);
		checkMinute(minute);
		checkDayOfMonth(dayOfMonth);

		Trigger trigger = TriggerUtils.makeMonthlyTrigger(jobName + "trigger",
				dayOfMonth, hour, minute);
		addJob2(jobName, jobGroupName, jboClass, jobDataMap, trigger);
	}

	public static void addLastDateOfMonthJob(String jobName,
			String jobGroupName, Class jboClass,
			HashMap<String, Object> jobDataMap, int hour, int minute)
			throws ACException {
		checkHour(hour);
		checkMinute(minute);
		CronTrigger ct = new CronTrigger(jobName + "trigger", jobGroupName);
		try {
			ct.setCronExpression(new CronExpression("0 " + minute + " " + hour
					+ " L * ? *"));
		} catch (Exception e) {
			throw new ACException(e.getMessage(), e);
		}

		addJob3(jobName, jobGroupName, jboClass, jobDataMap, ct);
	}

	public static void addWorkingDayInWeekJob(String jobName,
			String jobGroupName, Class jboClass,
			HashMap<String, Object> jobDataMap, int hour, int minute)
			throws ACException {
		checkHour(hour);
		checkMinute(minute);
		CronTrigger ct = new CronTrigger(jobName + "trigger", jobGroupName);
		try {
			ct.setCronExpression(new CronExpression("0 " + minute + " " + hour
					+ " ? * " + 2 + "-" + 6 + " *"));
		} catch (Exception e) {
			throw new ACException(e.getMessage(), e);
		}

		addJob3(jobName, jobGroupName, jboClass, jobDataMap, ct);
	}

	private static void addJob(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap,
			long delayStart, Trigger trigger) throws ACException {
		trigger.setGroup(jobGroupName);
		trigger.setStartTime(new Date(System.currentTimeMillis() + delayStart));
		JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jboClass);
		if (jobDataMap != null) {
			JobDataMap map = jobDetail.getJobDataMap();
			Set set = jobDataMap.entrySet();
			Iterator it = set.iterator();
			Map.Entry entry = null;
			String key = null;
			Object value = null;
			while (it.hasNext()) {
				entry = (Map.Entry) it.next();
				key = (String) entry.getKey();
				value = entry.getValue();
				map.put(key, value);
			}
		}
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			throw new ACException(e);
		}
	}

	private static void addJob2(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap, Trigger trigger)
			throws ACException {
		trigger.setGroup(jobGroupName);
		JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jboClass);
		if (jobDataMap != null) {
			JobDataMap map = jobDetail.getJobDataMap();
			Set set = jobDataMap.entrySet();
			Iterator it = set.iterator();
			Map.Entry entry = null;
			String key = null;
			Object value = null;
			while (it.hasNext()) {
				entry = (Map.Entry) it.next();
				key = (String) entry.getKey();
				value = entry.getValue();
				map.put(key, value);
			}
		}
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			throw new ACException(e);
		}
	}

	private static void addJob3(String jobName, String jobGroupName,
			Class jboClass, HashMap<String, Object> jobDataMap,
			CronTrigger trigger) throws ACException {
		JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jboClass);
		if (jobDataMap != null) {
			JobDataMap map = jobDetail.getJobDataMap();
			Set set = jobDataMap.entrySet();
			Iterator it = set.iterator();
			Map.Entry entry = null;
			String key = null;
			Object value = null;
			while (it.hasNext()) {
				entry = (Map.Entry) it.next();
				key = (String) entry.getKey();
				value = entry.getValue();
				map.put(key, value);
			}
		}
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			throw new ACException(e);
		}
	}

	private static void checkDelayStart(long delayStart) throws ACException {
		if (delayStart < 0L)
			throw new ACException("延迟开始工作时长(单位毫秒)不能小于0!");
	}

	private static int checkRepeatCount(int repeatCount) {
		if (repeatCount <= -1) {
			repeatCount = SimpleTrigger.REPEAT_INDEFINITELY;
		}
		return repeatCount;
	}

	private static void checkHour(int hour) throws ACException {
		if ((hour < 0) || (hour >= 24))
			throw new ACException("重复工作的小时时间点取值必须在0-23之间(包括0与23)!");
	}

	private static void checkMinute(int minute) throws ACException {
		if ((minute < 0) || (minute >= 60))
			throw new ACException("重复工作的分钟时间点取值必须在0-59之间(包括0与59)!");
	}

	private static int checkDayOfWeek(int dayOfWeek) throws ACException {
		if ((dayOfWeek < 1) || (dayOfWeek > 7)) {
			throw new ACException("重复工作的一星期的某一天取值必须在1-7间(包括1与7)!");
		}
		int d = 0;
		if (dayOfWeek == 1)
			d = 2;
		else if (dayOfWeek == 2)
			d = 3;
		else if (dayOfWeek == 3)
			d = 4;
		else if (dayOfWeek == 4)
			d = 5;
		else if (dayOfWeek == 5)
			d = 6;
		else if (dayOfWeek == 6)
			d = 7;
		else if (dayOfWeek == 7) {
			d = 1;
		}
		return d;
	}

	private static void checkDayOfMonth(int dayOfMonth) throws ACException {
		if ((dayOfMonth < 1) || (dayOfMonth > 31))
			throw new ACException("重复工作的分钟时间点取值必须在1-31之间(包括1与31)!");
	}
}