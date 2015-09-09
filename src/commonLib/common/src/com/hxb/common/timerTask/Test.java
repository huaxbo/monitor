package com.hxb.common.timerTask;

import com.hxb.ACException;
import com.hxb.common.imp.timerTask.QuartzSchedular;

import java.io.PrintStream;
import org.apache.log4j.Logger;

public class Test extends ACTaskJob {
	
	
	public static void main(String[] args) {
		try {
			new ACSchedularJob().addSecondlyJob("testJob", "testJobGroup",
					Test.class, null, 1000L, 1, 9);
		} catch (ACException e) {
			Logger log = Logger.getLogger(Test.class.getName());
			log.error("³ö´í" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void execute() throws Exception {
		System.out.println(System.currentTimeMillis());
	}
}